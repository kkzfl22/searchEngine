package com.liujun.search.engine.collect.pojo;

/**
 * 过滤标签的位置信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/03
 */
public class FilterTagPostionBusi {

  /** 开始位置 */
  private int startPostion;

  /** 结束位置 */
  private int endPostion;

  public FilterTagPostionBusi(int startPostion, int endPostion) {
    this.startPostion = startPostion;
    this.endPostion = endPostion;
  }

  public int getStartPostion() {
    return startPostion;
  }

  public void setStartPostion(int startPostion) {
    this.startPostion = startPostion;
  }

  public int getEndPostion() {
    return endPostion;
  }

  public void setEndPostion(int endPostion) {
    this.endPostion = endPostion;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("FilterTagPostionBusi{");
    sb.append("startPostion=").append(startPostion);
    sb.append(", endPostion=").append(endPostion);
    sb.append('}');
    return sb.toString();
  }
}
