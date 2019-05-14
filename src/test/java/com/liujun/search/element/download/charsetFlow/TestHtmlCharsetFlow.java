package com.liujun.search.element.download.charsetFlow;

import com.liujun.search.utilscode.element.html.HtmlReaderUtils;
import org.apache.commons.io.FileUtils;
import org.apache.http.entity.ContentType;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/30
 */
public class TestHtmlCharsetFlow {

  @Test
  public void testHtmlCode() throws Exception {
    getHtmlCode(HtmlReaderUtils.GetErrorFile("html_error_16.html"));
    getHtmlCode(HtmlReaderUtils.GetErrorFile("html_error_17.html"));
    getHtmlCode(HtmlReaderUtils.GetErrorFile("html_error_18.html"));
    getHtmlCode(HtmlReaderUtils.GetErrorFile("html_error_20.html"));
    getHtmlCode(HtmlReaderUtils.GetErrorFile("html_error_21.html"));
  }

  public void getHtmlCode(File file) throws Exception {
    System.out.println("file:" + file.getName());
    byte[] bytes = FileUtils.readFileToByteArray(file);

    String context = HtmlCharsetFlow.INSTANCE.htmlCharsetValue(bytes, null);

    Assert.assertNotEquals(context, null);
  }

  @Test
  public void failFile() throws Exception {

    System.out.println(ContentType.DEFAULT_TEXT);

    File file = HtmlReaderUtils.GetErrorFile("html_error_19.html");

    System.out.println("file:" + file.getName());
    byte[] bytes = FileUtils.readFileToByteArray(file);

    String context = HtmlCharsetFlow.INSTANCE.htmlCharsetValue(bytes, null);

    Assert.assertEquals(context, null);
  }
}
