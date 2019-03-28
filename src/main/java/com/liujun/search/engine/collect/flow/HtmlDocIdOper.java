package com.liujun.search.engine.collect.flow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.CollectFlowKeyEnum;
import com.liujun.search.engine.collect.operation.BloomFilter;

/**
 * 进行网页id的存储操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/17
 */
public class HtmlDocIdOper implements FlowServiceInf {

  /** 实例对象 */
  public static final HtmlDocIdOper INSTANCE = new HtmlDocIdOper();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    return true;
  }
}
