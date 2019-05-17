package com.liujun.search.engine.index.outputDescIndex;

import com.liujun.search.common.io.CommonIOUtils;
import com.liujun.search.common.properties.SysPropertiesUtils;
import com.liujun.search.engine.index.pojo.TempIndexData;
import com.liujun.search.utilscode.io.constant.SymbolMsg;
import com.liujun.search.utilscode.io.constant.SysPropertyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 倒排索引文件的输出操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/11
 */
public class DescIndexOutput {

  public static final DescIndexOutput INSTANCE = new DescIndexOutput();

  /** 最大文件 */
  private long maxFileSize;

  /** 当前的文件大小 */
  private AtomicLong currFileSize = new AtomicLong(0);

  /** 输出的流对象 */
  private FileOutputStream fileOutput;

  /** 缓冲输出对象 */
  private BufferedOutputStream bufferedOutput;

  /** 独占锁对象 */
  private Lock lock = new ReentrantLock();

  private Logger logger = LoggerFactory.getLogger(DescIndexOutput.class);

  public DescIndexOutput() {
    int dataMaxSize =
        SysPropertiesUtils.getInstance()
            .getIntegerValueOrDef(SysPropertyEnum.DESC_INDEX_MAXFILE_SIZE, 1 * 1024 * 1024 * 512);

    // 设置单文件最大值
    maxFileSize = dataMaxSize;

    openFile();
  }

  public void openFile() {
    String outFile = DescIndexFileName.INSTANCE.getDescIndexFileName();

    try {
      // 要开记录偏移文件信息
      DescIndexOffsetOutput.INSTANCE.open();

      fileOutput = new FileOutputStream(outFile);
      bufferedOutput = new BufferedOutputStream(fileOutput);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("DescIndexOutput  openFile FileNotFoundException ", e);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("DescIndexOutput openFile IOException ", e);
    }
  }

  /**
   * 进行倒排索引文件的输出操作
   *
   * @param tempIndexList 索引文件信息
   * @return 写入的长度
   */
  public int writeIndex(List<TempIndexData> tempIndexList) {

    if (null == tempIndexList || tempIndexList.isEmpty()) {
      return 0;
    }

    byte[] outdata = this.getOutData(tempIndexList);

    // 进行文件输出切换的检查
    this.checkAndSwitch(outdata);

    // 将数据进行输出
    try {

      // 进行偏移信息的输出
      int wordId = tempIndexList.get(0).getTempId();
      // 长度减1，因为需要去掉换行符
      DescIndexOffsetOutput.INSTANCE.write(wordId, currFileSize.get(), outdata.length - 1);

      // 进行数据的写入
      bufferedOutput.write(outdata);

      // 进行文件大小的变更操作
      this.currFileSize.addAndGet(outdata.length);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("DescIndexOutput writeIndex IOException ", e);
    }

    return outdata.length;
  }

  /**
   * 检查并进行文件的切换操作
   *
   * @param outdata 输出
   */
  private void checkAndSwitch(byte[] outdata) {
    if (currFileSize.get() + outdata.length > maxFileSize) {
      try {
        lock.lock();

        if (currFileSize.get() + outdata.length > maxFileSize) {
          // 1,关闭当前文件
          this.close();

          // 关闭偏移信息文件
          DescIndexOffsetOutput.INSTANCE.close();

          // 2,进行当前文件索号的增加操作
          DescIndexFileName.INSTANCE.getIndex().incrementAndGet();
          // 重新打开文件
          this.openFile();

          // 设置文件大小为0
          this.currFileSize.set(0);
        }
      } catch (Exception e) {
        e.printStackTrace();
        logger.error("DescIndexOutput checkAndSwitch Exception ", e);
      } finally {
        lock.unlock();
      }
    }
  }

  /** 进行文件的关闭操作 */
  public void close() {
    // 优先关闭offset
    DescIndexOffsetOutput.INSTANCE.close();

    CommonIOUtils.close(bufferedOutput);
    CommonIOUtils.close(fileOutput);
  }

  /**
   * 获取输出字节信息
   *
   * @param tempIndexList 索引集合
   * @return
   */
  private byte[] getOutData(List<TempIndexData> tempIndexList) {
    StringBuilder outData = new StringBuilder();

    outData.append(tempIndexList.get(0).getTempId());
    outData.append(SymbolMsg.DATA_COLUMN);

    for (TempIndexData indexData : tempIndexList) {
      outData.append(indexData.getDocSeqId());
      outData.append(SymbolMsg.COMMA);
    }
    outData.deleteCharAt(outData.length() - 1);
    outData.append(SymbolMsg.LINE);

    String outDataValue = outData.toString();

    // 以utf8的编码进行输出操作
    return outDataValue.getBytes(StandardCharsets.UTF_8);
  }
}
