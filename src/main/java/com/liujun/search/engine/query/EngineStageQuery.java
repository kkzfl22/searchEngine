package com.liujun.search.engine.query;

import com.liujun.search.engine.query.pojo.QueryRsp;
import com.liujun.search.engine.query.queryflow.QueryFlow;

import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/18
 */
public class EngineStageQuery {

  public static void main(String[] args) {

    String searchContext = "文件系统";

    int limit = 20;

    List<QueryRsp> list = QueryFlow.INSTANCE.query(searchContext, limit);

    for (QueryRsp query : list) {
      System.out.println(query);
    }
  }
}
