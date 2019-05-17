package com.liujun.search.engine.query.queryflow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.query.constant.QueryFlowEnum;
import com.liujun.search.engine.query.pojo.SortDocIdBusi;

import java.util.List;

/**
 * 分词后的网页排序数据提取
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/17
 */
public class WordDocIdPageLimit implements FlowServiceInf {

  public static final WordDocIdPageLimit INSTANCE = new WordDocIdPageLimit();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {
    List<SortDocIdBusi> list = context.getObject(QueryFlowEnum.PROC_DOCLIST.getKey());
    int limit = context.getObject(QueryFlowEnum.INPUT_LIMITPAGE.getKey());

    if (list != null && list.size() > limit) {
      List<SortDocIdBusi> outList = list.subList(0, limit);
      context.put(QueryFlowEnum.PROC_LIMIT_DOCLIST.getKey(), outList);
    } else {
      context.put(QueryFlowEnum.PROC_LIMIT_DOCLIST.getKey(), list);
    }

    return true;
  }
}
