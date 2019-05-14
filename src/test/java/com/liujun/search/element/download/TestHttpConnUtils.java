package com.liujun.search.element.download;

import org.junit.Assert;
import org.junit.Test;

/**
 * 网页内容的工具类
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/30
 */
public class TestHttpConnUtils {

  @Test
  public void testGetContext() {
    String charSet = HttpUtils.ContextTypeCharset("text/html; charset=GBK");

    Assert.assertEquals("GBK", charSet);
  }
}
