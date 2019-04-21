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
 * @date 2019/04/13
 */
public class TestHtmlSectionTagProcess {

  /** 清理网页段标签 */
  @Test
  public void testSectionTag() {
    this.htmlCheck(HtmlReaderUtils.ReadHtml(HtmlHrefFileEnum.WY163));
    this.htmlCheck(HtmlReaderUtils.ReadHtml(HtmlHrefFileEnum.SINA));
    this.htmlCheck(HtmlReaderUtils.ReadHtml(HtmlHrefFileEnum.SOHO));
    this.htmlCheck(HtmlReaderUtils.ReadHtml(HtmlHrefFileEnum.SMALL));
  }

  private void htmlCheck(String htmlCode) {

    System.out.println("----------------------------开始-----------");
    char[] htmlCleanArrays =
        HtmlSectionTagProcess.INSTANCE.cleanHtmlTagSection(htmlCode.toCharArray());

    String htmlClean = new String(htmlCleanArrays);
    System.out.println(htmlClean);

    Assert.assertNotNull(htmlClean);

    for (HtmlTagSectionEnum tagsection : HtmlTagSectionEnum.values()) {
      Assert.assertEquals(-1, htmlClean.indexOf(tagsection.getSectionStart()));
      Assert.assertEquals(-1, htmlClean.indexOf(tagsection.getSectionEnd()));
    }
    System.out.println("----------------------------结束-----------");
  }

  @Test
  public void sectionTestErrorFile() {
    File[] fileList = HtmlReaderUtils.GetErrorFileList();

    for (File fileItem : fileList) {
      this.htmlCheck(HtmlReaderUtils.GetContext(fileItem));
    }
  }
}
