package com.liujun.search.engine.collect.shutdown;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.thread.RunCollectThreadManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 停止文件队列
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/06
 */
public class StopFileQueue implements FlowServiceInf {

  public static final StopFileQueue INSTANCE = new StopFileQueue();

  private Logger logger = LoggerFactory.getLogger(StopFileQueue.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("shutdown flow set thread flag false start");

    // 将标识改为停止，当前任务停止，则直接进行停止操作
    RunCollectThreadManager.INTANACE.stopThread();

    System.out.println("shutdown flow set thread flag false finish");
    logger.info("shutdown flow set thread flag false finish");

    return true;
  }
}
