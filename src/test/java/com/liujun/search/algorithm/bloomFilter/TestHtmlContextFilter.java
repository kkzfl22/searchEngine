package com.liujun.search.algorithm.bloomFilter;

import com.liujun.search.algorithm.bloomFilter.BloomFilter;
import com.liujun.search.algorithm.bloomFilter.HtmlContextFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

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
public class TestHtmlContextFilter {

  @Test
  public void testContext() {
    String url = "<meta content=\"网易,邮箱,游戏,新闻,体育,娱乐,女性,亚运,论坛,短信,数码,汽车,手机,财经,科技,相册\" />";
    HtmlContextFilter.INSTANCE.putData(url);
    boolean exists = HtmlContextFilter.INSTANCE.exists(url);
    Assert.assertEquals(true, exists);

    HtmlContextFilter.INSTANCE.save();
    HtmlContextFilter.INSTANCE.loader();

    boolean exists2 = HtmlContextFilter.INSTANCE.exists(url);
    Assert.assertEquals(true, exists2);

    String baseUrl =
        "<meta name=\"Keywords\" content=\"网易,邮箱,游戏,新闻,体育,娱乐,女性,亚运,论坛,短信,数码,汽车,手机,财经,科技,相册\" />";

    for (int i = 0; i < 100; i++) {
      String outUrl = baseUrl + i;
      Assert.assertEquals(false, HtmlContextFilter.INSTANCE.exists(outUrl));
      HtmlContextFilter.INSTANCE.putData(outUrl);
      Assert.assertEquals(true, HtmlContextFilter.INSTANCE.exists(outUrl));
    }
  }

  @After
  public void clean() {
    HtmlUrlFilter.INSTANCE.clean();
  }
}
