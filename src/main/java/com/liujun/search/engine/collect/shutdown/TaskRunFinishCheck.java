package com.liujun.search.engine.collect.shutdown;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.thread.RunCollectThreadManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务运行结束
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/07
 */
public class TaskRunFinishCheck implements FlowServiceInf {

  /** 每两秒进行一次检查 */
  private static final long WATI_TIME = 2000;

  public static final TaskRunFinishCheck INSTANCE = new TaskRunFinishCheck();

  private Logger logger = LoggerFactory.getLogger(TaskRunFinishCheck.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("shutdown flow check finish flag  start");


    // 需等待所有任务都进行结束
    while (!(RunCollectThreadManager.INTANACE.checkFinish())) {
      logger.info("shutdown flow not  finished ,wait {} millsecond", WATI_TIME);
      try {
        Thread.sleep(WATI_TIME);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    logger.info("shutdown flow check finish flag  finish");

    return true;
  }
}
