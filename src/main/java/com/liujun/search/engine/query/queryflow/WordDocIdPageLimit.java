package com.liujun.search.engine.query.queryflow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.query.constant.QueryFlowEnum;
import com.liujun.search.engine.query.pojo.SortDocIdBusi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  private Logger logger = LoggerFactory.getLogger(WordDocIdPageLimit.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {
    List<SortDocIdBusi> list = context.getObject(QueryFlowEnum.PROC_DOCLIST.getKey());
    int limit = context.getObject(QueryFlowEnum.INPUT_LIMITPAGE.getKey());

    List<SortDocIdBusi> outList = null;
    if (list != null && list.size() > limit) {
      outList = list.subList(0, limit);
    } else {
      outList = list;
    }

    context.put(QueryFlowEnum.PROC_LIMIT_DOCLIST.getKey(), outList);

    logger.info("query page limit {} finish ", outList.size());

    return true;
  }
}
