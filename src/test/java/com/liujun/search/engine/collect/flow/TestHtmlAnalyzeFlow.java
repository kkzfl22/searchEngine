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
  }

  /** 测试网页分析 */
  @Test
  public void testHtmlAnalyzeQQ() {

    // 2，执行下载
    HtmlAnalyzeFLow.INSTANCE.downloadAndAnalyzeHtml(WebEntryEnum.QQ);
  }


  /** 测试网页分析 */
  @Test
  public void testHtmlAnalyze163() {

    // 2，执行下载
    HtmlAnalyzeFLow.INSTANCE.downloadAndAnalyzeHtml(WebEntryEnum.WY163);
  }
}
