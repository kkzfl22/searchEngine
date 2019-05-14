package com.liujun.search.engine.index.filesort;

import com.liujun.search.common.io.CommonIOUtils;
import com.liujun.search.engine.index.pojo.TempIndexData;
import com.liujun.search.utilscode.io.constant.PathCfg;
import com.liujun.search.utilscode.io.constant.SymbolMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * 将排序完成的索引数据进行输出
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/09
 */
public class FileIndexOutput {

  /** 临时索引文件排序完成目录 */
  private static final String TMP_INDEX_PATH =
      PathCfg.BASEPATH + PathCfg.INDEX_PATH + PathCfg.INDEX_TEMP_SORT_INDEX_PATH;

  /** 排序后的文件名前缀 */
  public static final String FILENAME_PREFIX_SORTINDEX = "sortindex_";

  /** 文件后缀 */
  public static final String SUFFIX_NAME = ".sortindex";

  /** 索引号占用的位数 */
  private static final int MAX_LENGTH_SORT_INDEX = 4;

  public static final FileIndexOutput INSTANCE = new FileIndexOutput();

  private Logger logger = LoggerFactory.getLogger(FileIndexOutput.class);

  public FileIndexOutput() {
    File outDir = new File(TMP_INDEX_PATH);

    // 当文件目录不存在时，则创建目录
    if (!outDir.exists()) {
      outDir.mkdirs();
    }
  }

  /**
   * 输出排序好的临时索引文件
   *
   * @param index 文件输出索引号
   * @param list 文件输出的集合信息
   */
  public void outSortFileIndex(int index, List<TempIndexData> list) {
    File outFile = this.getOutputFile(index);

    FileWriter write = null;
    BufferedWriter bufferedWrite = null;

    try {
      write = new FileWriter(outFile);
      bufferedWrite = new BufferedWriter(write);

      // 进行数据的输出操作
      for (TempIndexData data : list) {
        bufferedWrite.write(this.toIndexData(data));
      }
      bufferedWrite.flush();
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("FileIndexOutput outSortFileIndex IOException", e);
    } finally {
      CommonIOUtils.close(bufferedWrite);
      CommonIOUtils.close(write);
    }
  }

  /**
   * 获取输出文件名路径
   *
   * @param index 文件索引
   * @return 文件路径
   */
  private File getOutputFile(int index) {
    StringBuilder outFile = new StringBuilder();

    outFile.append(TMP_INDEX_PATH);
    outFile.append(SymbolMsg.PATH);
    outFile.append(FILENAME_PREFIX_SORTINDEX);
    outFile.append(this.getOutNameIndex(index));
    outFile.append(SUFFIX_NAME);

    return new File(outFile.toString());
  }

  /**
   * 获取索引文件的索引号
   *
   * @param indexValue
   * @return
   */
  private String getOutNameIndex(int indexValue) {
    String indexStr = String.valueOf(indexValue);

    StringBuilder outMsg = new StringBuilder();

    for (int i = 0; i < MAX_LENGTH_SORT_INDEX - indexStr.length(); i++) {
      outMsg.append("0");
    }
    outMsg.append(indexStr);

    return outMsg.toString();
  }

  /**
   * 将java对象输出到数据文件中
   *
   * @param data
   * @return
   */
  private String toIndexData(TempIndexData data) {

    StringBuilder outMsg = new StringBuilder();

    outMsg.append(data.getTempId());
    outMsg.append(SymbolMsg.DATA_COLUMN);
    outMsg.append(data.getDocSeqId());
    outMsg.append(SymbolMsg.LINE);

    return outMsg.toString();
  }
}
