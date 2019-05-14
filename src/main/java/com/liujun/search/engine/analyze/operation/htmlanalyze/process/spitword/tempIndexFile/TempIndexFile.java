package com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.tempIndexFile;

import com.liujun.search.common.io.CommonIOUtils;
import com.liujun.search.common.properties.SysPropertiesUtils;
import com.liujun.search.utilscode.io.constant.PathCfg;
import com.liujun.search.utilscode.io.constant.SymbolMsg;
import com.liujun.search.utilscode.io.constant.SysPropertyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 临时索引文件处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/25
 */
public class TempIndexFile {

  /** 临时索引文件前缀名 */
  private static final String PREFIX_NAME = "tem_index";

  /** 文件后缀名 */
  private static final String SUFFIX_NAME = ".tempindex";

  /** 基础路径 */
  private static final String BASEPATH =
      SysPropertiesUtils.getInstance().getValue(SysPropertyEnum.FILE_PROCESS_PATH);

  /** 临时索引文件大小 */
  private static final int TEMP_MAX_INDEX =
      SysPropertiesUtils.getInstance()
          .getIntegerValueOrDef(SysPropertyEnum.ANALYZE_MAX_FILE, 1 * 1024 * 1024 * 1024);

  /** 索引占用数字的位数 */
  private static final int MAX_INDEX_LENGTH = 4;

  /** 输出的索引文件编号 */
  private AtomicInteger indexFile = new AtomicInteger(0);

  /** 当前文件大小 */
  private AtomicLong currFileSize = new AtomicLong(0);

  /** 临时索引文件 */
  public static final TempIndexFile INSTANCE = new TempIndexFile();

  /** 输出流 */
  private FileOutputStream fileOutputStream;

  /** 文件通道 */
  private BufferedOutputStream bufferOutputStream;

  /** 独占锁 */
  private Lock lock = new ReentrantLock();

  private Logger logger = LoggerFactory.getLogger(TempIndexFile.class);

  public TempIndexFile() {
    // 进行首次的文件读取操作
    this.firstRead();
  }

  /**
   * 写入数据，
   *
   * @param keyIndex 单词编号
   * @param docId 网页编号编号
   */
  public void writeData(int keyIndex, int docId) {
    // 1,检查大小是否已经超过了大小
    // 将数据转化为写入行数据
    String line = parseWriteLine(keyIndex, docId);
    byte[] outBufferedData = line.getBytes();

    // 检查并切换文件
    this.checkAndSwitchFile(outBufferedData.length);

    try {
      // 进行数据的写入操作
      this.bufferOutputStream.write(outBufferedData);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("TempIndexFile write data IOException", e);
    }

    // 写入完成后修改文件大小
    this.currFileSize.addAndGet(outBufferedData.length);
  }

  public void flush() {
    try {
      this.bufferOutputStream.flush();
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("TempIndexFile write disk flush IOException", e);
    }
  }

  /**
   * 检查并切换文件
   *
   * @param outbufferLength
   */
  private void checkAndSwitchFile(int outbufferLength) {
    // 当超过单文件大小后，则进行切换操作,需要加锁以及二次检查确认
    if (this.currFileSize.get() + outbufferLength > TEMP_MAX_INDEX) {
      try {
        lock.lock();
        // 1，将再次检查文件大小是否符合要求,进行二次确认,防止高并发问题
        if (this.currFileSize.get() + outbufferLength > TEMP_MAX_INDEX) {
          // 关闭所有
          this.closeAll();
          // 进行索引的加1操作
          this.indexFile.incrementAndGet();
          // 将文件大小修改为新的大小
          this.currFileSize.set(0);
          // 进行将文件流重新打开
          this.openFile();
        }
      } catch (Exception e) {
        e.printStackTrace();
        logger.error("TempIndexfile Exception:", e);
      } finally {
        lock.unlock();
      }
    }
  }

  /** 进行所有流程的关闭操作 */
  public void closeAll() {
    // 1,关闭当前骗过你入文件
    this.close(this.bufferOutputStream);
    this.close(this.fileOutputStream);
  }

  /**
   * 进行关闭操作
   *
   * @param stream
   */
  public void close(Closeable stream) {
    CommonIOUtils.close(stream);
  }

  /**
   * 进行写入行数据的转化
   *
   * @param keyWordIndex 网页单词编号
   * @param docId 网页编号
   * @return 写入文件的数据
   */
  public String parseWriteLine(int keyWordIndex, int docId) {
    StringBuilder outLine = new StringBuilder();

    outLine.append(keyWordIndex);
    outLine.append(SymbolMsg.DATA_COLUMN);
    outLine.append(docId);
    outLine.append(SymbolMsg.LINE);

    return outLine.toString();
  }

  /** 进行首次的打开文件操作 */
  public void firstRead() {
    // 1,检查临时索引文件是否已经存
    String basePath = this.getTempIndexPath();

    File outFile = new File(basePath);

    // 如果已经存在文件,则找到最大索引
    if (outFile.exists()) {
      String[] fileList = outFile.list();

      if (null != fileList && fileList.length > 1) {
        Arrays.sort(fileList);
      }

      if (fileList.length > 0) {
        int lastIndex = getLastIndex(fileList[fileList.length - 1]);
        // 设置文件索引号
        indexFile.set(lastIndex);
      }
    }
    // 如果文件不存在，则创建第文件夹
    else {
      // 创建目录
      this.pathCheckAndMake();
    }

    // 进行文件初始化大小设置
    this.setFileSize();

    // 打开文件
    this.openFile();
  }

  /** 进行当前文件大小的设置操作 */
  private void setFileSize() {
    String path = this.getTempIndexPath();
    String fileNmae = this.getFileName();

    String filePath = path + SymbolMsg.PATH + fileNmae;

    File currFile = new File(filePath);

    if (currFile.exists()) {
      this.currFileSize.set(currFile.length());
    } else {
      this.currFileSize.set(0);
    }
  }

  /** 打开文件操作 */
  public void openFile() {
    String path = this.getTempIndexPath();
    String fileNmae = this.getFileName();

    String filePath = path + SymbolMsg.PATH + fileNmae;

    try {
      this.fileOutputStream = new FileOutputStream(filePath, true);
      this.bufferOutputStream = new BufferedOutputStream(fileOutputStream);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("TempIndexFile openfile Exception FileNotFoundException", e);
      throw new RuntimeException("tempindexfile  not FileNotFoundException");
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("TempIndexFile openfile Exception IOException", e);
      throw new RuntimeException("tempindexfile  IOException");
    }
  }

  /**
   * 获取最后一个索引文件
   *
   * @param file 文件名
   * @return
   */
  private int getLastIndex(String file) {
    int underIndex = file.lastIndexOf(SymbolMsg.UNDER_LINE) + 1;
    int suffixIndex = file.indexOf(SUFFIX_NAME);

    String indexStr = file.substring(underIndex, suffixIndex);

    return Integer.parseInt(indexStr);
  }

  private void pathCheckAndMake() {
    String basePath = this.getTempIndexPath();

    File makePath = new File(basePath);

    // 当文件不存在时，进行文件夹的创建操作
    if (!makePath.exists()) {
      makePath.mkdirs();
    }
  }

  /**
   * 临时索引文件目录
   *
   * @return 临时索引文件目录
   */
  String getTempIndexPath() {
    StringBuilder tempIndexPath = new StringBuilder();

    tempIndexPath.append(BASEPATH);
    tempIndexPath.append(SymbolMsg.PATH);
    tempIndexPath.append(PathCfg.ANALYZE_PATH);
    tempIndexPath.append(SymbolMsg.PATH);
    tempIndexPath.append(PathCfg.ANALYZE_PATH_TEMP_INDEX_PATH);

    return tempIndexPath.toString();
  }

  /**
   * 获取文件名
   *
   * @return 文件名
   */
  String getFileName() {
    StringBuilder fileName = new StringBuilder();

    fileName.append(PREFIX_NAME);
    fileName.append(SymbolMsg.UNDER_LINE);
    fileName.append(nameIndex(indexFile.get()));
    fileName.append(SUFFIX_NAME);

    return fileName.toString();
  }

  /**
   * 获取名称索引输出值
   *
   * @return
   */
  private String nameIndex(int value) {
    String valueStr = String.valueOf(value);

    StringBuilder outIndex = new StringBuilder();

    for (int i = 0; i < MAX_INDEX_LENGTH - valueStr.length(); i++) {
      outIndex.append("0");
    }
    outIndex.append(valueStr);

    return outIndex.toString();
  }

  /** 进行清理操作 */
  public void clean() {

    // 进行所有文件的关闭操作
    this.closeAll();

    String basePath = this.getTempIndexPath();

    File basePathFile = new File(basePath);

    if (basePathFile.exists()) {
      File[] files = basePathFile.listFiles();

      for (File fileItem : files) {
        fileItem.delete();
      }
    }
  }

  int getFileIndex() {
    return indexFile.get();
  }

  long getCurrFileSize() {
    return currFileSize.get();
  }
}
