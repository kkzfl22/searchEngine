package com.liujun.search.engine.index.mergeIndex;

import com.liujun.search.common.io.CommonIOUtils;
import com.liujun.search.engine.index.pojo.TempIndexData;
import com.liujun.search.utilscode.io.constant.SymbolMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/10
 */
public class SortIndexFileReader {

  private FileReader fileRead;

  private BufferedReader bufferedReader;

  /** 文件读取完毕的标识 */
  private boolean readFinish = false;

  private Logger logger = LoggerFactory.getLogger(SortIndexFileReader.class);

  public SortIndexFileReader(File file) {
    // 打开文件操作
    this.openFile(file);
  }

  /**
   * 进行文件打开操作
   *
   * @param file 文件信息
   */
  private void openFile(File file) {
    try {
      fileRead = new FileReader(file);
      bufferedReader = new BufferedReader(fileRead);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("SortIndexFileReader open file FileNotFoundException", e);
    }
  }

  /**
   * 进行数据的读取操作
   *
   * @return
   */
  public TempIndexData getIndexData() {
    try {
      String line = bufferedReader.readLine();

      if (line != null) {
        return this.parseEntity(line);
      } else {
        readFinish = true;
      }
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("SortIndexFileReader getIndexData  IOException", e);
    }

    return null;
  }

  public boolean finishFlag() {
    return readFinish;
  }

  /** 进行文件的关闭操作 */
  public void closeFile() {
    CommonIOUtils.close(bufferedReader);
    CommonIOUtils.close(fileRead);
  }

  /**
   * 将字符转化为java对象
   *
   * @param line
   * @return
   */
  private TempIndexData parseEntity(String line) {
    String[] data = line.split(SymbolMsg.DATA_COLUMN);

    TempIndexData tmpDataEntity = new TempIndexData();

    tmpDataEntity.setTempId(Integer.parseInt(data[0]));
    tmpDataEntity.setDocSeqId(Long.parseLong(data[1]));

    return tmpDataEntity;
  }
}
