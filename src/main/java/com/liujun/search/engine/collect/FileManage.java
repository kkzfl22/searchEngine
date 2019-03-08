package com.liujun.search.engine.collect;

import com.liujun.search.common.constant.PathCfg;
import com.liujun.search.common.constant.PropertiesEnum;
import com.liujun.search.common.io.IOUtils;
import com.liujun.search.common.properties.PropertiesUtilProcess;
import com.liujun.search.engine.collect.html.FileChunkMsg;
import com.liujun.search.engine.collect.html.HtmlRawInfoBusi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 文件管理器
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/05
 */
public class FileManage {

  /** 单文件的最大大小，限制为1Gb */
  private static final int FILE_SIZE = 1 * 1024 * 1024 * 1024;

  /** 待爬取网页链接文件 */
  private static final String FILE_HTML_DOC = "html_proc_";

  /** 文件后缀名 */
  private static final String FILE_OUT_PREFIX = ".txt";

  /** 默认写入buffer的大小 */
  private static final int DEFABULT_SIZE = 1024 * 4;

  /** 实例对象 */
  public static final FileManage INSTANCE = new FileManage();

  /** logger对象 */
  private Logger logger = LoggerFactory.getLogger(FileManage.class);

  /** 文件输出对象 */
  private FileOutputStream outputStream;

  /** 文件通道对象 */
  private FileChannel outputChannel;

  /** 写入数据的缓存 */
  private ByteBuffer buffer = ByteBuffer.allocate(1024 * 4);

  /** 当前的偏移位置 */
  private long currPostion;

  /** 当前chunk的索引号 */
  private AtomicInteger fileIndex = new AtomicInteger(0);

  private FileManage() {
    this.load();
    this.openFile();
  }

  public long getCurrPostion() {
    return currPostion;
  }

  public int getFileIndex() {
    return fileIndex.get();
  }

  public void openFile() {
    // 获取指定的文件
    String outputName =
        PathCfg.BASEPATH + PathCfg.COLLEC_PATH + FILE_HTML_DOC + fileIndex.get() + FILE_OUT_PREFIX;

    try {
      // 修改为允许文件进行追加操作
      outputStream = new FileOutputStream(outputName, true);
      outputChannel = outputStream.getChannel();
      currPostion = outputChannel.position();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("FileManage openFile FileNotFoundException:", e);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("FileManage openFile IOException:", e);
    }
  }

  /** 进行文件的关闭操作 */
  public void closeFile() {
    IOUtils.close(outputChannel);
    IOUtils.close(outputStream);
  }

  /**
   * 向文件管理系统中放入数据
   *
   * @param data 放的网页的html的内容
   * @return 数据存储的位置相关的信息
   */
  public FileChunkMsg putData(long htmlId, String data) {

    FileChunkMsg result = null;

    int dataLength = data.length();

    // 检查文件是否超过限制
    if (currPostion + dataLength > FILE_SIZE) {
      int valIndex = fileIndex.get();

      Lock lock = new ReentrantLock();

      try {
        lock.lock();

        // 进行值的原子更新操作，则避免发生
        boolean upd = fileIndex.compareAndSet(valIndex, valIndex + 1);

        // 如果当前更新成功，则进行更新操作
        if (upd) {
          // 关闭文件对象
          this.closeFile();
          // 进行文件打开操作
          this.openFile();
        }
      } finally {
        lock.unlock();
      }
    }

    HtmlRawInfoBusi rawBusi = new HtmlRawInfoBusi(htmlId, data);

    try {
      int writeLength = 0;
      byte[] outDataBuffer = rawBusi.getLineData();

      int outDataLength = outDataBuffer.length;

      // 进行数据的封装操作
      result = new FileChunkMsg(this.fileIndex.get(), this.currPostion, outDataLength);

      // 如果一次数据没有写入完毕，需要再次写入,直到完成
      while (writeLength < outDataLength) {

        // 如果未超过大小，则直接 进行写入
        if (writeLength + DEFABULT_SIZE <= outDataLength) {
          buffer.put(outDataBuffer, writeLength, DEFABULT_SIZE);
        }
        // 如果超过了大小，则按照大小进行实际写入操作
        else {
          int outBufferSize = outDataLength - writeLength;
          buffer.put(outDataBuffer, writeLength, outBufferSize);
        }

        buffer.flip();
        // 进行数据写入操作
        writeLength += outputChannel.write(buffer);

        // 进行压缩操作
        buffer.compact();
      }
      // 更新写入的位置信息
      currPostion = currPostion + writeLength;

      // 写入完成，数据被清空
      buffer.clear();

    } catch (IOException e) {
      e.printStackTrace();
      logger.error("FileManage putData IOException:", e);
    }

    return result;
  }

  /** 将当前的临时指针等信息写入文件中,保存 */
  public void save() {
    PropertiesUtilProcess.INSTANCE.setValue(
        PropertiesEnum.FILE_INDEX_OUT.getKey(), String.valueOf(fileIndex.get()));
    PropertiesUtilProcess.INSTANCE.setValue(
        PropertiesEnum.FILE_POSTION_OUT.getKey(), String.valueOf(currPostion));
    PropertiesUtilProcess.INSTANCE.saveProperties();
  }

  /** 进行数据加载 */
  public void load() {
    fileIndex.set(
        PropertiesUtilProcess.INSTANCE.getIntValueOrDef(PropertiesEnum.FILE_INDEX_OUT.getKey(), 0));
    currPostion =
        PropertiesUtilProcess.INSTANCE.getLongValueOrDef(
            PropertiesEnum.FILE_POSTION_OUT.getKey(), 0);
  }

  /**
   * 获取数据内容
   *
   * @param chunkmsg 文件块信息
   * @return 数据的内容信息
   */
  public String getData(FileChunkMsg chunkmsg) {

    // 进行数据的读取操作
    FileInputStream input = null;
    FileChannel channel = null;

    String outputName =
        PathCfg.BASEPATH
            + PathCfg.COLLEC_PATH
            + FILE_HTML_DOC
            + chunkmsg.getFileIndex()
            + FILE_OUT_PREFIX;

    try {
      input = new FileInputStream(outputName);
      channel = input.getChannel();

      StringBuilder result = new StringBuilder();

      ByteBuffer buffer = ByteBuffer.allocate(1024);
      channel.position(chunkmsg.getStartPostion());

      int readLength = 0;
      int readNum = 0;

      // 进行循环数据读取
      while (readLength < chunkmsg.getDataLength()) {
        readNum = channel.read(buffer);
        if (readNum > 0) {
          buffer.flip();
          byte[] dataBuffer = new byte[readNum];
          buffer.get(dataBuffer);
          // 将数据放入到String字符串
          result.append(new String(dataBuffer, StandardCharsets.UTF_8));
          buffer.clear();
          // 更新读取的总条度信息
          readLength += readNum;
        }
      }

      return result.toString();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("FileManage getData FileNotFoundException:", e);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("FileManage getData IOException:", e);
    } finally {
      IOUtils.close(channel);
      IOUtils.close(input);
    }

    return null;
  }
}
