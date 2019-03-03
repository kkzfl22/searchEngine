package com.liujun.search.common.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/03
 */
public class IOUtils {

  /** 日志 */
  private static final Logger LOGGER = LoggerFactory.getLogger(IOUtils.class);

  public static void close(Closeable close) {
    if (close != null) {
      try {
        close.close();
      } catch (IOException e) {
        e.printStackTrace();
        LOGGER.error("IOUtils close IOException", e);
      }
    }
  }
}
