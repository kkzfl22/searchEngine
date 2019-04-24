package com.liujun.search.engine.analyze.constant;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/24
 */
public enum SpitWordFlowEnum {

  /** 网页的内容数组，char数组 */
  SPITWORD_INPUT_CONTEXT_ARRAYS("spitword_input_context_arrays"),

  /** 网页的内容匹配的分词位置 */
  SPITWORD_INPUT_POSITION("spitword_input_postion"),

  /** 分词的输出 */
  SPITWORD_OUTPUT_WORDBUSI("spitworkd_output_wordbusi"),
  ;

  private String key;

  SpitWordFlowEnum(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
