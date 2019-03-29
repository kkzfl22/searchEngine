package com.liujun.search.engine.collect.flow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.CollectFlowKeyEnum;
import com.liujun.search.engine.collect.operation.DocIdproc;

/**
 * 进行网页id的存储存储到文件的操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/17
 */
public class HtmlDocIdToFile implements FlowServiceInf {

  /** 实例对象 */
  public static final HtmlDocIdToFile INSTANCE = new HtmlDocIdToFile();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    // 1,获取当前网页链接
    String urlAddress = context.getObject(CollectFlowKeyEnum.FLOW_DOWNLOAD_ADDRESS.getKey());

    // 2,获取当前网页的id
    long numberId = context.getObject(CollectFlowKeyEnum.FLOW_CONTEXT_NUMBER_SEQID.getKey());

    // 3,进行将网页id的存储操作
    DocIdproc.INSTANCE.putDoc(urlAddress, numberId);

    return true;
  }
}
