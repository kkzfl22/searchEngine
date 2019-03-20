package com.liujun.search.algorithm.manager.constant;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/19
 */
public enum CommonPatternEnum {

  /** 字符串工 */
  LINK_HREF_START("<a"),

  /** 链接的位置 */
  LINK_HREF_URL_START("href=\""),

  /** 链接位置结束 */
  LINK_HREF_URL_END("\""),

  /** 链接结束 */
  LINK_HREF_END("</a>"),

  /** 链的地址的锚的处理 */
  LINK_HREF_ANCHOR("#"),

  /** email的标识检查 */
  HREF_EMAIL_FLAG("@"),

  /** 结束标识 */
  HREF_EMAIL_COM(".com"),

  /** 结束标识 */
  HREF_EMAIL_CN(".cn"),
  ;

  CommonPatternEnum(String pattern) {
    this.pattern = pattern;
  }

  /** 模式串信息 */
  private String pattern;

  public String getPattern() {
    return pattern;
  }
}
