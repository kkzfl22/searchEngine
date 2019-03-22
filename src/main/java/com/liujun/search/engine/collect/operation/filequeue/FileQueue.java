package com.liujun.search.engine.collect.operation.filequeue;

import com.liujun.search.common.constant.PathCfg;
import com.liujun.search.common.constant.SymbolMsg;
import com.liujun.search.common.io.IOUtils;
import com.liujun.search.engine.collect.constant.WebEntryEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 文件队列的实现
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/01
 */
public class FileQueue {

  /** 文件队列名称的前缀信息 */
  private static final String LINKS_PREFIX_NAME = "links_";

  /** 文件的后缀信息 */
  public static final String SUFFIX_NAME = ".bin";

  /** 网页链接文件夹信息 */
  public static final String LINKED_PATH = "links";

  /** 偏移的文件 */
  public static final String PROCESS_LINK_BASE_PATH = PathCfg.BASEPATH + PathCfg.COLLEC_PATH;

  /** 最大buffer的大小 */
  private static final int MAX_BYTEBUFFERSIZE = 4096;

  /** 读取文件队列的字符的大小,即最大URL地址长度 */
  private static final int DEFAULT_READ_SIZE = 255;

  /** 日志 */
  private Logger log = LoggerFactory.getLogger(FileQueue.class);

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

  /** 用来存储链接的文件，按网站来进行 */
  private final String linksFile;

  /** 文件偏移量 */
  private final FileQueueOffset fileOffset;

  public FileQueue(String linksFile) {

    this.linksFile = LINKS_PREFIX_NAME + linksFile + SUFFIX_NAME;
    // 初始化文件偏移队列
    fileOffset = new FileQueueOffset(linksFile);

    // 进行文件偏移位置的读取操作
    fileOffset.readOffset();
  }

  /**
   * 获取文件队列，
   *
   * @param entry 入口信息
   * @return
   */
  public static FileQueue GetQueue(WebEntryEnum entry) {

    FileQueue instance = new FileQueue(entry.getFlag());
    // 检查并创建基础文件夹
    instance.checkAndCreateDir();
    // 进行首个入口的写入操作
    instance.firstWriteEntry(entry);

    return instance;
  }

  /** 进行首次文件的入口写入 */
  public void firstWriteEntry(WebEntryEnum entry) {
    // 1,检查当前文件是否已经存在
    File fileExists = new File(this.getLinksFile());
    // 如果文件不存在，或者已经被重置，则进行首次的写入操作
    if (!fileExists.exists() || this.fileOffset.getWriteOffset() == 0) {
      this.openWrite();
      this.put(entry.getUrlAddress());
      this.closeWrite();
    }
  }

  /** 打开文件队列 */
  public void openFileQueue() {
    // 读取偏移量信息
    this.openWrite();
    this.openRead();
  }

  /** 写入偏移位置 */
  public void writeOffset() {
    fileOffset.writeOffset();
  }

  /** 读取偏移位置 */
  public void readOffset() {
    fileOffset.readOffset();
  }

  /** 进行文件队列的清理操作,即将所有的标识都重置为0，再进行操作 */
  public void clean() {
    this.fileOffset.setReadOffset(0);
    this.fileOffset.setWriteOffset(0);

    // 删除offset文件
    fileOffset.clean();
    fileOffset.writeOffset();

    // 将所有通道进行关闭一次
    this.closeAll();

    // 进行文件的清理操作
    new File(this.getLinksFile()).delete();
  }

  /**
   * 插入单个地址
   *
   * @param address 地址信息
   * @return true 插入成功 false 插入失败
   */
  public boolean put(String address) {
    try {
      // 添加换行符
      address = dataAddLine(address);

      // 将单个数据写入到文件通道中
      int offset = this.writeOneDataToFileChannel(address);

      // 将数据进行一次刷盘操作
      writeChannel.force(true);
      // 更新postion的位置信息
      this.fileOffset.setWriteOffset(this.fileOffset.getWriteOffset() + offset);

      return true;
    } catch (IOException e) {
      e.printStackTrace();
      log.error("FileQueue put IOException", e);
    } finally {
      // 执行最后的清理操作
      writeBuffer.clear();
    }

    return false;
  }

  /**
   * 插入一个集合的数据
   *
   * @param addressList 地址集合信息
   */
  public boolean put(Collection<String> addressList) {
    try {
      int curOffset = 0;
      for (String address : addressList) {
        // 添加换行符
        address = dataAddLine(address);

        // 如果长度大于缓冲区,则写入文件
        if (address.length() + writeBuffer.position() >= MAX_BYTEBUFFERSIZE) {
          curOffset += this.writeBufferOrChannel(address);
        } else {
          // 如果文件还未超过缓冲区，则写入缓冲区
          byte[] valueBytes = address.getBytes(StandardCharsets.UTF_8);
          writeBuffer.put(valueBytes);
        }
      }

      // 数据结束后，将缓冲区的数据写入至文件中
      if (writeBuffer.position() != 0) {
        curOffset += this.bufferwriteChannel();
      }

      // 将数据进行一次刷盘操作
      writeChannel.force(false);
      // 写入新的位置
      this.fileOffset.setWriteOffset(fileOffset.getWriteOffset() + curOffset);

      return true;
    } catch (IOException e) {
      e.printStackTrace();
      log.error("FileQueue put list IOException", e);
    } finally {
      writeBuffer.clear();
    }

    return false;
  }

  /** 从文件队列中获取一个数据 */
  public String get() {
    String readData = null;

    StringBuilder msg = new StringBuilder();

    try {

      boolean readFinish = false;

      while (!readFinish && readChannel.read(readBuffer, this.fileOffset.getReadOffset()) > 0) {
        long tmpReadOffset = 0;

        readBuffer.flip();

        int startPostion = 0;
        int endPostion;

        for (int i = startPostion; i < readBuffer.limit(); i++) {
          // 如果当前找到了换行符，就添加到集合中
          if (readBuffer.get(i) == SymbolMsg.LINE_INT) {
            endPostion = i;

            // 进行数据的获取，不包括换行符
            readData = this.getData(endPostion, startPostion);

            msg.append(readData);
            // 偏移加上缓冲区大小,再加缓冲区的时间需要再加1位，加上换行符号
            tmpReadOffset = tmpReadOffset + (endPostion - startPostion + 1);

            // 标识当前完成
            readFinish = true;

            break;
          }
        }

        // 当前遍历结束了，未找到结束符，需要将当前数据加入到集合中
        if (!readFinish) {
          // 获取数据
          readData = this.getData(readBuffer.limit(), 0);
          msg.append(readData);
          // 偏移加上缓冲区大小
          tmpReadOffset = tmpReadOffset + readBuffer.limit();
          readBuffer.clear();
        }

        // 更新偏移位置
        this.fileOffset.setReadOffset(this.fileOffset.getReadOffset() + tmpReadOffset);
      }

      // 将位置写入到postion中
      this.readChannel.position(this.fileOffset.getReadOffset());
    } catch (IOException e) {
      e.printStackTrace();
      log.error("FileQueue get list offset IOException  ", e);
    } finally {
      // 读取完成清空队列
      readBuffer.clear();
    }

    return msg.toString();
  }

  /** 打开读取队列 */
  private void openRead() {
    try {
      // 文件队列信息
      String linksFile = this.getLinksFile();
      // 当前读取的文件路径
      readInput = new FileInputStream(linksFile);
      // 获取文件通道
      readChannel = readInput.getChannel();
      // 设置当前默认的offset
      readChannel.position(this.fileOffset.getReadOffset());
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
      // 文件队列信息
      String linksFile = this.getLinksFile();

      // 文件写入流
      writeOutput = new FileOutputStream(linksFile, true);
      // 文件输出通道
      writeChannel = writeOutput.getChannel();
      // 指定输出位置
      writeChannel.position(this.fileOffset.getWriteOffset());
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

  /**
   * 获取存储链接的网页文件路径信息
   *
   * @return 链接文件信息
   */
  private String getLinksFile() {
    return PROCESS_LINK_BASE_PATH + File.separator + LINKED_PATH + File.separator + this.linksFile;
  }

  /** 进行文件夹的创建操作 */
  private void checkAndCreateDir() {
    String baseDir = PROCESS_LINK_BASE_PATH + File.separator + LINKED_PATH + File.separator;

    // 检查并创建文件夹
    File outDir = new File(baseDir);
    if (!outDir.exists()) {
      outDir.mkdirs();
    }
  }

  /**
   * 添加换行符信息
   *
   * @param address
   * @return
   */
  private String dataAddLine(String address) {
    String result = address + SymbolMsg.LINE;
    return result;
  }

  /**
   * 数据进行清除换行符号信息
   *
   * @param data
   * @return
   */
  private String dataCleanLine(String data) {
    String address = data.trim();
    return address;
  }

  /** 关闭操作 */
  private void closeAll() {
    this.closeWrite();
    this.closeRead();
  }

  /**
   * 进行写入数据至缓冲区操作，或者文件中
   *
   * <p>如果缓冲区满了，就会写入缓冲区
   *
   * <p>最后一次如果未满，则数据会留在缓冲区中，待下一次缓冲区满了，再写入至文件中
   *
   * @param data 待写入的数据信息
   * @return 写入数据的长度
   * @throws IOException
   */
  private int writeBufferOrChannel(String data) throws IOException {

    int dataOffset = 0;

    // 当前写入数据的偏移位置
    int offset;
    // 待写入字符串长度
    int dataLength = data.length();
    byte[] valueBytes = data.getBytes(StandardCharsets.UTF_8);

    // 开始操作的位置
    int startPos = 0;

    while (startPos < dataLength) {
      // 计算写入数据的总长度
      int oldPos = writeBuffer.position();
      // 计算写入数据的长度
      int buffLength = this.countWriteLength(startPos, dataLength);

      writeBuffer.put(valueBytes, startPos, buffLength);

      // 因为当前需要进行当前数据的遍历，需要减去buffer中的已有数据位置
      startPos += writeBuffer.position() - oldPos;

      // 如果通道满了，则写入文件中
      if (writeBuffer.position() == writeBuffer.limit()) {
        writeBuffer.flip();
        offset = writeChannel.write(writeBuffer);
        dataOffset += offset;
        // 进行压缩操作
        writeBuffer.compact();
      }
    }

    // 返回整个偏移的位置
    return dataOffset;
  }

  /**
   * 将缓冲区中的数据写入至文件中
   *
   * <p>一般用于多集合数据的写入，在最后一次时将缓冲区中的数据写入至文件中
   *
   * @return 写入数据的长度
   * @throws IOException
   */
  private int bufferwriteChannel() throws IOException {

    int dataOffset = 0;

    // 当前写入数据的偏移位置
    int offset;
    writeBuffer.flip();
    offset = writeChannel.write(writeBuffer);
    dataOffset += offset;

    // 进行压缩操作
    writeBuffer.clear();

    // 返回整个偏移的位置
    return dataOffset;
  }

  /**
   * 进行单个数据将数据写入通道中
   *
   * <p>检查通道中的数据，写入至文件中，
   *
   * <p>当前所有的数据都会被写入文件通道中
   *
   * @param data 待写入的数据信息
   * @return 写入数据的长度
   * @throws IOException
   */
  private int writeOneDataToFileChannel(String data) throws IOException {

    int dataOffset = 0;

    // 当前写入数据的偏移位置
    int offset;
    // 待写入字符串长度
    int dataLength = data.length();
    byte[] valueBytes = data.getBytes(StandardCharsets.UTF_8);

    // 开始操作的位置
    int startPos = 0;

    while (startPos < dataLength) {
      // 计算写入数据的总长度
      int oldPos = writeBuffer.position();
      // 计算写入数据的长度
      int buffLength = this.countWriteLength(startPos, dataLength);

      writeBuffer.put(valueBytes, startPos, buffLength);
      writeBuffer.flip();

      offset = writeChannel.write(writeBuffer);
      dataOffset += offset;
      // 因为当前需要进行当前数据的遍历，需要减去buffer中的已有数据位置
      startPos += offset - oldPos;

      // 进行压缩操作
      writeBuffer.compact();
    }

    // 返回整个偏移的位置
    return dataOffset;
  }

  /**
   * 计算写入数据的长度
   *
   * @param startPos 起始位置
   * @param writeLength 待写入数据的长度
   * @return 当前写入数据的长度
   */
  private int countWriteLength(int startPos, int writeLength) {
    int buffLength;
    // 计算可写入空间
    int writeLimit = writeBuffer.limit() - writeBuffer.position();

    if (writeLength - startPos > writeLimit) {
      buffLength = writeLimit;
    } else {
      buffLength = writeLength - startPos;
    }

    return buffLength;
  }

  /**
   * 进行当前结束字符的数据获取
   *
   * @param endPostion
   * @param startPostion
   * @return
   */
  private String getData(int endPostion, int startPostion) {
    byte[] buffCode = new byte[endPostion - startPostion];
    readBuffer.get(buffCode, startPostion, buffCode.length);
    return new String(buffCode);
  }
}
