package com.liujun.search.engine.collect.constant;

/**
 * 进行过滤链的枚举
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/20
 */
public enum FilterChainEnum {

  /** 原始待过滤的字符串信息 */
  FILTER_SRC("src_chars"),
  ;

  FilterChainEnum(String key) {
    this.key = key;
  }

  /** key信息 */
  private String key;

  public String getKey() {
    return key;
  }
}
