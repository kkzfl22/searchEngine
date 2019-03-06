package com.liujun.search.engine.collect;

import com.liujun.search.common.constant.PropertyEnum;
import com.liujun.search.common.io.IOUtils;
import com.liujun.search.common.properties.PropertiesUtils;
import com.liujun.search.engine.collect.html.FileChunkMsg;
import com.liujun.search.engine.collect.html.HtmlRawInfoBusi;
import org.apache.logging.slf4j.Log4jLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
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

  public static final FileManage INSTANCE = new FileManage();

  /** 单文件的最大大小，限制为1Gb */
  private static final int FILE_SIZE = 1 * 1024 * 1024 * 1024;

  /** 基础路径 */
  private static final String BASEPATH =
      PropertiesUtils.getInstance().getValue(PropertyEnum.FILE_PROCESS_PATH);

  /** 数据收集的目录 */
  private static final String COLLEC_PATH = "collect/";

  /** 待爬取网页链接文件 */
  private static final String FILE_HTML_DOC = "html_proc_";

  /** 文件后缀名 */
  private static final String FILE_OUT_PREFIX = ".txt";

  /** 用于文件的索引号,以及当前写入偏移量的保存 */
  private static final String FIlE_INDEX_OUT = "file_index_out.index";

  /** 默认写入buffer的大小 */
  private static final int DEFABULT_SIZE = 1024 * 4;

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

  private FileManage() {}

  public void openFile() {
    // 获取指定的文件
    String outputName = BASEPATH + COLLEC_PATH + FILE_HTML_DOC + fileIndex.get() + FILE_OUT_PREFIX;

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

      HtmlRawInfoBusi rawBusi = new HtmlRawInfoBusi(htmlId, data);

      try {
        int writeLength = 0;
        byte[] outDataBuffer = rawBusi.getLineData();

        int outDataLength = outDataBuffer.length;

        // 进行数据的封装操作
        result = new FileChunkMsg(this.fileIndex.get(), this.currPostion, outDataLength);

        // 如果一次数据没有写入完毕，需要再次写入,直到完成
        while (writeLength <= outDataLength) {

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
        // 写入完成，数据被清空
        buffer.clear();

      } catch (IOException e) {
        e.printStackTrace();
        logger.error("FileManage putData IOException:", e);
      }
    }

    return result;
  }

  /**
   * 获取数据内容
   *
   * @param chunkmsg 文件块信息
   * @return 数据的内容信息
   */
  public String getData(FileChunkMsg chunkmsg) {
    String msg = null;

    // 使用kmp算法进行字符查找操作，即查找结尾字符串

    return msg;
  }
}
