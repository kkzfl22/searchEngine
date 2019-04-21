package com.liujun.search.engine.analyze.constant;

/**
 * 网页分析流程
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/13
 */
public enum AnalyzeEnum {

  /** 数据分析的输入 */
  ANALYZE_INPUT_DATALINE("analyze_input_dataline"),

  /** 网页分析的内容，分拆为char数组 */
  ANALYZE_INPUT_HTMLCONTEXT_ARRAY("analyze_input_htmlcontext_array"),

  /** 进行网页分析的输入 */
  ANLYZE_OUTPUT_HTMLCONTEXT("analyze_output_htmlcontext"),
  ;

  private String key;

  AnalyzeEnum(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
