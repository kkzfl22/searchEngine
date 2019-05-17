package com.liujun.search.engine.query.pojo;

/**
 * 网页搜索的结果返回
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/17
 */
public class QueryRsp {

  /** 网页的id */
  private long docId;

  /** 网页的内容 */
  private String docUrl;

  /** 预留，用做网页的摘要 */
  private String docTitle;

  public QueryRsp(long docId, String docUrl) {
    this.docId = docId;
    this.docUrl = docUrl;
  }

  public long getDocId() {
    return docId;
  }

  public void setDocId(long docId) {
    this.docId = docId;
  }

  public String getDocUrl() {
    return docUrl;
  }

  public void setDocUrl(String docUrl) {
    this.docUrl = docUrl;
  }

  public String getDocTitle() {
    return docTitle;
  }

  public void setDocTitle(String docTitle) {
    this.docTitle = docTitle;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("QueryRsp{");
    sb.append("docId=").append(docId);
    sb.append(", docUrl='").append(docUrl).append('\'');
    sb.append(", docTitle='").append(docTitle).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
