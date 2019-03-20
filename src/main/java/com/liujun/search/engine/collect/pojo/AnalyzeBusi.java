package com.liujun.search.engine.collect.pojo;

/**
 * 网页分析的实体信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/20
 */
public class AnalyzeBusi {

  /** 网页地址信息 */
  private String href;

  /** 网页的结束位置 */
  private int EndPostion;

  public AnalyzeBusi(String href) {
    this.href = href;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public int getEndPostion() {
    return EndPostion;
  }

  public void setEndPostion(int endPostion) {
    EndPostion = endPostion;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("AnalyzeBusi{");
    sb.append("href='").append(href).append('\'');
    sb.append(", EndPostion=").append(EndPostion);
    sb.append('}');
    return sb.toString();
  }
}
