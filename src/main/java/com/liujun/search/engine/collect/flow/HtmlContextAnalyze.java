package com.liujun.search.engine.collect.flow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.CollectAnalyzeFlowKeyEnum;
import com.liujun.search.engine.collect.operation.html.HtmlHrefAnalyze;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  private Logger logger = LoggerFactory.getLogger(HtmlContextAnalyze.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("collect html context analyze start");

    char[] htmlContext =
        context.getObject(CollectAnalyzeFlowKeyEnum.FLOW_DOWNLOAD_CONTEXT_ARRAY.getKey());

    // 进行网页链接的获取操作
    Set<String> hrefValue = HtmlHrefAnalyze.INSTANCE.getHref(htmlContext);

    context.put(CollectAnalyzeFlowKeyEnum.FLOW_CONTEXT_HREF_LIST.getKey(), hrefValue);

    logger.info("collect html context analyze finish,href size:{}", hrefValue.size());

    return true;
  }
}
