package com.liujun.search.engine.analyze.constant;

/**
 * 进行分词的处理流程
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/24
 */
public enum WordFLowEnum {

  /** 输入参数，分出来的词 */
  WORD_INPUT_WORD("word_input_word"),

  /** 当前的网页编号 */
  WORKD_INPUT_DOCID("workd_input_docie"),

  /** 处理参数，获取索引号 */
  WORD_PROC_INDEX("word_proc_index"),
  ;

  private String key;

  WordFLowEnum(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }
}
