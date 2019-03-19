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
  LINK_HREF_URL("href=\""),

  /** 链接结束 */
  LINK_HREF_END("</a>"),
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
