package com.liujun.search.element.download;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 进行https网页的下载操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/04
 */
public class HttpsHtmlDownloadImpl implements HtmlDownLoadInf {

  private Logger logger = LoggerFactory.getLogger(HttpsHtmlDownloadImpl.class);

  private static final Map<String, String> HEAD_MAP = new HashMap<>();

  static {
    // 指定报文头Content-type、User-Agent
    HEAD_MAP.put("Content-type", "application/x-www-form-urlencoded");
    HEAD_MAP.put(
        "User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
  }

  public static final HttpsHtmlDownloadImpl INSTNACE = new HttpsHtmlDownloadImpl();

  /** 采用绕过验证的方式处理https请求 */
  private SSLContext sslcontext = this.createIgnoreVerifySSL();

  /** 设置协议http和https对应的处理socket链接工厂的对象 */
  private Registry<ConnectionSocketFactory> socketFactoryRegistry =
      RegistryBuilder.<ConnectionSocketFactory>create()
          .register("http", PlainConnectionSocketFactory.INSTANCE)
          .register("https", new SSLConnectionSocketFactory(sslcontext))
          .build();

  /**
   * 绕过验证
   *
   * @return
   * @throws NoSuchAlgorithmException
   * @throws KeyManagementException
   */
  private SSLContext createIgnoreVerifySSL() {

    X509TrustManager trustManager = this.getTrustManager();

    SSLContext sc = null;
    try {
      sc = SSLContext.getInstance("SSLv3");
      sc.init(null, new TrustManager[] {trustManager}, null);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      logger.error("https create ignore ssl NoSuchAlgorithmException", e);
    } catch (KeyManagementException e) {
      e.printStackTrace();
      logger.error("https create ignore ssl KeyManagementException", e);
    }
    return sc;
  }

  @Override
  public String downloadHtml(String url) {

    String body = null;

    long startTime = System.currentTimeMillis();

    PoolingHttpClientConnectionManager connManager =
        new PoolingHttpClientConnectionManager(socketFactoryRegistry);

    HttpClients.custom().setConnectionManager(connManager);

    // 创建自定义的httpclient对象
    CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();

    CloseableHttpResponse response = null;

    try {
      // 创建get方式请求对象
      HttpGet get = new HttpGet(url);

      for (Map.Entry<String, String> entryValue : HEAD_MAP.entrySet()) {
        get.setHeader(entryValue.getKey(), entryValue.getValue());
      }

      // 执行请求操作，并拿到结果（同步阻塞）
      response = client.execute(get);

      // 获取结果实体
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        // 按指定编码转换结果实体为String类型
        body = EntityUtils.toString(entity, StandardCharsets.UTF_8);
      }

      EntityUtils.consume(entity);

    } catch (ClientProtocolException e) {
      e.printStackTrace();
      logger.error("https download error ,ClientProtocolException", e);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("https download error ,IOException", e);
    } finally {
      HttpConnUtils.close(response);
      HttpConnUtils.close(client);
    }

    long endTime = System.currentTimeMillis();

    if (StringUtils.isNotEmpty(body)) {
      logger.info(
          "http download :"
              + url
              + ",use time:"
              + (endTime - startTime)
              + " html length :"
              + body.length());
    } else {
      logger.info(
          "http download :" + url + ",use time:" + (endTime - startTime) + " html length 0");
    }

    return body;
  }

  private X509TrustManager getTrustManager() {
    // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
    X509TrustManager trustManager =
        new X509TrustManager() {
          @Override
          public void checkClientTrusted(
              java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
              String paramString) {}

          @Override
          public void checkServerTrusted(
              java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
              String paramString) {}

          @Override
          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
          }
        };

    return trustManager;
  }
}
