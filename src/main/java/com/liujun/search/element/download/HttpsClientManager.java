package com.liujun.search.element.download;

import com.liujun.search.common.properties.SysPropertiesUtils;
import com.liujun.search.element.constant.HttpDefConnCfg;
import com.liujun.search.element.constant.HttpVersionEnum;
import com.liujun.search.common.constant.SysPropertyEnum;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * 连接池管理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/05
 */
public class HttpsClientManager {

  private static final String[] SSL_VERSION =
      new String[] {"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2"};

  private static final Logger logger = LoggerFactory.getLogger(HttpsClientManager.class);

  /** 池化管理 */
  private static PoolingHttpClientConnectionManager poolConnManager;

  /** 请求器的配置 */
  private static RequestConfig requestConfig;

  static {
    SSLContext sslcontext = createIgnoreVerifySSL();

    SSLConnectionSocketFactory socketFactory =
        new SSLConnectionSocketFactory(
            sslcontext, SSL_VERSION, null, NoopHostnameVerifier.INSTANCE);

    // 设置协议http和https对应的处理socket链接工厂的对象
    Registry<ConnectionSocketFactory> socketFactoryRegistry =
        RegistryBuilder.<ConnectionSocketFactory>create()
            .register(HttpVersionEnum.HTTP.getVerison(), PlainConnectionSocketFactory.INSTANCE)
            .register(HttpVersionEnum.HTTPS.getVerison(), socketFactory)
            .build();

    // 初始化连接管理器
    poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
    // 将最大连接数增加到200，实际项目最好从配置文件中读取这个值
    int maxTotal =
        SysPropertiesUtils.getInstance()
            .getIntegerValueOrDef(
                SysPropertyEnum.HTTP_POOL_MAXTOTAL, HttpDefConnCfg.POOL_MAX_TOTAL.getCfg());
    poolConnManager.setMaxTotal(maxTotal);

    int defMaxRoute =
        SysPropertiesUtils.getInstance()
            .getIntegerValueOrDef(
                SysPropertyEnum.HTTP_MAX_DEFAULT_PERROUTE,
                HttpDefConnCfg.HTTP_MAX_DEFAULT_ROUTE.getCfg());

    // 设置设置每个路由上的默认连接个数
    poolConnManager.setDefaultMaxPerRoute(defMaxRoute);
    // 根据默认超时限制初始化requestConfig
    int socketTimeout =
        SysPropertiesUtils.getInstance()
            .getIntegerValueOrDef(
                SysPropertyEnum.HTTP_SOCKETTIMEOUT, HttpDefConnCfg.HTTP_SOCKET_TIMEOUT.getCfg());
    int connectTimeout =
        SysPropertiesUtils.getInstance()
            .getIntegerValueOrDef(
                SysPropertyEnum.HTTP_CONN_TIMEOUT, HttpDefConnCfg.CONN_TIMEOUT.getCfg());
    int connectionRequestTimeout =
        SysPropertiesUtils.getInstance()
            .getIntegerValueOrDef(
                SysPropertyEnum.HTTP_CONN_REQ_TIMEOUT, HttpDefConnCfg.CONN_REQ_TIMEOUT.getCfg());

    requestConfig =
        RequestConfig.custom()
            .setConnectionRequestTimeout(connectionRequestTimeout)
            .setSocketTimeout(socketTimeout)
            .setConnectTimeout(connectTimeout)
            .build();
  }

  /**
   * 绕过验证
   *
   * @return
   * @throws NoSuchAlgorithmException
   * @throws KeyManagementException
   */
  private static SSLContext createIgnoreVerifySSL() {
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

    SSLContext sc = null;
    try {
      sc = SSLContext.getInstance("TLS");
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

  /**
   * 获取连接操作
   *
   * @return
   */
  public static CloseableHttpClient getConnection() {
    CloseableHttpClient httpClient =
        HttpClients.custom()
            // 设置连接池管理
            .setConnectionManager(poolConnManager)
            // 设置请求配置
            .setDefaultRequestConfig(requestConfig)
            // 设置重试次数
            .setRetryHandler(new DefaultHttpRequestRetryHandler(2, true))
            .build();

    if (poolConnManager != null && poolConnManager.getTotalStats() != null) {
      logger.info(
          "HttpsClientManager now client pool {}", poolConnManager.getTotalStats().toString());
    }

    return httpClient;
  }
}
