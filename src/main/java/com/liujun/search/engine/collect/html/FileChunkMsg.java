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

  /** 文件位置 */
  private long startPostion;

  /** 文件大小 */
  private int dataLength;

  public FileChunkMsg(int fileIndex, long startPostion, int dataLength) {
    this.fileIndex = fileIndex;
    this.startPostion = startPostion;
    this.dataLength = dataLength;
  }

  public int getFileIndex() {
    return fileIndex;
  }

  public void setFileIndex(int fileIndex) {
    this.fileIndex = fileIndex;
  }

  public int getDataLength() {
    return dataLength;
  }

  public void setDataLength(int dataLength) {
    this.dataLength = dataLength;
  }

  public long getStartPostion() {
    return startPostion;
  }

  public void setStartPostion(long startPostion) {
    this.startPostion = startPostion;
  }
}
