package com.liujun.search.element.download;

import com.liujun.search.engine.collect.constant.WebEntryEnum;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
}
