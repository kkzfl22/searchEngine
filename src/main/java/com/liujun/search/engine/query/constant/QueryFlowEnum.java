package com.liujun.search.engine.query.constant;

/**
 * 进行查询流程
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/13
 */
public enum QueryFlowEnum {

  /** 输入的待搜索的内容 */
  INPUT_CONTEXT("input_context"),

  /** 进行页最大限制的提取 */
  INPUT_LIMITPAGE("input_context"),

  /** 处理的分词数据 */
  PROC_SPITWORD("proc_spitword"),

  /** 处理的单词偏移信息 */
  PROC_WORDOFFSET("proc_wordoffset"),

  /** 处理过的分组单词偏移信息 */
  PROC_GROUPOFFSET("proc_groupOffset"),

  /** 网页的id信息 */
  PROC_HTMLDOCINFO("proc_htmldocinfo"),

  /** 网页全部信息 */
  PROC_DOCLIST("proc_doclist"),

  /** 计算得到的网页集合 */
  PROC_LIMIT_DOCLIST("proc_limit_doclist"),

  /** 搜索查询的结果 */
  OUTOUT_QUERYRSP("output_queryrsp"),
  ;

  private String key;

  QueryFlowEnum(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
