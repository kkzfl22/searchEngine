package com.liujun.search.engine.collect.shutdown;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 停止文件队列
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/06
 */
public class CloseLogframe implements FlowServiceInf {

  public static final CloseLogframe INSTANCE = new CloseLogframe();

  private Logger logger = LoggerFactory.getLogger(CloseLogframe.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("shutdown flow log4j start ... ");

    // shutdown log4j2
    if (LogManager.getContext() instanceof LoggerContext) {
      logger.info("Shutting down log4j2");
      Configurator.shutdown((LoggerContext) LogManager.getContext());
      System.out.println("shutdown log4j...");
    } else {
      logger.warn("Unable to shutdown log4j2");
    }

    logger.info("shutdown flow log4j finish ... ");

    return true;
  }
}
