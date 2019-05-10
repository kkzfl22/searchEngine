package com.liujun.search.element.download;

import com.liujun.search.utilscode.io.constant.SymbolMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/04
 */
public class HttpUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

  /** 网页文本内容 */
  private static final String TEXT_HOME = "text/html";

  /** 网页编码信息 */
  private static final String CHARSET_NAME = "charset=";

  /**
   * 进行关闭操作
   *
   * @param close 关闭的对象
   */
  public static void close(Closeable close) {
    if (null != close) {
      try {
        close.close();
      } catch (IOException e) {
        e.printStackTrace();
        LOGGER.error("client close IOException", e);
      }
    }
  }

  /**
   * 获取context信息
   *
   * @param contextType
   * @return
   */
  public static boolean ContextTypeChec(String contextType) {
    String[] contextArrays = contextType.split(SymbolMsg.SEMICOLON);

    for (String context : contextArrays) {
      if (TEXT_HOME.equals(context)) {
        return true;
      }
    }

    return false;
  }

  /**
   * 获取网页中的内容编码信息
   *
   * @param contextType
   * @return
   */
  public static String ContextTypeCharset(String contextType) {
    String[] contextArrays = contextType.split(SymbolMsg.SEMICOLON);

    if (contextArrays.length > 1) {
      for (String context : contextArrays) {
        int charIndex = context.indexOf(CHARSET_NAME);

        if (charIndex != -1) {
          return context.substring(charIndex + CHARSET_NAME.length());
        }
      }
    }

    return null;
  }
}
