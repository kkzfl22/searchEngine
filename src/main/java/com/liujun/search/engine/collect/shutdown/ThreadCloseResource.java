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
public class ThreadCloseResource implements FlowServiceInf {

  public static final ThreadCloseResource INSTANCE = new ThreadCloseResource();

  private Logger logger = LoggerFactory.getLogger(ThreadCloseResource.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("shutdown flow  close rsource start");

    // 将标识改为停止，当前任务停止，则直接进行停止操作
    RunCollectThreadManager.INTANACE.closeResource();

    System.out.println("shutdown flow  close rsource  finish");
    logger.info("shutdown flow  close rsource  finish");

    return true;
  }
}
