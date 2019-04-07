package com.liujun.search.engine.collect.constant;

/**
 * 进行采集分析流程的枚举
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/18
 */
public enum CollectAnalyzeFlowKeyEnum {

  /** 网页入口参数 */
  WEB_ENTRY("web_entry_url"),

  /** 网磁连接对象 */
  FLOW_INPUT_HTTP_CONN("flow_input_http_conntion"),

  /** 下载的地址 */
  FLOW_DOWNLOAD_ADDRESS("flow_download_url"),

  /** 下载的网页内容信息 */
  FLOW_DOWNLOAD_CONTEXT("flow_download_context"),

  /** 下载的网页内容信息的字符数组 */
  FLOW_DOWNLOAD_CONTEXT_ARRAY("flow_download_context_array"),

  /** 链接集合对象信息 */
  FLOW_CONTEXT_HREF_LIST("flow_context_href_list"),

  /** 序列号值标识 */
  FLOW_CONTEXT_NUMBER_SEQID("flow_context_number_seqid"),
  ;

  private String key;

  CollectAnalyzeFlowKeyEnum(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
