package com.liujun.search.engine.collect.flow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.CollectAnalyzeFlowKeyEnum;
import com.liujun.search.engine.collect.operation.docraw.DocRawWriteProc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 进行网页id的存储存储到文件的操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/17
 */
public class HtmlDocrawToFile implements FlowServiceInf {

  /** 实例对象 */
  public static final HtmlDocrawToFile INSTANCE = new HtmlDocrawToFile();

  private Logger logger = LoggerFactory.getLogger(HtmlDocrawToFile.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("collect docraw start");

    // 2,获取当前网页的id
    long numberId = context.getObject(CollectAnalyzeFlowKeyEnum.FLOW_CONTEXT_NUMBER_SEQID.getKey());

    // 获取网页的内容信息
    String htmlContext = context.getObject(CollectAnalyzeFlowKeyEnum.FLOW_DOWNLOAD_CONTEXT.getKey());

    // 3,进行将网页id的存储操作
    DocRawWriteProc.INSTANCE.putHtml(numberId, htmlContext);

    logger.info("collect docraw finish");

    return true;
  }
}
