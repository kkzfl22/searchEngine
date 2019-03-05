package com.liujun.search.engine.collect.html;

/**
 * 文件块的信息
 *
 * <p>目前使用以k单位来分配存储管理单元
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/05
 */
public class FileChunkMsg {

  /** 文件的索引号 */
  private int fileIndex;

  /** 文件块的起始索引 */
  private int chunkStartIndex;

  /** 文件大小 */
  private int dataLength;

  public FileChunkMsg(int fileIndex, int chunkStartIndex, int dataLength) {
    this.fileIndex = fileIndex;
    this.chunkStartIndex = chunkStartIndex;
    this.dataLength = dataLength;
  }

  public int getFileIndex() {
    return fileIndex;
  }

  public void setFileIndex(int fileIndex) {
    this.fileIndex = fileIndex;
  }

  public int getChunkStartIndex() {
    return chunkStartIndex;
  }

  public void setChunkStartIndex(int chunkStartIndex) {
    this.chunkStartIndex = chunkStartIndex;
  }

  public int getDataLength() {
    return dataLength;
  }

  public void setDataLength(int dataLength) {
    this.dataLength = dataLength;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("FileChunkMsg{");
    sb.append("fileIndex=").append(fileIndex);
    sb.append(", chunkStartIndex=").append(chunkStartIndex);
    sb.append(", dataLength=").append(dataLength);
    sb.append('}');
    return sb.toString();
  }
}
