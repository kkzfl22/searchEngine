package com.liujun.search.engine.index;

import com.liujun.search.engine.index.filesort.TempIndexSort;
import com.liujun.search.engine.index.mergeIndex.MergeSortIndex;

/**
 * 索引数据的处理操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/11
 */
public class IndexProcess {

  public static final IndexProcess INSTANCE = new IndexProcess();

  public void indexOut() {
    // 1，进行临时索引的排序
    TempIndexSort.INSTANCE.tempIndexSort();
    // 2,进行索引的合并输出
    MergeSortIndex.INSTANCE.mergeIndex();
  }
}
