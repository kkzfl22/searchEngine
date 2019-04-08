package com.liujun.search.engine.collect.shutdown;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.operation.docraw.DocRawWriteProc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 保存布隆过滤器中的数据
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/06
 */
public class CloseDocrawFile implements FlowServiceInf {

  public static final CloseDocrawFile INSTANCE = new CloseDocrawFile();

  private Logger logger = LoggerFactory.getLogger(CloseDocrawFile.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("shutdown flow close doc docraw file start");

    // 保存布隆过滤器的数据
    DocRawWriteProc.INSTANCE.close();

    System.out.println("shutdown flow close doc docraw file finish");
    logger.info("shutdown flow close doc docraw file finish");

    return true;
  }
}
