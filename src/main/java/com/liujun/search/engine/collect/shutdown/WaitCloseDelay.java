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
public class WaitCloseDelay implements FlowServiceInf {

  public static final WaitCloseDelay INSTANCE = new WaitCloseDelay();

  /** 延迟5秒关闭系统 */
  private static final long DELAY_TIME = 2000;

  private Logger logger = LoggerFactory.getLogger(WaitCloseDelay.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("shutdown flow  finis delay :" + DELAY_TIME);

    System.out.println("shutdown finish ..");

    // 将标识改为停止，当前任务停止，则直接进行停止操作
    Thread.sleep(DELAY_TIME);

    return true;
  }
}
