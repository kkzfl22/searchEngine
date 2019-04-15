package com.liujun.search.engine.analyze.pojo;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/13
 */
public class DataTagPosition {

  /** 开始位置 */
  private int start;

  /** 结束位置 */
  private int end;

  public DataTagPosition() {}

  public DataTagPosition(int start, int end) {
    this.start = start;
    this.end = end;
  }

  public int getStart() {
    return start;
  }

  public void setStart(int start) {
    this.start = start;
  }

  public int getEnd() {
    return end;
  }

  public void setEnd(int end) {
    this.end = end;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("DataTagPosition{");
    sb.append("start=").append(start);
    sb.append(", end=").append(end);
    sb.append('}');
    return sb.toString();
  }
}
