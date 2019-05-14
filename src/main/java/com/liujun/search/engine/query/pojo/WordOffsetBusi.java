package com.liujun.search.engine.query.pojo;

/**
 * 分词的偏移服务
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/13
 */
public class WordOffsetBusi {

  /** 文件索引号 */
  private int fileIndex;

  /** 分词的id */
  private int wordId;

  /** 分词的偏移量 */
  private long offset;

  /** 长度 */
  private int length;

  public int getWordId() {
    return wordId;
  }

  public void setWordId(int wordId) {
    this.wordId = wordId;
  }

  public long getOffset() {
    return offset;
  }

  public void setOffset(long offset) {
    this.offset = offset;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public int getFileIndex() {
    return fileIndex;
  }

  public void setFileIndex(int fileIndex) {
    this.fileIndex = fileIndex;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("WordOffsetBusi{");
    sb.append("fileIndex=").append(fileIndex);
    sb.append(", wordId=").append(wordId);
    sb.append(", offset=").append(offset);
    sb.append(", length=").append(length);
    sb.append('}');
    return sb.toString();
  }
}
