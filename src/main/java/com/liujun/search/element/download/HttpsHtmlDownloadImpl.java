package com.liujun.search.element.download;

import com.liujun.search.common.properties.SysPropertiesUtils;
import com.liujun.search.utilscode.io.constant.SysPropertyEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

  @Override
  public String downloadHtml(String url, CloseableHttpClient client) {

    String body = null;

    long startTime = System.currentTimeMillis();

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
        boolean isStream = entity.isStreaming();
        long contextLength = entity.getContentLength();

        logger.info(
            "html downloadHtml url :{} ,rsp context type {}, issteam: {} ,content length : {}  ,",
            url,
            entity.getContentType().getValue(),
            isStream,
            contextLength);

        // 如果当前文件非流，则进行下载文本操作
        if (HttpConnUtils.ContextTypeChec(entity.getContentType().getValue())) {
          // 按指定编码转换结果实体为String类型
          body = EntityUtils.toString(entity, StandardCharsets.UTF_8);
          EntityUtils.consume(entity);
        }
      }

    } catch (ClientProtocolException e) {
      e.printStackTrace();
      logger.error("https download error ,ClientProtocolException", e);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("https download error ,IOException", e);
    } finally {
      HttpConnUtils.close(response);
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
}
