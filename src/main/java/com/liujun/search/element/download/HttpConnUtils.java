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
public class HttpConnUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpConnUtils.class);

  /** 网页文本内容 */
  private static final String TEXT_HOME = "text/html";

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

  public static boolean ContextTypeChec(String contextType) {
    String[] contextArrays = contextType.split(SymbolMsg.SEMICOLON);

    for (String context : contextArrays) {
      if (TEXT_HOME.equals(context)) {
        return true;
      }
    }

    return false;
  }
}
