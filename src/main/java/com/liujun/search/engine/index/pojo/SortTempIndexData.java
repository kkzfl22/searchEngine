package com.liujun.search.engine.index.pojo;

/**
 * 排序的临时索引文件
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/11
 */
public class SortTempIndexData implements Comparable<SortTempIndexData> {

  /** 索引信息 */
  private int index;

  /** 索引内容 */
  private TempIndexData indexData;

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public TempIndexData getIndexData() {
    return indexData;
  }

  public void setIndexData(TempIndexData indexData) {
    this.indexData = indexData;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("SortTempIndexData{");
    sb.append("index=").append(index);
    sb.append(", indexData=").append(indexData);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public int compareTo(SortTempIndexData o) {
    if (null == this.getIndexData() || o.getIndexData() == null) {
      return 0;
    }

    if (this.getIndexData().getTempId() > o.getIndexData().getTempId()) {
      return 1;
    } else if (this.getIndexData().getTempId() < o.getIndexData().getTempId()) {
      return -1;
    }

    return 0;
  }
}
