package com.liujun.search.element.download;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * 进行网http的下载操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/04
 */
public class HttpHtmlDownloadImpl implements HtmlDownLoadInf {

  private static final int HTTP_OK = 200;

  private static final int HTTP_OK_MAX = 300;

  /** 响应处理对象 */
  private ResponseHandler<String> responseHandler = this.getRspHandler();

  public static final HttpHtmlDownloadImpl INSTANCE = new HttpHtmlDownloadImpl();

  private Logger logger = LoggerFactory.getLogger(HttpHtmlDownloadImpl.class);

  @Override
  public String downloadHtml(String url, CloseableHttpClient httpclient) {

    String responseBody = null;

    long startTime = System.currentTimeMillis();

    try {
      HttpGet httpget = new HttpGet(url);
      // 执行下载操作
      responseBody = httpclient.execute(httpget, responseHandler);

    } catch (Exception e) {
      e.printStackTrace();
      logger.error("http download exception:", e);
    }

    long endTime = System.currentTimeMillis();

    if (StringUtils.isNotEmpty(responseBody)) {
      logger.info(
          "http download :"
              + url
              + ",use time:"
              + (endTime - startTime)
              + " html length :"
              + responseBody.length());
    } else {
      logger.info(
          "http download :" + url + ",use time:" + (endTime - startTime) + " html length 0");
    }

    return responseBody;
  }

  /**
   * 获取rsp对象处理操作
   *
   * @return
   */
  private ResponseHandler<String> getRspHandler() {
    return (response) -> {
      int status = response.getStatusLine().getStatusCode();
      if (status >= HTTP_OK && status < HTTP_OK_MAX) {
        HttpEntity entity = response.getEntity();
        return entity != null ? EntityUtils.toString(entity) : null;
      } else {
        throw new ClientProtocolException("http Unexpected response status: " + status);
      }
    };
  }
}
