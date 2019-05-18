package com.liujun.search.engine.collect.operation.html;

import com.liujun.search.utilscode.io.code.PathUtils;
import com.liujun.search.utils.PathEnum;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
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

  /** 默认文件 */
  private static final String DEF_NAME = "html_error_zz.html";

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
  public void htmlErrorHrefGetError() throws IOException {

    File fileList = new File(BASE_ERROR_PATH);

    String[] listFileNames = fileList.list();

    for (String nameValues : listFileNames) {

      if (DEF_NAME.equals(nameValues)) {
        continue;
      }

      Set<String> list = this.getHref(nameValues);
      Assert.assertNotNull(list);
    }
  }

  /**
   * 解决bug的问题
   *
   * @param fileSoho 错误文件
   * @return 文件信息
   * @throws IOException 异常
   */
  private Set<String> getHref(String fileSoho) throws IOException {
    File sohoFile = new File(BASE_ERROR_PATH, fileSoho);

    String htmlContext = FileUtils.readFileToString(sohoFile, StandardCharsets.UTF_8);

    if (StringUtils.isNotEmpty(htmlContext)) {
      return HtmlHrefAnalyze.INSTANCE.getHref(htmlContext);
    }

    return null;
  }

  /**
   * 解决bug的问题
   *
   * @return 文件信息
   * @throws IOException 异常
   */
  @Test
  public void special() throws IOException {

    String fileSoho = "20180912.ai";

    File sohoFile = new File(BASE_ERROR_PATH, fileSoho);

    String htmlContext = FileUtils.readFileToString(sohoFile, StandardCharsets.UTF_8);

    if (StringUtils.isNotEmpty(htmlContext)) {
      Set<String> list = HtmlHrefAnalyze.INSTANCE.getHref(htmlContext);
      System.out.println(list);
    }
  }
}
