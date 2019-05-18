package com.liujun.search.engine.query;

import com.liujun.search.engine.query.pojo.QueryRsp;
import com.liujun.search.engine.query.queryflow.QueryFlow;

import java.util.List;

/**
 * 进行结果的查询操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/05/18
 */
public class Query {

  public static final Query INSTANCE = new Query();

  /**
   * 进行查询操作
   *
   * @param context 内容信息
   * @param limit 限制条数
   * @return 查询的结果集
   */
  public List<QueryRsp> query(String context, int limit) {
    return QueryFlow.INSTANCE.query(context, limit);
  }
}
