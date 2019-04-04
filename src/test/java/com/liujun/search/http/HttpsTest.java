package com.liujun.search.http;

import org.apache.http.HttpEntity;
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

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/04
 */
public class HttpsTest {

  /**
   * 绕过验证
   *
   * @return
   * @throws NoSuchAlgorithmException
   * @throws KeyManagementException
   */
  public static SSLContext createIgnoreVerifySSL()
      throws NoSuchAlgorithmException, KeyManagementException {
    SSLContext sc = SSLContext.getInstance("SSLv3");

    // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
    X509TrustManager trustManager =
        new X509TrustManager() {
          @Override
          public void checkClientTrusted(
              java.security.cert.X509Certificate[] paramArrayOfX509Certificate, String paramString)
              throws CertificateException {}

          @Override
          public void checkServerTrusted(
              java.security.cert.X509Certificate[] paramArrayOfX509Certificate, String paramString)
              throws CertificateException {}

          @Override
          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
          }
        };

    sc.init(null, new TrustManager[] {trustManager}, null);
    return sc;
  }

  public static final void main(String[] args) throws Exception {

    String body = "";

    // 采用绕过验证的方式处理https请求
    SSLContext sslcontext = createIgnoreVerifySSL();

    // 设置协议http和https对应的处理socket链接工厂的对象
    Registry<ConnectionSocketFactory> socketFactoryRegistry =
        RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.INSTANCE)
            .register("https", new SSLConnectionSocketFactory(sslcontext))
            .build();
    PoolingHttpClientConnectionManager connManager =
        new PoolingHttpClientConnectionManager(socketFactoryRegistry);
    HttpClients.custom().setConnectionManager(connManager);

    // 创建自定义的httpclient对象
    CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();
    // CloseableHttpClient client = HttpClients.createDefault();

    try {
      // 创建get方式请求对象
      // HttpGet get = new HttpGet("https://www.baidu.com/");
      HttpGet get = new HttpGet("https://www.qq.com/");

      // 指定报文头Content-type、User-Agent
      get.setHeader("Content-type", "application/x-www-form-urlencoded");
      get.setHeader(
          "User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");

      // 执行请求操作，并拿到结果（同步阻塞）
      CloseableHttpResponse response = client.execute(get);

      // 获取结果实体
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        // 按指定编码转换结果实体为String类型
        body = EntityUtils.toString(entity, "UTF-8");
      }

      EntityUtils.consume(entity);
      // 释放链接
      response.close();
      System.out.println("body:" + body);
    } finally {
      client.close();
    }
  }
}
