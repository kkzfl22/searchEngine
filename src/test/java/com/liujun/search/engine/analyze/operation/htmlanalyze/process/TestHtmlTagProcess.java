package com.liujun.search.engine.analyze.operation.htmlanalyze.process;

import com.liujun.search.engine.analyze.constant.HtmlTagSectionEnum;
import com.liujun.search.utilscode.element.constant.HtmlHrefFileEnum;
import com.liujun.search.utilscode.element.html.HtmlReaderUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/17
 */
public class TestHtmlTagProcess {

  /** 测试网页标签的清除 */
  @Test
  public void testCleantag() {
    // 进行网页的标签的清除工作
    this.htmlCheck(HtmlReaderUtils.ReadHtml(HtmlHrefFileEnum.SMALL));
  }

  private void htmlCheck(String htmlCode) {

    System.out.println("----------------------------开始-----------");

    char[] htmlTagArrays = htmlCode.toCharArray();
    // 1,进行标签段的清除
    char[] cleanSectionTags = HtmlSectionTagProcess.INSTANCE.cleanHtmlTagSection(htmlTagArrays);

    String htmlClean = HtmlTagProcess.INSTANCE.cleanHtmlTag(cleanSectionTags);

    System.out.println(htmlClean);
    Assert.assertNotNull(htmlClean);

    for (HtmlTagSectionEnum tagsection : HtmlTagSectionEnum.values()) {
      Assert.assertEquals(-1, htmlClean.indexOf(tagsection.getSectionStart()));
      Assert.assertEquals(-1, htmlClean.indexOf(tagsection.getSectionEnd()));
    }

    System.out.println("----------------------------结束-----------");
  }

  @Test
  public void testSoho() {
    this.htmlCheck(HtmlReaderUtils.ReadHtml(HtmlHrefFileEnum.SOHO));
  }

  @Test
  public void testSina() {
    this.htmlCheck(HtmlReaderUtils.ReadHtml(HtmlHrefFileEnum.SINA));
  }

  @Test
  public void testHtmlError() {
    File[] fileList = HtmlReaderUtils.GetErrorFileList();

    for (File fileItem : fileList) {
      System.out.println("文件:" + fileItem.getPath());

      if (fileItem.length() > 0) {

        this.htmlCheck(HtmlReaderUtils.GetContext(fileItem));
      }
    }
  }

  @Test
  public void testWy163() {
    this.htmlCheck(HtmlReaderUtils.ReadHtml(HtmlHrefFileEnum.WY163));
  }
}
