package com.liujun.search.algorithm.bloomFilter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试布隆过滤器
 *
 * <p>1，检查数据是否在
 *
 * <p>2，将数据添加至布隆过滤器中
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/04
 */
public class TestHtmlUrlFilter {

  @Test
  public void testUrl() {
    String url = "http://www.baidu.com3";
    HtmlUrlFilter.INSTANCE.putData(url);
    boolean exists = HtmlUrlFilter.INSTANCE.exists(url);
    Assert.assertEquals(true, exists);

    HtmlUrlFilter.INSTANCE.save();
    HtmlUrlFilter.INSTANCE.loader();

    boolean exists2 = HtmlUrlFilter.INSTANCE.exists(url);
    Assert.assertEquals(true, exists2);

    String baseUrl = "http://www.baidu.coom/111/";

    for (int i = 0; i < 100; i++) {
      String outUrl = baseUrl + i;
      Assert.assertEquals(false, HtmlUrlFilter.INSTANCE.exists(outUrl));
      HtmlUrlFilter.INSTANCE.putData(outUrl);
      Assert.assertEquals(true, HtmlUrlFilter.INSTANCE.exists(outUrl));
    }
  }

  @After
  public void clean() {
    HtmlUrlFilter.INSTANCE.clean();
  }
}
