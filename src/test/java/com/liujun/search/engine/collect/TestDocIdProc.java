package com.liujun.search.engine.collect;

/**
 * 用于测试链接与与id的对应关系
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/05
 */
public class TestDocIdProc {

  public void putDocId() {
    // 内容信息
    String url = "";
    // id信息
    long id = 0;
    // 网页与对应的id的关系
    DocIdproc.INSTANCE.putDocId(url, id);
  }
}
