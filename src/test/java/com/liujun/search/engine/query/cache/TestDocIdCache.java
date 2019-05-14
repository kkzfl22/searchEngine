package com.liujun.search.engine.query.cache;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试通过网页的id获取
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/13
 */
public class TestDocIdCache {

  /** 通过网页的id获取网页的url */
  @Test
  public void getDocUrl() {
    Long docId = 14l;
    String url = DocIdCache.INSTANCE.getUrl(docId);

    Assert.assertNotNull(url);
    Assert.assertEquals("https://fanshuyao.iteye.com/blog/2440634", url);
  }
}
