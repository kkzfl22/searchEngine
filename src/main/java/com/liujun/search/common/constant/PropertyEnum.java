package com.liujun.search.common.constant;

/**
 * 属性文件的枚举对象
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/01
 */
public enum PropertyEnum {

  /** 文件操作的基础路径 */
  FILE_PROCESS_PATH("file.process.path");

  private String key;

  PropertyEnum(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
