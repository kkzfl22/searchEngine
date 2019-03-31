package com.liujun.search.algorithm.manager.constant;

/**
 * 网页标签枚举
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/31
 */
public enum BMHtmlTagEnum {

  /** 网页链接标签 */
  HTML_HREF("<a", "</a>"),

  /** 网页标签javascript的内容处理 */
  HTML_SCRIPT("<script", "</script>");

  /** 开始 */
  private String begin;

  /** 结束 */
  private String end;

  BMHtmlTagEnum(String begin, String end) {
    this.begin = begin;
    this.end = end;
  }

  public String getBegin() {
    return begin;
  }

  public String getEnd() {
    return end;
  }
}
