package com.liujun.search.engine.collect.flow;

import com.liujun.search.engine.collect.constant.WebEntryEnum;
import com.liujun.search.utilscode.element.constant.HtmlHrefFileEnum;
import com.liujun.search.utilscode.element.html.HtmlHrefUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/18
 */
public class TestHtmlAnalyzeFlow {

  /** 测试网页分析 */
  @Test
  public void testHtmlAnalyze() {

    // 2，执行下载
    HtmlAnalyzeFLow.INSTANCE.downloadAndAnalyzeHtml(WebEntryEnum.SOHO);

    //    // 进行结果的验证
    //    Assert.assertNotNull(downloadSet);
    //
    //    Set<String> fileHrefSet = HtmlHrefUtils.INSTANCE.getHrefUrl(HtmlHrefFileEnum.SOHO);
    //
    //    Assert.assertNotNull(fileHrefSet);
    //
    //    // 检查网页链接是否正确
    //    Assert.assertThat(downloadSet, Matchers.hasItem("http://mp.sohu.com"));
    //    Assert.assertThat(downloadSet, Matchers.hasItem("https://mail.sohu.com"));
    //    Assert.assertThat(
    //        downloadSet,
    // Matchers.hasItem("http://www.sohu.com/upload/uiue20150210/ylbj_sohu.html"));

    // Assert.assertArrayEquals(downloadSet,fileHrefSet);
  }
}
