package com.liujun.search.engine.query.queryflow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.query.constant.QueryFlowEnum;
import com.liujun.search.engine.query.pojo.QueryRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索引擎的流程
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/17
 */
public class QueryFlow {

  /** 运行的流程 */
  private static final List<FlowServiceInf> RUNFLOW = new ArrayList<>();

  /** 日志 */
  private Logger logger = LoggerFactory.getLogger(QueryFlow.class);

  static {
    // 1,进行分词操作
    RUNFLOW.add(SpitWord.INSTANCE);
    // ,进行获取分词的偏移信息
    RUNFLOW.add(GetWordOffset.INSTANCE);
    // 进行索引文件的分组
    RUNFLOW.add(GroupFileOffset.INSTANCE);
    // 进行倒排索引读取
    RUNFLOW.add(GetDescIndex.INSTANCE);
    // 进行计算与排序
    RUNFLOW.add(CountAndOrder.INSTANCE);
    // 读取数据条数的限制
    RUNFLOW.add(WordDocIdPageLimit.INSTANCE);
    // 通过id获取网页的url
    RUNFLOW.add(GetUrlByDocId.INSTANCE);
  }

  /**
   * 进行查询
   *
   * @param contect 输入的网页内容
   * @param limit 查询的条数限制
   * @return 查询的结果
   */
  public List<QueryRsp> query(String contect, int limit) {

    boolean execRsp = false;

    FlowServiceContext context = new FlowServiceContext();

    context.put(QueryFlowEnum.INPUT_CONTEXT.getKey(), contect);
    context.put(QueryFlowEnum.INPUT_LIMITPAGE.getKey(), limit);

    try {
      for (FlowServiceInf runService : RUNFLOW) {
        if (!(execRsp = runService.runFlow(context))) {
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("QueryFlow query Exception", e);
    }

    if (execRsp) {
      return context.getObject(QueryFlowEnum.OUTOUT_QUERYRSP.getKey());
    }

    return null;
  }
}
