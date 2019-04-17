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

  /** 开始处理的类型 */
  TAG_PROC_FLAG_STARTEND_TYPE("tag_proc_flag_startend_type"),

  /** 标识当前为匹配的信息 */
  TAG_PROC_FLAG_STARTMATCHER("tag_proc_flag_start_matcher"),

  /** 输入输出位置信息 */
  TAG_INOUTP_LIST_POSITION("tag_inout_list_position"),

  /** 输出标签结束 */
  TAG_OUTPUT_FINISH_FLAG("tag_output_finish_flag"),
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
