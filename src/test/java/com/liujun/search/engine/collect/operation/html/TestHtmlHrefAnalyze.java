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
    System.out.println();

    for (String context : list) {
      System.out.println(context);
    }

    Assert.assertThat(list, Matchers.hasItem("http://www.sohu.com?strategyid=00004"));
    Assert.assertThat(
        list, Matchers.hasItem("http://search.sohu.com/?keyword=拜伦&queryType=outside"));
    Assert.assertThat(
        list,
        Matchers.hasItem(
            "https://passport.zhan.sohu.com/passport/sohu/login-jumpto?callback=https://yonghufankui.kuaizhan.com/clubpc/topics/WhOlhDSItmL75kIf"));
  }

  @Test
  public void htmlErrorHrefGetError() throws IOException {

    String fileSoho = "soho_context_error.html";

    Set<String> list = this.getHref(fileSoho);

    Assert.assertNotNull(list);
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

    return HtmlHrefAnalyze.INSTANCE.getHref(htmlContext);
  }

  @Test
  public void htmlErrorHrefGetError3() throws IOException {

    String fileSoho = "soho_error_2.html";
    Set<String> list = this.getHref(fileSoho);
    Assert.assertNotEquals(0, list.size());
    Assert.assertThat(
        list,
        Matchers.not(Matchers.hasItem("http://search.sohu.com/?keyword=小米&queryType=outside123")));
    Assert.assertThat(
        list, Matchers.hasItem("http://search.sohu.com/?keyword=科技&queryType=outside"));
  }

  @Test
  public void htmlErrorHrefGetError4() throws IOException {

    String fileSoho = "soho_error_3.html";
    Set<String> list = this.getHref(fileSoho);
    Assert.assertNotEquals(0, list.size());
    Assert.assertThat(
        list, Matchers.hasItem("http://servlet/enp.web.Publish"));
  }

  @Test
  public void htmlErrorAnalyze4() throws IOException {

    String fileSoho = "soho_error_4.html";
    Set<String> list = this.getHref(fileSoho);
    Assert.assertNotEquals(0, list.size());
    Assert.assertThat(
            list, Matchers.hasItem("http://www.baom.com.cn/2019-03/26/content_42036.html"));
  }
}
