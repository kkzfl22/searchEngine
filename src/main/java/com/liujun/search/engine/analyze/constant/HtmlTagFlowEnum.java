package com.liujun.search.engine.analyze.constant;

/**
 * 网页标签处理流程
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/16
 */
public enum HtmlTagFlowEnum {

  /** 输入标签的内容数组 */
  TAG_INPUT_CONTEXT_ARRAY("tag_input_context_array"),

  /** 搜索的开始位置 */
  TAG_INPUT_POSITION_START("tag_input_postion_start"),

  /** 标识当前为匹配的信息 */
  TAG_PROC_FLAG_STARTMATCHER("tag_proc_flag_start_matcher"),

  /** 输入输出位置信息 */
  TAG_INOUTP_LIST_POSITION("tag_inout_list_position"),

  /** 输出标签结束 */
  TAG_OUTPUT_FINISH_FLAG("tag_output_finish_flag"),

  /** 标签后进行过滤的输入数据信息 */
  TAG_AFTER_FILTER_INPUT_CONTEXT("tag_after_filter_input_context"),

  /** 标签处理前的数据处理 */
  TAG_BEFORE_INPUT_CONTEXT_ARRAY("tag_after_filter_input_context_array"),

  /** 进行网页标签的输出操作 */
  TAG_BEFORE_OUTPUT_CONTEXT("tag_before_output_context"),
  ;

  /** 网页标签处理 */
  private String key;

  HtmlTagFlowEnum(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
