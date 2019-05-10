package com.liujun.search.element.download;

import com.liujun.search.engine.collect.constant.WebEntryEnum;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * 进行https相关的测试操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/05
 */
public class TestHttpsHtmlDownloadImpl {

  private CloseableHttpClient client;

  @Before
  public void getConn() {
    client = HttpsClientManager.getConnection();
  }

  @Test
  public void downloadQQ() {

    String conect =
        HttpsHtmlDownloadImpl.INSTNACE.downloadHtml(WebEntryEnum.QQ.getUrlAddress(), client);
    Assert.assertNotEquals(0, conect.length());
  }

  @Test
  public void downloadwy163() {
    String conect =
        HttpsHtmlDownloadImpl.INSTNACE.downloadHtml(WebEntryEnum.WY163.getUrlAddress(), client);
    Assert.assertNotEquals(0, conect.length());
  }

  @Test
  public void downloadSina() {
    String conect =
        HttpsHtmlDownloadImpl.INSTNACE.downloadHtml(WebEntryEnum.SINA.getUrlAddress(), client);
    Assert.assertNotEquals(0, conect.length());
  }

  @Test
  public void downloadSoho() {
    String conect =
        HttpsHtmlDownloadImpl.INSTNACE.downloadHtml(WebEntryEnum.SOHO.getUrlAddress(), client);
    Assert.assertNotEquals(0, conect.length());
  }

  @Test
  public void testDownStream() {
    String url = "http://node.video.qq.com/x/api/download_pc";
    String conect = HttpsHtmlDownloadImpl.INSTNACE.downloadHtml(url, client);
    Assert.assertEquals(null, conect);
  }

  @Test
  public void testDownErrorUrl() {
    String url = "http://tv.sohu.com/comic/?from=pgc_player";
    String conect = HttpsHtmlDownloadImpl.INSTNACE.downloadHtml(url, client);
    Assert.assertNotEquals(null, conect);
  }

  @Test
  public void testDownErrorUrl2() {
    String url = "http://tv.sohu.com/s2011/9240/s328641340/";
    String conect = HttpsHtmlDownloadImpl.INSTNACE.downloadHtml(url, client);
    Assert.assertNotEquals(null, conect);
  }

  @Test
  public void testDownErrorUrl3() {
    List<String> listArrays =
        Arrays.asList(
            "http://auto.sina.com.cn/newcar/x/2019-04-29/detail-ihvhiewr8933678.shtml",
            "https://art.163.com/19/0430/10/EE0K9K9M009998I0.html",
            "https://www.163.com/",
            "http://new.qq.com/cmsn/20190430/20190430001796.html",
            "http://new.qq.com/omn/20190430/20190430A0668X.html");

    for (String url : listArrays) {
      this.check(url);
    }
  }

  private void check(String url) {
    String conect = HttpsHtmlDownloadImpl.INSTNACE.downloadHtml(url, client);
    int startIndex = conect.indexOf("<title>");
    int endIndex = conect.indexOf("</title>");
    System.out.println(conect.substring(startIndex, endIndex));

    int keystartIndex = conect.indexOf("name=\"keywords");
    if (keystartIndex != -1) {
      System.out.println(conect.substring(keystartIndex, keystartIndex + 50));
    }

    Assert.assertNotEquals(null, conect);

    System.out.println("------------------------------------------------------------------");
  }
}
