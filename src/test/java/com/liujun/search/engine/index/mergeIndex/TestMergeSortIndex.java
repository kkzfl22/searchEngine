package com.liujun.search.engine.index.mergeIndex;

import com.liujun.search.utilscode.io.constant.PathCfg;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * 测试合并排序索引文件
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/10
 */
public class TestMergeSortIndex {

  /** 临时索引文件排序完成目录 */
  private static final String TMP_INDEX_PATH =
      PathCfg.BASEPATH + PathCfg.INDEX_PATH + PathCfg.INDEX_OUTPUT_DESCINDEX_PATH;

  /** 测试合并倒排索引 */
  @Test
  public void testmergeSort() {
    MergeSortIndex.INSTANCE.mergeIndex();

    // 检查输出输出的索引文件是否存在
    File outFile = new File(TMP_INDEX_PATH);

    Assert.assertEquals(true, outFile.exists());
    Assert.assertNotEquals(0, outFile.listFiles().length);
  }
}
