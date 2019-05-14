package com.liujun.search.engine.query.cache;

import com.liujun.search.engine.query.pojo.WordOffsetBusi;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试倒排索引偏移文件信息的加载
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/14
 */
public class TestWordOffsetCache {

  /** 测试读取偏移量信息 */
  @Test
  public void testloadWordOffset() {
    int wordId = 0;

    WordOffsetBusi busi = WordOffsetCache.INSTANCE.getWordOffset(wordId);

    Assert.assertNotNull(busi);
  }
}
