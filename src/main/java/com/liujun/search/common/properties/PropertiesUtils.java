package com.liujun.search.common.properties;

import com.liujun.search.common.constant.PropertyEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** properties file loader */
public class PropertiesUtils {

  private static final String DEF_FILENAME = "application.properties";

  private Properties prop = new Properties();

  private static final PropertiesUtils PROINSTANCE = new PropertiesUtils();

  public PropertiesUtils() {
    // loader default property fiile application.properties
    loadProperties(DEF_FILENAME);
  }

  public static PropertiesUtils getInstance() {
    return PROINSTANCE;
  }

  public void loadProperties(String fileName) {
    if (prop.isEmpty()) {
      InputStream in = null;

      try {
        in = PropertiesUtils.class.getResourceAsStream(fileName);
        if (in == null) {
          in = PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
        }
        if (in == null) {
          in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        }

        if (in != null) {
          prop.load(in);
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      } finally {
        if (null != in) {
          try {
            in.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  /**
   * 获取值的方法
   *
   * @param key key信息
   * @return 返回
   */
  public String getValue(PropertyEnum key) {
    return prop.getProperty(key.getKey());
  }

  /**
   * 获取值带默认的方法
   *
   * @param key key信息
   * @param defValue 默认值
   * @return
   */
  public int getIntegerValueOrDef(PropertyEnum key, int defValue) {
    String value = prop.getProperty(key.getKey());

    if (StringUtils.isNotEmpty(value)) {
      value = value.trim();
      if (StringUtils.isNumeric(value)) {
        return Integer.parseInt(value);
      }
      return defValue;
    } else {
      return defValue;
    }
  }
}
