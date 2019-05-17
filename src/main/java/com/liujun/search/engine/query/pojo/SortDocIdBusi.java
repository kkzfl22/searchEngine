package com.liujun.search.engine.query.pojo;

/**
 * 进行排序的网页id数据
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/16
 */
public class SortDocIdBusi implements Comparable<SortDocIdBusi> {

  /** 网页的id */
  private Long docId;

  /** 统计次数 */
  private int countDocId;

  public SortDocIdBusi(Long docId, int countDocId) {
    this.docId = docId;
    this.countDocId = countDocId;
  }

  public Long getDocId() {
    return docId;
  }

  public void setDocId(Long docId) {
    this.docId = docId;
  }

  public int getCountDocId() {
    return countDocId;
  }

  public void setCountDocId(int countDocId) {
    this.countDocId = countDocId;
  }

  @Override
  public int compareTo(SortDocIdBusi o) {

    if (this.getCountDocId() < o.getCountDocId()) {
      return 1;
    } else if (this.getCountDocId() > o.getCountDocId()) {
      return -1;
    }

    return 0;
  }
}
