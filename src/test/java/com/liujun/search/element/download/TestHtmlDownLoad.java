package com.liujun.search.element.download;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * 测试网页下载
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/17
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestHtmlDownLoad {

  /** 测试下载http网页信息 */
  @Test
  public void test01DownloadHttp() {
    String url = "http://www.sohu.com/";

    String html = DownLoad.INSTANCE.downloadHtml(url);
    System.out.println("length:" + html.length());
    Assert.assertNotNull(html);
  }

  /** 测试下载https的网页信息 */
  @Test
  public void test02DownloadHttps() {

    String httpsUrl = "https://www.163.com/";

    String html = DownLoad.INSTANCE.downloadHtml(httpsUrl);
    System.out.println("length:" + html.length());
    Assert.assertNotNull(html);
  }

  /** 测评下载长地址 */
  @Test
  public void test03Downloadhttps() {
    String httpsUrl = "http://news.163.com/special/wangsansanhome/";

    String html = DownLoad.INSTANCE.downloadHtml(httpsUrl);

    System.out.println("length:" + html.length());
    Assert.assertNotNull(html);
  }
}
