package com.liujun.search.engine.collect.operation.html;

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

  /** 分析的网页路径 */
  private static final String ANALLYZE_PATH = "html/analyze";

  private static final String BASE_PATH =
      TestHtmlHrefGet.class.getClassLoader().getResource(ANALLYZE_PATH).getPath();

  @Test
  public void testHtmlHref() throws IOException {
    String fileSoho = "soho.html";
    File sohoFile = new File(BASE_PATH, fileSoho);
    String htmlContext = FileUtils.readFileToString(sohoFile, StandardCharsets.UTF_8);

    Set<String> list = HtmlHrefAnalyze.INSTANCE.getHref(htmlContext);

    Assert.assertNotNull(list);
    Assert.assertThat(list, Matchers.not("/"));
  }
}
