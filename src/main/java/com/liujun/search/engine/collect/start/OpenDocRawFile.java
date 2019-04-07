package com.liujun.search.engine.collect.start;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.operation.docraw.DocRawProc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/06
 */
public class OpenDocRawFile implements FlowServiceInf {

  public static final OpenDocRawFile INSTANCE = new OpenDocRawFile();

  /** 日志对象 */
  private Logger logger = LoggerFactory.getLogger(OpenDocRawFile.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("collect start open docraw file start..");

    // 进行当前线程相关的初始化操作
    DocRawProc.INSTANCE.openFile();

    logger.info("collect start open docraw file  start..");

    return true;
  }
}
