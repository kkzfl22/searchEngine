package com.liujun.search.engine.collect.flow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;

/**
 * 流程准备阶段的操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/17
 */
public class FlowInit implements FlowServiceInf {

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    // 1,从文件队列中获取地址信息
    // FileQueue.INSTANCE.get()

    return false;
  }
}
