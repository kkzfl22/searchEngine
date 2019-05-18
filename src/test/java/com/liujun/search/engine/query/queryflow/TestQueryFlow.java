package com.liujun.search.engine.query.queryflow;

import com.liujun.search.engine.query.pojo.QueryRsp;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 最终的查询流程
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/17
 */
public class TestQueryFlow {

  @Test
  public void testQuery() {
    String context = "正则表达式";
    int limit = 10;

    List<QueryRsp> list = QueryFlow.INSTANCE.query(context, limit);
    Assert.assertNotNull(list);
  }
}
