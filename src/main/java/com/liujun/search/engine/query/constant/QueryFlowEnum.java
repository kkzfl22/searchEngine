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

  /** 处理的分词数据 */
  PROC_SPITWORD("proc_spitword"),

  /** 处理的单词偏移信息 */
  PROC_WORDOFFSET("proc_wordoffset"),
  ;

  private String key;

  QueryFlowEnum(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
