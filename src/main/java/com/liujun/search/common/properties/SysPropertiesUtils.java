package com.liujun.search.common.properties;

import com.liujun.search.utilscode.io.constant.SysPropertyEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 只读属性文件，
 *
 * @author liujun
 * @date 2014年6月10日
 * @vsersion 0.0.1
 */
public class SysPropertiesUtils {

  private static final String DEF_FILENAME = "application.properties";

  private Properties prop = new Properties();

  private static final SysPropertiesUtils PROINSTANCE = new SysPropertiesUtils();

  public SysPropertiesUtils() {
    // loader default property fiile application.properties
    loadProperties(DEF_FILENAME);
  }

  public static SysPropertiesUtils getInstance() {
    return PROINSTANCE;
  }

  public void loadProperties(String fileName) {
    if (prop.isEmpty()) {
      InputStream in = null;

      try {
        in = SysPropertiesUtils.class.getResourceAsStream(fileName);
        if (in == null) {
          in = SysPropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
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
  public String getValue(SysPropertyEnum key) {
    return prop.getProperty(key.getKey());
  }

  /**
   * 获取对象类型的数据
   *
   * @param key key信息
   * @return
   */
  public int getIntValue(SysPropertyEnum key) {

    String value = prop.getProperty(key.getKey());

    if (null != value) {
      return Integer.parseInt(value);
    }

    return 0;
  }

  /**
   * 获取值带默认的方法
   *
   * @param key key信息
   * @param defValue 默认值
   * @return
   */
  public int getIntegerValueOrDef(SysPropertyEnum key, int defValue) {
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
