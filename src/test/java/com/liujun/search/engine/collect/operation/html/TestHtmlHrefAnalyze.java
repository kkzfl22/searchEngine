package com.liujun.search.engine.collect.operation.html;

import com.liujun.search.utilscode.io.code.PathUtils;
import com.liujun.search.utilscode.io.constant.PathEnum;
import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/20
 */
public class TestHtmlHrefAnalyze {

  private static final String BASE_PATH = PathUtils.GetClassPath(PathEnum.FILE_ANALYZE_HTML_PATH);

  private static final String BASE_ERROR_PATH =
      PathUtils.GetClassPath(PathEnum.FILE_ANALYZE_ERROR_HTML_PATH);

  @Test
  public void testHtmlHref() throws IOException {
    String fileSoho = "soho.html";
    File sohoFile = new File(BASE_PATH, fileSoho);
    String htmlContext = FileUtils.readFileToString(sohoFile, StandardCharsets.UTF_8);

    Set<String> list = HtmlHrefAnalyze.INSTANCE.getHref(htmlContext);

    Assert.assertNotNull(list);
    Assert.assertThat(list, Matchers.not("/"));
  }

  @Test
  public void htmlErrorHref() throws IOException {

    String fileSoho = "soho_error.html";
    File sohoFile = new File(BASE_ERROR_PATH, fileSoho);
    String htmlContext = FileUtils.readFileToString(sohoFile, StandardCharsets.UTF_8);

    Set<String> list = HtmlHrefAnalyze.INSTANCE.getHref(htmlContext);

    Assert.assertNotNull(list);
  }
}
