package com.liujun.search.engine.collect.operation.html;

import com.liujun.search.engine.collect.pojo.AnalyzeBusi;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 测试网页链接的分析操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/20
 */
public class TestHtmlHrefGet {

  /** 分析的网页路径 */
  private static final String ANALLYZE_PATH = "html/analyze";

  private static final String BASE_PATH =
      TestHtmlHrefGet.class.getClassLoader().getResource(ANALLYZE_PATH).getPath();

  @Test
  public void testGetHrefUrl() throws IOException {
    String fileSoho = "soho.html";
    File sohoFile = new File(BASE_PATH, fileSoho);
    String htmlContext = FileUtils.readFileToString(sohoFile, StandardCharsets.UTF_8);

    char[] contextArray = htmlContext.toCharArray();

    // 进行网页分析
    AnalyzeBusi analyzeRspOne = HtmlHrefGet.INSTANCE.getHref(contextArray, 0);
    // 对比第一个网页链接
    Assert.assertEquals(
        "http://news.sohu.com/s2018/guoqing69/index.shtml", analyzeRspOne.getHref());

    // 对比第二个网页链接
    AnalyzeBusi analyzeRspTwo =
        HtmlHrefGet.INSTANCE.getHref(contextArray, analyzeRspOne.getEndPostion());
    Assert.assertEquals("javascript:void(0)", analyzeRspTwo.getHref());

    // 测试第三个网页链接
    AnalyzeBusi analyzeRsp3 =
        HtmlHrefGet.INSTANCE.getHref(contextArray, analyzeRspTwo.getEndPostion());
    Assert.assertEquals("/", analyzeRsp3.getHref());

    // 测试第4个网页链接
    AnalyzeBusi analyzeRsp4 =
        HtmlHrefGet.INSTANCE.getHref(contextArray, analyzeRsp3.getEndPostion());
    Assert.assertEquals("http://mp.sohu.com", analyzeRsp4.getHref());

    // 测试第5个网页链接
    AnalyzeBusi analyzeRsp5 =
        HtmlHrefGet.INSTANCE.getHref(contextArray, analyzeRsp4.getEndPostion());
    Assert.assertEquals("https://www.sogou.com", analyzeRsp5.getHref());
  }

  @Test
  public void printAllHref() throws IOException {
    String fileSoho = "soho.html";
    File sohoFile = new File(BASE_PATH, fileSoho);
    String htmlContext = FileUtils.readFileToString(sohoFile, StandardCharsets.UTF_8);

    char[] contextArray = htmlContext.toCharArray();

    int startIndex = 0;

    while (startIndex < contextArray.length) {
      // 进行网页分析
      AnalyzeBusi analyzeRspOne = HtmlHrefGet.INSTANCE.getHref(contextArray, startIndex);

      System.out.println(analyzeRspOne.getHref());
      startIndex = analyzeRspOne.getEndPostion();
    }
  }
}
