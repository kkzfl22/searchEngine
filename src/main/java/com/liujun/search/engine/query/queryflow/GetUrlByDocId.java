package com.liujun.search.engine.query.queryflow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.query.cache.DocIdCache;
import com.liujun.search.engine.query.constant.QueryFlowEnum;
import com.liujun.search.engine.query.pojo.QueryRsp;
import com.liujun.search.engine.query.pojo.SortDocIdBusi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取网页的URL,通过计算的网页值排名
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/17
 */
public class GetUrlByDocId implements FlowServiceInf {

  public static final GetUrlByDocId INSTANCE = new GetUrlByDocId();

  private Logger logger = LoggerFactory.getLogger(GetUrlByDocId.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    List<SortDocIdBusi> list = context.getObject(QueryFlowEnum.PROC_LIMIT_DOCLIST.getKey());

    List<QueryRsp> queryRspList = new ArrayList<>();

    for (SortDocIdBusi docIdItem : list) {
      String url = DocIdCache.INSTANCE.getUrl(docIdItem.getDocId());
      QueryRsp rsp = new QueryRsp(docIdItem.getDocId(), url);
      rsp.setCountNum(docIdItem.getCountDocId());
      queryRspList.add(rsp);
    }

    context.put(QueryFlowEnum.OUTOUT_QUERYRSP.getKey(), queryRspList);

    logger.info("query get url response {}", queryRspList);

    return true;
  }
}
