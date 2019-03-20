package com.liujun.search.engine.collect.operation.html;

import org.junit.Assert;
import org.junit.Test;

/**
 * 进行网页链接过滤的测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/20
 */
public class TestHtmlHrefFilter {

  /** 进行正常不被过滤的情况检查 */
  @Test
  public void testnotFilter() {
    String src = "//it.sohu.com";
    boolean rsp = HtmlHrefFilter.INSTANCE.filterCheck(src);
    Assert.assertEquals(false, rsp);

    src = "http://fashion.sohu.com";
    rsp = HtmlHrefFilter.INSTANCE.filterCheck(src);
    Assert.assertEquals(false, rsp);

    src = "http://fashion.sohu.com";
    rsp = HtmlHrefFilter.INSTANCE.filterCheck(src);
    Assert.assertEquals(false, rsp);

    src = "http://www.sohu.com/picture/302215191?sec=wd&qq-pf-to=pcqq.group";
    rsp = HtmlHrefFilter.INSTANCE.filterCheck(src);
    Assert.assertEquals(false, rsp);
  }

  /** 进行过滤的条件测试 */
  @Test
  public void testFilterSuccess() {
    // 1,测试过滤的/测试
    String src = "/";
    boolean rsp = HtmlHrefFilter.INSTANCE.filterCheck(src);
    Assert.assertEquals(true, rsp);

    // 过滤掉用javascript
    src = "javascript:window.location.reload()";
    rsp = HtmlHrefFilter.INSTANCE.filterCheck(src);
    Assert.assertEquals(true, rsp);

    // 过滤ico图片
    src = "//statics.itc.cn/web/static/images/pic/sohu-logo/favicon.ico";
    rsp = HtmlHrefFilter.INSTANCE.filterCheck(src);
    Assert.assertEquals(true, rsp);

    // 过滤jpg图片
    src = "http://i2.itc.cn/20141216/26b_4594d163_880a_4ddc_d8a5_23b7ed2a23b3_1.jpg";
    rsp = HtmlHrefFilter.INSTANCE.filterCheck(src);
    Assert.assertEquals(true, rsp);

    // 过滤jpg图片
    src = "http://i2.itc.cn/20141216/26b_4594d163_880a_4ddc_d8a5_23b7ed2a23b3_1.jpg&12312";
    rsp = HtmlHrefFilter.INSTANCE.filterCheck(src);
    Assert.assertEquals(true, rsp);

    // 过滤邮箱
    src = "mailto:kf@vip.sohu.com";
    rsp = HtmlHrefFilter.INSTANCE.filterCheck(src);
    Assert.assertEquals(true, rsp);
  }
}
