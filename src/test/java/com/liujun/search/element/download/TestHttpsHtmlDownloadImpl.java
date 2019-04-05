package com.liujun.search.element.download;

import com.liujun.search.engine.collect.constant.WebEntryEnum;
import org.junit.Assert;
import org.junit.Test;

/**
 * 进行https相关的测试操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/05
 */
public class TestHttpsHtmlDownloadImpl {

  @Test
  public void downloadQQ() {
    String conect = HttpsHtmlDownloadImpl.INSTNACE.downloadHtml(WebEntryEnum.QQ.getUrlAddress());
    Assert.assertNotEquals(0, conect.length());
  }

  @Test
  public void downloadwy163() {
    String conect = HttpsHtmlDownloadImpl.INSTNACE.downloadHtml(WebEntryEnum.WY163.getUrlAddress());
    Assert.assertNotEquals(0, conect.length());
  }

  @Test
  public void downloadSina() {
    String conect = HttpsHtmlDownloadImpl.INSTNACE.downloadHtml(WebEntryEnum.SINA.getUrlAddress());
    Assert.assertNotEquals(0, conect.length());
  }

  @Test
  public void downloadSoho() {
    String conect = HttpsHtmlDownloadImpl.INSTNACE.downloadHtml(WebEntryEnum.SOHO.getUrlAddress());
    Assert.assertNotEquals(0, conect.length());
  }
}
