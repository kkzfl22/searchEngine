package com.liujun.search.engine.analyze.pojo;

/**
 * rowdata的行数据信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/09
 */
public class RawDataLine {

  /** 数据的序列号 */
  private long id;

  /** 数据列的长度 */
  private long length;

  /** 网页的内容信息 */
  private String htmlContext;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getLength() {
    return length;
  }

  public void setLength(long length) {
    this.length = length;
  }

  public String getHtmlContext() {
    return htmlContext;
  }

  public void setHtmlContext(String htmlContext) {
    this.htmlContext = htmlContext;
  }
}
