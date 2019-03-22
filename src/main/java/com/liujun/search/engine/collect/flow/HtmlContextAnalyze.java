package com.liujun.search.engine.collect.flow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.CollectFlowKeyEnum;
import com.liujun.search.engine.collect.operation.html.HtmlHrefAnalyze;

import java.util.Set;

/**
 * 对网页内容进行分析操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/18
 */
public class HtmlContextAnalyze implements FlowServiceInf {

  public static final HtmlContextAnalyze INSTANCE = new HtmlContextAnalyze();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    String value = context.getObject(CollectFlowKeyEnum.FLOW_DOWNLOAD_CONTEXT.getKey());

    // 进行网页链接的获取操作
    Set<String> hrefValue = HtmlHrefAnalyze.INSTANCE.getHref(value);

    context.put(CollectFlowKeyEnum.FLOW_CONTEXT_HREF_LIST.getKey(), hrefValue);

    return true;
  }
}
