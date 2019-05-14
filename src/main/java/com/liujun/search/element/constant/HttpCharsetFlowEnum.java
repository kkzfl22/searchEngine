package com.liujun.search.element.constant;

/**
 * 进行字符的操作流程
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/30
 */
public enum HttpCharsetFlowEnum {

  /** 输入流信息 */
  CHARSET_INPUT_INPUTARRAYS("charset_input_inputarrays"),

  /** 内容类型 */
  CHARSET_INPUT_CONTEXTYPE("charset_input_contextType"),

  /** 从网页内容中获取的网页编码 */
  CHARSET_PROC_GETCHARSET("charset_proc_getcharset"),

  /** 解码后输出的网页内容信息 */
  CHARSET_OUTPU_HTMLCONTEXT("charset_output_htmlcontext"),
  ;

  private String key;

  HttpCharsetFlowEnum(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }
}
