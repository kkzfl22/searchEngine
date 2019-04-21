package com.liujun.search.engine.analyze.operation.htmlanalyze.process;

import com.liujun.search.utilscode.element.html.HtmlReaderUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * 网页注释段的处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/20
 */
public class TestHtmlTagAnnotationProcess {

  /**
   * 测试注释的去掉
   *
   * @param htmlCode
   */
  public void testAnnotation(String htmlCode) {
    System.out.println("----------------------------开始------------------");
    char[] htmlCleanArrays =
        HtmlSectionTagProcess.INSTANCE.cleanHtmlTagSection(htmlCode.toCharArray());

    // 进行注释标签的清理
    char[] outHtmlArrays = HtmlTagAnnotationProcess.INSTANCE.annotationProc(htmlCleanArrays, 0);

    String htmlClean = new String(outHtmlArrays);
    System.out.println(htmlClean);

    // Assert.assertEquals(-1, htmlClean.indexOf(HtmlTagAnnotationProcess.ANNOTATION_START_STR));
    Assert.assertEquals(-1, htmlClean.indexOf(HtmlTagAnnotationProcess.ANNOTATION_END_STR));
    System.out.println("----------------------------结束------------------");
  }

  @Test
  public void testProcAnnotation() {
    String htmlCode = HtmlReaderUtils.GetContext("html_error_5.html");
    this.testAnnotation(htmlCode);
  }

  @Test
  public void testProcAnnotation3() {
    String htmlCode = HtmlReaderUtils.GetContext("html_error_3.html");
    this.testAnnotation(htmlCode);
  }

  @Test
  public void testHtmlError() {
    File[] fileList = HtmlReaderUtils.GetErrorFileList();

    for (File fileItem : fileList) {
      System.out.println("文件:" + fileItem.getPath());

      if (fileItem.length() > 0) {
        this.testAnnotation(HtmlReaderUtils.GetContext(fileItem));
      }
    }
  }
}
