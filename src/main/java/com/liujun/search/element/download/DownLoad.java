package com.liujun.search.element.download;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 进行网存下载操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/17
 */
public class DownLoad {

  private Logger logger = LoggerFactory.getLogger(DownLoad.class);

  /** 下载网页的实例对象信息 */
  public static final DownLoad INSTANCE = new DownLoad();

  /**
   * 根据网页地址下载网页信息
   *
   * @param url
   * @return
   */
  public String downloadHtml(String url) {

    CloseableHttpClient client = HttpClients.createDefault();

    HttpGet get = new HttpGet(url);

    get.addHeader(
        "User-Agent",
        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");

    get.addHeader("Accept-Charset", "UTF-8");
    get.addHeader("Host", "www.sohu.com");
    get.addHeader(
        "Accept",
        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");

    get.getParams().setParameter("http.protocol.allow-circular-redirects", true);
    try {
      HttpResponse response = client.execute(get);

      if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
        return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
      }

    } catch (ClientProtocolException ex) {
      ex.printStackTrace();
      logger.error("DownLoad downloadHtml ClientProtocolException,url:", ex);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("DownLoad downloadHtml IOException,url:" + get, e);
    } finally {
      if (null != client) {
        try {
          client.close();
        } catch (IOException e) {
          e.printStackTrace();
          logger.error("DownLoad downloadHtml close IOException", e);
        }
      }
    }

    // 翻放内存对象
    client = null;
    get = null;

    return StringUtils.EMPTY;
  }
}
