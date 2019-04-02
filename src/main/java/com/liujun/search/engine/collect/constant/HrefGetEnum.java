package com.liujun.search.engine.collect.constant;

/**
 * 进行链接的枚举获取
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/31
 */
public enum HrefGetEnum {
  /** 网页内容信息 */
  HTML_CONTEXT_BYTES("href_context_bytes"),

  /** 起始搜索的顶点 */
  HREFA_START_POSITION("href_start_position"),

  /** 搜索的标签内容<a 标签的位置 */
  HREF_CON_ASTART_POSITION("href_con_astart_posion"),

  /** 网页内容看script开始位置 */
  HREF_GET_SCRIPTSTART_INDEX("href_get_scriptstart_index"),

  /** 网页内容看script结束位置 */
  HREF_GET_SCRIPTEND_INDEX("href_get_scriptend_index"),

  /** 网页内容看annotation开始位置 */
  HREF_GET_ANNOTATION_START_INDEX("href_get_annotationstart_index"),

  /** 网页内容看annotation结束位置 */
  HREF_GET_ANNOTATION_END_INDEX("href_get_annotationend_index"),

  /** 数据返回对象信息 */
  HREF_RESULT_OBJECT("href_result_object"),

  /** 存储返回的链接地址集合 */
  HREF_RESULT_SET_OBJECT("href_reslut_set_object"),
  ;

  private String hrefKey;

  HrefGetEnum(String hrefKey) {
    this.hrefKey = hrefKey;
  }

  public String getHrefKey() {
    return hrefKey;
  }
}
