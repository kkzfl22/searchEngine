package com.liujun.search.engine.collect.html;

/**
 * html的原始网页存储
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/04
 */
public class HtmlRawInfoBusi {

  /** 原始网页的id */
  private long htmlId;

  /** 网页大小 */
  private int htmlLength;

  /** 原始网页的内容 */
  private String htmlDocument;

  public HtmlRawInfoBusi(long htmlId, String htmlDocument) {
    this.htmlId = htmlId;
    // 网页大小通过计算得到
    this.htmlLength = htmlDocument.length();
    this.htmlDocument = htmlDocument;
  }

  public long getHtmlId() {
    return htmlId;
  }

  public void setHtmlId(long htmlId) {
    this.htmlId = htmlId;
  }

  public int getHtmlLength() {
    return htmlLength;
  }

  public void setHtmlLength(int htmlLength) {
    this.htmlLength = htmlLength;
  }

  public String getHtmlDocument() {
    return htmlDocument;
  }

  public void setHtmlDocument(String htmlDocument) {
    this.htmlDocument = htmlDocument;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("HtmlRawInfoBusi{");
    sb.append("htmlId=").append(htmlId);
    sb.append(", htmlLength=").append(htmlLength);
    sb.append(", htmlDocument='").append(htmlDocument).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
