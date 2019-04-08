package com.liujun.search.engine.collect.pojo;

/**
 * 进行docraw文件内容的读取数据的busi信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/08
 */
public class DocRawReadBusi {

  /** 当前的文件的id */
  private long docId;

  /** 文件长度信息 */
  private int docLength;

  /** 完整的网页内容信息 */
  private String htmlContext;

  /** 当前读取的文件编号 */
  private int fileIndex;

  /** 当前查找的结束的位置 */
  private int endPosition;

  public long getDocId() {
    return docId;
  }

  public void setDocId(long docId) {
    this.docId = docId;
  }

  public int getDocLength() {
    return docLength;
  }

  public void setDocLength(int docLength) {
    this.docLength = docLength;
  }

  public String getHtmlContext() {
    return htmlContext;
  }

  public void setHtmlContext(String htmlContext) {
    this.htmlContext = htmlContext;
  }

  public int getEndPosition() {
    return endPosition;
  }

  public void setEndPosition(int endPosition) {
    this.endPosition = endPosition;
  }

  public int getFileIndex() {
    return fileIndex;
  }

  public void setFileIndex(int fileIndex) {
    this.fileIndex = fileIndex;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("DocRawReadBusi{");
    sb.append("docId=").append(docId);
    sb.append(", docLength=").append(docLength);
    sb.append(", htmlContext='").append(htmlContext).append('\'');
    sb.append(", fileIndex=").append(fileIndex);
    sb.append(", endPosition=").append(endPosition);
    sb.append('}');
    return sb.toString();
  }
}
