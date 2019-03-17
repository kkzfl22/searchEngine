package com.liujun.search.common.flow;

/**
 * 流程运行的任务接口
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/09/11
 */
public interface FlowServiceInf {

  /**
   * 运行流程
   *
   * @param context 流程的上下文对象，运行流程所需的对象都在此对象中
   * @return true countine,false return
   * @throws Exception 流程的异常信息
   */
  boolean runFlow(FlowServiceContext context) throws Exception;
}
