package com.liujun.search.common.properties;

import com.liujun.search.common.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * 属性文件操作
 *
 * @author liujun
 * @date 2014年6月10日
 * @vsersion 0.0.1
 */
public class PropertiesUtilProcess {

  /** 实例信息 */
  public static final PropertiesUtilProcess INSTANCE = new PropertiesUtilProcess();

  /** 日志操作 */
  private Logger logger = LoggerFactory.getLogger(PropertiesUtilProcess.class);

  /** 属性文件信息 */
  private Properties prop = new Properties();

  /** 文件信息 */
  private File operPath;

  /** 完整的文件路径 */
  public PropertiesUtilProcess() {
    String filePath = PropertiesUtilProcess.class.getClassLoader().getResource("runSave").getPath();
    String name = "runcfg.properties";

    this.operPath = new File(filePath, name);
  }

  /** 进行属性文件的加载操作 */
  public void loadProperties() {
    InputStream in = null;
    try {
      in = new FileInputStream(operPath);
      prop.load(in);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("PropertiesUtilProcess loadProperties IOException", e);
      throw new RuntimeException(e);
    } finally {
      IOUtils.close(in);
    }
  }

  /** 进行属性文件的保存操作 */
  public void saveProperties() {
    OutputStream outputStream = null;

    try {
      outputStream = new FileOutputStream(operPath);
      prop.store(outputStream, "");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("PropertiesUtilProcess saveProperties FileNotFoundException", e);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("PropertiesUtilProcess saveProperties IOException", e);
    } finally {
      IOUtils.close(outputStream);
    }
  }

  /**
   * 获取值的方法
   *
   * @param key key信息
   * @return 返回
   */
  public String getValue(String key) {
    return prop.getProperty(key);
  }

  /**
   * 进行属性的设置操作
   *
   * @param key
   * @param value
   */
  public void setValue(String key, String value) {
    this.prop.setProperty(key, value);
  }

  /**
   * 获取值带默认的方法
   *
   * @param key key信息
   * @param defValue 默认值
   * @return
   */
  public int getIntValueOrDef(String key, int defValue) {
    String value = prop.getProperty(key);

    if (StringUtils.isNotEmpty(value)) {
      value = value.trim();
      if (StringUtils.isNumeric(value)) {
        return Integer.parseInt(value);
      }
    }
    return defValue;
  }

  /**
   * 获取值带默认的方法
   *
   * @param key key信息
   * @param defValue 默认值
   * @return
   */
  public long getLongValueOrDef(String key, long defValue) {
    String value = prop.getProperty(key);

    if (StringUtils.isNotEmpty(value)) {
      value = value.trim();
      if (StringUtils.isNumeric(value)) {
        return Long.parseLong(value);
      }
    }
    return defValue;
  }
}
