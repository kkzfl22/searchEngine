package com.liujun.search.engine.collect.start;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.WebEntryEnum;
import com.liujun.search.engine.collect.operation.BloomFilter;
import com.liujun.search.engine.collect.thread.CollectThreadPool;
import com.liujun.search.engine.collect.thread.HtmCollectThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 启动数据采集线程
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/06
 */
public class StartDataCollectThread implements FlowServiceInf {

  public static final StartDataCollectThread INSTANCE = new StartDataCollectThread();

  private Logger logger = LoggerFactory.getLogger(StartDataCollectThread.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("collect start collect thread  start..");

    for (WebEntryEnum entry : WebEntryEnum.values()) {
      // 遍动网页收集线程
      CollectThreadPool.INSTANCE.submitTask(new HtmCollectThread(entry));
    }

    logger.info("collect start collect thread  finish..");

    return true;
  }
}
