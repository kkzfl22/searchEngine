package com.liujun.search.engine.index.filesort;

import com.liujun.search.engine.index.pojo.TempIndexData;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * 进行临时索引文件的排序操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/09
 */
public class TempIndexSort {

  public static final TempIndexSort INSTANCE = new TempIndexSort();

  /** 进行临时索引文件的排序操作 */
  public void tempIndexSort() {
    File[] tempIndexs = FileIndexReader.INSTANCE.getIndexFiles();

    for (int i = 0; i < tempIndexs.length; i++) {
      File tempIndex = tempIndexs[i];
      List<TempIndexData> tempIndexList = FileIndexReader.INSTANCE.getFileIndexList(tempIndex);
      // 进行排序操作
      Collections.sort(tempIndexList);
      // 进行索引的输出操作
      FileIndexOutput.INSTANCE.outSortFileIndex(i, tempIndexList);
    }
  }


}
