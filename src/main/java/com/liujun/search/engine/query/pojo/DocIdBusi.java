package com.liujun.search.engine.query.pojo;

/**
 * 网页文件id与链接的对应关系
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/13
 */
public class DocIdBusi {

  /** 网页的id */
  private long docId;

  /** 网页的链接 */
  private String htmlUrl;

  public long getDocId() {
    return docId;
  }

  public void setDocId(long docId) {
    this.docId = docId;
  }

  public String getHtmlUrl() {
    return htmlUrl;
  }

  public void setHtmlUrl(String htmlUrl) {
    this.htmlUrl = htmlUrl;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("DocIdBusi{");
    sb.append("docId=").append(docId);
    sb.append(", htmlUrl='").append(htmlUrl).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
