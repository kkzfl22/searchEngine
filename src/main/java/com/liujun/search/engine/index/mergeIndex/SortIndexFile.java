package com.liujun.search.engine.index.mergeIndex;

import com.liujun.search.common.constant.PathCfg;

import java.io.File;

/**
 * 索引排序完成的文件读取
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/11
 */
public class SortIndexFile {

  /** 临时排序完成的文件路径 */
  private static final String TMP_SORTINDEX_PATH =
      PathCfg.BASEPATH + PathCfg.INDEX_PATH + PathCfg.INDEX_TEMP_SORT_INDEX_PATH;

  public static final SortIndexFile INSTANCE = new SortIndexFile();

  /**
   * 获取索引文件集合
   *
   * @return 文件
   */
  public File[] indexFiles() {
    File itemFile = new File(TMP_SORTINDEX_PATH);

    if (itemFile.exists()) {
      return itemFile.listFiles();
    }

    return new File[0];
  }
}
