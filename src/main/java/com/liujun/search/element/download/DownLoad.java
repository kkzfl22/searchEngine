package com.liujun.search.element.download;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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

    try {
      HttpResponse response = client.execute(get);

      if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
        return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
      }
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("DownLoad downloadHtml IOException", e);
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
