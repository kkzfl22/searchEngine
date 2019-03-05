package com.liujun.search.engine.collect;

import com.liujun.search.engine.collect.html.FileChunkMsg;

/**
 * 文件管理器
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/05
 */
public class FileManage {

  public static final FileManage INSTANCE = new FileManage();

  private FileManage() {}

  /**
   * 向文件管理系统中放入数据
   *
   * @param data 放的网页的html的内容
   * @return 数据存储的位置相关的信息
   */
  public FileChunkMsg putData(String data) {
    FileChunkMsg result = null;

    return result;
  }

  /**
   * 获取数据内容
   *
   * @param fileIndex 文件索引
   * @param chunkIndex 文件块的索引
   * @return 数据的内容信息
   */
  public String getData(int fileIndex, int chunkIndex) {
    String msg = null;

    return msg;
  }
}
