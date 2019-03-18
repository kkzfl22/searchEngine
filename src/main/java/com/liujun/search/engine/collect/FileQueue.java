package com.liujun.search.engine.collect;

import com.liujun.search.common.constant.PathCfg;
import com.liujun.search.common.constant.SymbolMsg;
import com.liujun.search.common.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件队列的实现
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/01
 */
public class FileQueue {

  /** 待爬取网页链接文件 */
  private static final String LINKS_FILE = "links.bin";

  /** 记录下的读取与写入的偏移的位置 */
  private static final String LINKS_FILE_OFFSET = "links_offset.bin";

  /** 偏移的文件 */
  private static final String PROCESS_LINK_FILEOFFSET =
      PathCfg.BASEPATH + PathCfg.COLLEC_PATH + LINKS_FILE_OFFSET;

  /** 操作的文件路径 */
  private static final String PRECESS_FILE = PathCfg.BASEPATH + PathCfg.COLLEC_PATH + LINKS_FILE;

  /** 最大buffer的大小 */
  private static final int MAX_BYTEBUFFERSIZE = 4096;

  /** 读取文件队列的字符的大小,即最大URL地址长度 */
  private static final int DEFAULT_READ_SIZE = 255;

  /** 日志 */
  private Logger log = LoggerFactory.getLogger(FileQueue.class);

  /** 实例对象 */
  public static final FileQueue INSTANCE = new FileQueue();

  /** 当前读取的偏移量 */
  private long readOffset = 0;

  /** 当前写入的偏移量 */
  private long writeOffset = 0;

  /** 文件输入流 */
  private FileInputStream readInput;

  /** 读取通道 */
  private FileChannel readChannel;

  /** buffer用于缓存数据 */
  private ByteBuffer readBuffer = ByteBuffer.allocateDirect(DEFAULT_READ_SIZE);

  /** 写入流对象 */
  private FileOutputStream writeOutput;

  /** 文件写入通道 */
  private FileChannel writeChannel;

  /** buffer用于缓存数据 */
  private ByteBuffer writeBuffer = ByteBuffer.allocateDirect(MAX_BYTEBUFFERSIZE);

  /** 打开文件队列 */
  public void openFileQueue() {
    // 读取偏移量信息
    this.readOffset();
    this.openWrite();
    this.openRead();
  }

  /** 从本地文件中读取偏移量信息 */
  public void readOffset() {

    // 1,check file exists
    File offsetFile = new File(PROCESS_LINK_FILEOFFSET);

    if (!offsetFile.exists()) {
      this.readOffset = 0;
      this.writeOffset = 0;
    } else {
      InputStream input = null;
      byte[] buffer = new byte[32];

      try {
        input = new FileInputStream(PROCESS_LINK_FILEOFFSET);
        int size = input.read(buffer);

        String value = new String(buffer, 0, size);
        int spitIndex = value.indexOf(SymbolMsg.COMMA);

        // 重新设置当前的集合信息
        this.readOffset = Long.parseLong(value.substring(0, spitIndex));
        this.writeOffset = Long.parseLong(value.substring(spitIndex + 1));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        log.error("FileQueue readAndSetOffset FileNotFoundException", e);
      } catch (IOException e) {
        e.printStackTrace();
        log.error("FileQueue readAndSetOffset IOException", e);
      } finally {
        IOUtils.close(input);
      }
    }
  }

  /** 将当前的写入与读取的偏移量写入至文件中 */
  public void writeOffset() {
    FileOutputStream outputStream = null;

    StringBuilder outOffset = new StringBuilder();
    outOffset.append(this.readOffset);
    outOffset.append(SymbolMsg.COMMA);
    outOffset.append(this.writeOffset);

    byte[] outbyte = outOffset.toString().getBytes();

    try {
      File outFile = new File(PROCESS_LINK_FILEOFFSET);

      if (!outFile.exists()) {
        outFile.createNewFile();
      }

      outputStream = new FileOutputStream(PROCESS_LINK_FILEOFFSET, false);
      outputStream.write(outbyte);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      log.error("FileQueue writeOffset FileNotFoundException", e);
    } catch (IOException e) {
      e.printStackTrace();
      log.error("FileQueue writeOffset IOException", e);
    } finally {
      IOUtils.close(outputStream);
    }
  }

  /** 进行文件队列的清理操作 */
  public void clean() {
    // 进行关闭操作
    this.closeAll();

    // 进行文件的清理操作
    new File(PRECESS_FILE).delete();

    // 删除offset文件
    new File(PROCESS_LINK_FILEOFFSET).delete();
  }

  /** 关闭操作 */
  public void closeAll() {
    this.closeWrite();
    this.closeRead();
  }

  /**
   * 插入单个地址
   *
   * @param address 地址信息
   * @return true 插入成功 false 插入失败
   */
  public boolean put(String address) {
    try {
      writeBuffer.put(address.getBytes(StandardCharsets.UTF_8));
      writeBuffer.flip();

      int offset = writeChannel.write(writeBuffer);
      // 数据写入完成后需要clean
      if (offset == writeBuffer.position()) {
        writeBuffer.clear();
      }

      // 将数据进行一次刷盘操作
      writeChannel.force(true);
      this.writeOffset = this.writeOffset + offset;

      return true;
    } catch (IOException e) {
      e.printStackTrace();
      log.error("FileQueue put IOException", e);
    }

    return false;
  }

  /**
   * 插入一个集合的数据
   *
   * @param addressList 地址集合信息
   */
  public boolean put(List<String> addressList) {

    try {
      int curOffset = 0;
      for (String address : addressList) {
        // 地址的转换后的buffer信息
        byte[] addressBytes = address.getBytes(StandardCharsets.UTF_8);

        writeBuffer.put(addressBytes);
        // 如果当前数据加待写入的数据未超过缓冲区，则写入缓冲区，待数据满了再写入文件中
        if (writeBuffer.position() + addressBytes.length < MAX_BYTEBUFFERSIZE) {
          continue;
        } else {
          // 将文件写入到通道中
          this.writeFileChannel(curOffset);
        }
      }

      // 当遍历完成后还有数据未写入需要进行写入操作
      if (writeBuffer.position() != 0) {
        this.writeFileChannel(curOffset);
      }

      // 将数据进行一次刷盘操作
      writeChannel.force(false);
      this.writeOffset = this.writeOffset + curOffset;

      return true;
    } catch (IOException e) {
      e.printStackTrace();
      log.error("FileQueue put list IOException", e);
    }

    return false;
  }

  /** 从文件队列中获取一个数据 */
  public String get() {
    String readData = null;

    try {
      long fileStartPostion = this.readChannel.position();

      long tmpReadOffset = 0;

      // 将数据读取到缓冲区中
      readChannel.read(readBuffer, fileStartPostion);

      readBuffer.flip();

      int startPostion = 0;
      int endPostion;

      for (int i = startPostion; i < readBuffer.limit(); i++) {
        // 如果当前找到了换行符，就添加到集合中
        if (readBuffer.get(i) == SymbolMsg.LINE_INT) {
          endPostion = i;
          byte[] buffCode = new byte[endPostion - startPostion + 1];

          readBuffer.get(buffCode, startPostion, buffCode.length);
          readData = new String(buffCode);

          // 偏移加上缓冲区大小
          tmpReadOffset = tmpReadOffset + buffCode.length;

          buffCode = null;
          break;
        }
      }

      // 读取完成清空队列
      readBuffer.clear();
      // 更新偏移位置
      this.readOffset = this.readOffset + tmpReadOffset;
      // 将位置写入到postion中
      this.readChannel.position(fileStartPostion + tmpReadOffset);
    } catch (IOException e) {
      e.printStackTrace();
      log.error("FileQueue get list offset IOException  ", e);
    }

    return readData;
  }

  /**
   * 通过指定的偏移位置，读取指定的行数
   *
   * @param offsetParam 起始始偏移位置
   * @param readNum 指定的读取行数
   * @return 读取到的数据集合
   */
  public List<String> readData(int offsetParam, int readNum) {

    List<String> result = new ArrayList<>();

    int startOffset = offsetParam;

    if (startOffset < 0) {
      startOffset = 0;
    }

    FileInputStream inputStream = null;
    FileChannel channel = null;
    ByteBuffer inputBuffer = ByteBuffer.allocate(2048);

    try {
      // 当前读取的文件路径
      inputStream = new FileInputStream(PRECESS_FILE);
      // 获取文件通道
      channel = inputStream.getChannel();
      // 设置起始位置
      channel.position(startOffset);

      int startPostion = 0;
      int endPostion = 0;

      // 声明读取的变量信息
      while ((channel.read(inputBuffer, startOffset)) > 0) {
        inputBuffer.flip();
        for (int i = startPostion; i < inputBuffer.limit(); i++) {
          // 如果当前找到了换行符，就添加到集合中
          if (inputBuffer.get(i) == SymbolMsg.LINE_INT) {
            endPostion = i;
            byte[] buffCode = new byte[endPostion - startPostion + 1];
            inputBuffer.get(buffCode, 0, buffCode.length);
            result.add(new String(buffCode));

            // 偏移加上缓冲区大小
            startOffset = startOffset + buffCode.length;
            startPostion = i+1;
            buffCode = null;

            if (result.size() >= readNum) {
              break;
            }
          }
        }
        if (result.size() >= readNum) {
          break;
        }

        // 如果当前缓冲级使用完成，则更新缓冲区为0
        if (inputBuffer.position() == inputBuffer.limit()) {
          inputBuffer.clear();
        } else {
          inputBuffer.compact();
        }
        // 重新更新起始顶点为0
        startPostion = 0;
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
      log.error("FileQueue openRead FileNotFoundException", e);
    } catch (IOException e) {
      e.printStackTrace();
      log.error("FileQueue openRead IOException", e);
    } finally {
      IOUtils.close(channel);
      IOUtils.close(inputStream);
    }

    return result;
  }

  /**
   * 将数据写入到文件通道中
   *
   * @param offsetParm 指定起始偏移位置
   * @return 写入数据的长度
   * @throws IOException 异常
   */
  private int writeFileChannel(int offsetParm) throws IOException {
    int curOffset = offsetParm;

    writeBuffer.flip();
    int offset = writeChannel.write(writeBuffer);

    // 数据写入完成后需要clean
    if (offset == writeBuffer.position()) {
      writeBuffer.clear();
    }
    curOffset += offset;

    return curOffset;
  }

  /** 打开读取队列 */
  private void openRead() {
    try {
      // 当前读取的文件路径
      readInput = new FileInputStream(PRECESS_FILE);
      // 获取文件通道
      readChannel = readInput.getChannel();
      // 设置当前默认的offset
      readChannel.position(readOffset);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      log.error("FileQueue openRead FileNotFoundException", e);
    } catch (IOException e) {
      e.printStackTrace();
      log.error("FileQueue openRead IOException", e);
    }
  }

  /** 打开放入队列 */
  private void openWrite() {
    try {
      // 文件写入流
      writeOutput = new FileOutputStream(PRECESS_FILE, true);
      // 文件输出通道
      writeChannel = writeOutput.getChannel();
      writeChannel.position(writeOffset);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      log.error("FileQueue openWrite FileNotFoundException", e);
    } catch (IOException e) {
      e.printStackTrace();
      log.error("FileQueue openWrite IOException", e);
    }
  }

  /** 关闭写入通道 */
  private void closeWrite() {
    // 关闭写入
    IOUtils.close(writeChannel);
    IOUtils.close(writeOutput);
  }

  /** 关闭读取 */
  private void closeRead() {
    // 再关闭读取
    IOUtils.close(readChannel);
    IOUtils.close(readInput);
  }
}
