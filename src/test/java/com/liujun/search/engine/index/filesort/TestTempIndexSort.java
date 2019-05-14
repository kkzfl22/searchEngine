package com.liujun.search.engine.index.filesort;

import org.junit.Test;

/**
 * 测试临时索引文件的排序操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/10
 */
public class TestTempIndexSort {

  @Test
  public void testTempIndexSort() {
    TempIndexSort.INSTANCE.tempIndexSort();
  }
}
