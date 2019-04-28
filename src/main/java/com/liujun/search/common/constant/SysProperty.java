package com.liujun.search.common.constant;

/**
 * 系统配制参数
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/28
 */
public enum SysProperty {

  /** 系统配制的目录 */
  CONFIG_PATH("config.path"),
  ;

  private String key;

  SysProperty(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
