package com.liujun.search.engine.collect;

/**
 * html原始网页的处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/04
 */
public class DocRawProc {

  public static final DocRawProc INSTANCE = new DocRawProc();

  /** 文件中每个块的大小 */
  private static final int CHUNK = 1 * 1024;

  /**
   * 插入html数据的信息
   *
   * @param htmlCode html的内容信息
   */
  public void putHtml(long id, String htmlCode) {}

  /**
   * 获取html的内容信息
   *
   * @param id 网的id信息
   * @return 获取的网页内容信息
   */
  public String getHtml(long id) {
    String result = null;

    return result;
  }
}
