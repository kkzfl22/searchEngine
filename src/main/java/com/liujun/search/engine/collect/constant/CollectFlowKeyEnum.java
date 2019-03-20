package com.liujun.search.engine.collect.constant;

/**
 * 进行流程的枚举的添加
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/18
 */
public enum CollectFlowKeyEnum {

  /** 下载的地址 */
  FLOW_DOWNLOAD_ADDRESS("flow_download_url"),

  /** 下载的网页内容信息 */
  FLOW_DOWNLOAD_CONTEXT("flow_download_context"),

  /** 链接集合对象信息 */
  FLOW_CONTEXT_HREF_LIST("flow_context_href_list"),
  ;

  private String key;

  CollectFlowKeyEnum(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
