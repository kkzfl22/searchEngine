package com.liujun.search.engine.index.outputDescIndex;

import org.junit.Assert;
import org.junit.Test;

/**
 * 进行文件名相关的测试操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/14
 */
public class TestDescIndexFileName {

  @Test
  public void testNameIndex() {
    int index = DescIndexFileName.INSTANCE.getNameIndex("desc_index_1090_offset.offsetindex");
    Assert.assertNotEquals(-1, index);
  }

  @Test
  public void testgetName() {
    String fileName = DescIndexFileName.INSTANCE.getDescIndexFileName(123);
    Assert.assertNotNull(fileName);
  }
}
