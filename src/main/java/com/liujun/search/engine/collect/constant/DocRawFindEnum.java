package com.liujun.search.engine.collect.constant;

/**
 * 进行raw文件的查找枚举变量定义
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/24
 */
public enum DocRawFindEnum {
  /** 查找输入的id */
  INPUT_FIND_ID("input_find_id"),

  /** 输入的原始字符串信息 */
  INPUT_SRC_CHARS("input_src_chars"),

  /** 当前id是否被查找到的标识 */
  PROC_FIND_ID_FLAG("proc_find_id_flag"),

  /** 用来进行匹配id的bm算法对象 */
  PROC_FIND_MATCHER_OBJ("proc_find_matcher_obj"),

  /** 查找的id在字符串的索引位置 */
  PROC_FIND_ID_INDEX("proc_find_id_index"),

  /** 用来存储匹配的数据对象 */
  PROC_COLLECT_OUT_DATA("proc_collect_out_data"),

  /** 用来记录数据结束位置 */
  PROC_FIND_END_INDEX("proc_find_end_index"),

  /** 数据查找的输出结果信息 */
  OUT_FIND_DATA_CONTEXT("out_data_context"),

  /** 数据查找结束的标识 */
  OUT_FIND_END_FLAG("out_find_end_flag"),
  ;

  DocRawFindEnum(String key) {
    this.key = key;
  }

  /** key信息 */
  private String key;

  public String getKey() {
    return key;
  }
}
