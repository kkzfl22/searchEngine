package com.liujun.search.common.properties;

import com.liujun.search.common.constant.SymbolMsg;
import com.liujun.search.common.constant.SysPropertyEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
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

  private static final Logger LOGGER = LoggerFactory.getLogger(SysPropertiesUtils.class);

  public SysPropertiesUtils() {
    loadProperties(DEF_FILENAME);
  }

  public static SysPropertiesUtils getInstance() {
    return PROINSTANCE;
  }

  /**
   * 进行重新加载操作
   *
   * @param basePath
   */
  public void reloadProc(String basePath) {
    if (StringUtils.isNotEmpty(basePath)) {
      prop.clear();
      String pathFile = basePath + SymbolMsg.PATH + DEF_FILENAME;
      loadProperties(pathFile);
    }
  }

  public void loadProperties(String fileName) {
    if (prop.isEmpty()) {
      InputStream in = null;

      try {
        File existsChec = new File(fileName);

        if (existsChec.exists()) {
          in = new FileInputStream(existsChec);
        }

        if (null == in) {
          in = SysPropertiesUtils.class.getResourceAsStream(fileName);
        }
        if (in == null) {
          in = SysPropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
        }
        if (in == null) {
          in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        }

        if (null != in) {
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

  /**
   * 用于修改属性的值
   *
   * @param key
   * @param value
   */
  public void setValue(SysPropertyEnum key, String value) {
    prop.setProperty(key.getKey(), value);
  }
}
