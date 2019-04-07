package com.liujun.search.engine.collect.start;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.operation.DocIdproc;
import com.liujun.search.engine.collect.operation.docraw.DocRawProc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/06
 */
public class OpenDocIdFile implements FlowServiceInf {

  public static final OpenDocIdFile INSTANCE = new OpenDocIdFile();

  private Logger logger = LoggerFactory.getLogger(OpenDocIdFile.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("collect start open doc_id file start..");

    // 进行当前线程相关的初始化操作
    DocIdproc.INSTANCE.openFile();

    logger.info("collect start open doc_id file finish");

    return true;
  }
}
