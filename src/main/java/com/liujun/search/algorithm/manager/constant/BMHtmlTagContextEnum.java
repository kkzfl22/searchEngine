package com.liujun.search.algorithm.manager.constant;

/**
 * 网页标签的内容枚举信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/19
 */
public enum BMHtmlTagContextEnum {

  /** 链接的位置 */
  HTML_HREF_URL_START("href=\""),

  /** 链接位置结束 */
  HTML_HREF_URL_END("\""),

  /** 链的地址的锚的处理 */
  HTML_HREF_FILTER_ANCHOR("#"),

  /** email的标识检查 */
  HTML_HREF_FILTER_EMAIL_FLAG("@"),

  /** 结束标识 */
  HTML_HREF_FILTER_EMAIL_COM(".com"),

  /** 结束标识 */
  HTML_HREF_FILTER_EMAIL_CN(".cn"),
  ;

  BMHtmlTagContextEnum(String pattern) {
    this.pattern = pattern;
  }

  /** 模式串信息 */
  private String pattern;

  public String getPattern() {
    return pattern;
  }
}
