package com.liujun.search.engine.collect.flow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.CollectFlowKeyEnum;
import com.liujun.search.engine.collect.operation.docraw.DocRawProc;

/**
 * 进行网页id的存储存储到文件的操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/17
 */
public class HtmlDowrawToFile implements FlowServiceInf {

  /** 实例对象 */
  public static final HtmlDowrawToFile INSTANCE = new HtmlDowrawToFile();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    // 2,获取当前网页的id
    long numberId = context.getObject(CollectFlowKeyEnum.FLOW_CONTEXT_NUMBER_SEQID.getKey());

    // 获取网页的内容信息
    String htmlContext = context.getObject(CollectFlowKeyEnum.FLOW_DOWNLOAD_CONTEXT.getKey());

    // 3,进行将网页id的存储操作
    DocRawProc.INSTANCE.putHtml(numberId, htmlContext);

    return true;
  }
}
