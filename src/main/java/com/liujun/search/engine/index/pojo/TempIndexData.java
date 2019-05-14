package com.liujun.search.engine.index.pojo;

/**
 * 临时索引内容信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/09
 */
public class TempIndexData implements Comparable<TempIndexData> {

  /** 索引中的单词编号 */
  private int tempId;

  /** 网页编号的id */
  private long docSeqId;

  public int getTempId() {
    return tempId;
  }

  public void setTempId(int tempId) {
    this.tempId = tempId;
  }

  public long getDocSeqId() {
    return docSeqId;
  }

  public void setDocSeqId(long docSeqId) {
    this.docSeqId = docSeqId;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("TempIndexData{");
    sb.append("tempId=").append(tempId);
    sb.append(", docSeqId=").append(docSeqId);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public int compareTo(TempIndexData o) {

    if (this.getTempId() > o.getTempId()) {
      return 1;
    } else if (this.getTempId() < o.getTempId()) {
      return -1;
    }

    return 0;
  }
}
