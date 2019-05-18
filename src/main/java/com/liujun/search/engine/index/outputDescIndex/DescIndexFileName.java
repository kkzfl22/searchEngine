package com.liujun.search.engine.index.outputDescIndex;

import com.liujun.search.common.constant.PathCfg;
import com.liujun.search.common.constant.SymbolMsg;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 倒排索引文件输出的文件名称
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/11
 */
public class DescIndexFileName {

  /** 倒排索引文件排序完成目录 */
  public static final String DESC_INDEX_PATH =
      PathCfg.BASEPATH + PathCfg.INDEX_PATH + PathCfg.INDEX_OUTPUT_DESCINDEX_PATH;

  /** 倒排索引文件的前缀 */
  private static final String DESC_INDEX_FILENAME_PREFIX = "desc_index_";

  /** 倒排索引的文件后缀名 */
  private static final String DESC_INDEX_FILENAME_SUFFIX = ".descindex";

  /** 倒排索引文件的偏移量文件名命令规则desc_index_#0#_offset */
  private static final String DESC_INDEX_FILENAME_OFFSET_PREFIX = "_offset";

  /** 索引所对应的偏移量文件 */
  public static final String DESC_INDEX_FILENAME_OFFSET_SUFFIX = ".offsetindex";

  public static final DescIndexFileName INSTANCE = new DescIndexFileName();

  /** 输出的文件索引id */
  private AtomicInteger index = new AtomicInteger(0);

  public DescIndexFileName() {

    File fileDirCheck = new File(DESC_INDEX_PATH);

    if (!fileDirCheck.exists()) {
      fileDirCheck.mkdirs();
    }
  }

  /**
   * 获取倒排索引文件的名称
   *
   * @return 文件名
   */
  public String getDescIndexFileName() {
    StringBuilder outDescName = new StringBuilder();

    outDescName.append(DESC_INDEX_PATH);
    outDescName.append(SymbolMsg.PATH);
    outDescName.append(DESC_INDEX_FILENAME_PREFIX);
    outDescName.append(index.get());
    outDescName.append(DESC_INDEX_FILENAME_SUFFIX);

    return outDescName.toString();
  }

  /**
   * 获取倒排索引的偏移量的文件名称
   *
   * @return 文件名
   */
  public String getDescIndexOffsetFileName() {
    StringBuilder offsetName = new StringBuilder();

    offsetName.append(DESC_INDEX_PATH);
    offsetName.append(SymbolMsg.PATH);
    offsetName.append(DESC_INDEX_FILENAME_PREFIX);
    offsetName.append(index.get());
    offsetName.append(DESC_INDEX_FILENAME_OFFSET_PREFIX);
    offsetName.append(DESC_INDEX_FILENAME_OFFSET_SUFFIX);

    return offsetName.toString();
  }

  public AtomicInteger getIndex() {
    return index;
  }

  public int getNameIndex(String name) {
    int startIndex = name.indexOf(DESC_INDEX_FILENAME_PREFIX);
    if (startIndex != -1) {
      startIndex = startIndex + DESC_INDEX_FILENAME_PREFIX.length();

      int endIndex = name.indexOf(DESC_INDEX_FILENAME_OFFSET_PREFIX);
      String value = name.substring(startIndex, endIndex);

      return Integer.parseInt(value);
    }
    return -1;
  }

  /**
   * 通过索引id获取文件名称
   *
   * @return 文件名
   */
  public String getDescIndexFileName(int index) {
    StringBuilder outDescName = new StringBuilder();

    outDescName.append(DESC_INDEX_PATH);
    outDescName.append(SymbolMsg.PATH);
    outDescName.append(DESC_INDEX_FILENAME_PREFIX);
    outDescName.append(index);
    outDescName.append(DESC_INDEX_FILENAME_SUFFIX);

    return outDescName.toString();
  }
}
