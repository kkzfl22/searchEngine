package com.liujun.search.engine.collect.flow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.CollectAnalyzeFlowKeyEnum;
import com.liujun.search.engine.collect.operation.html.HtmlFormatCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 检查是否为一个网页,即检查是否存在<html或者<div标签
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/17
 */
public class HtmlTagCheck implements FlowServiceInf {

  /** 实例对象 */
  public static final HtmlTagCheck INSTANCE = new HtmlTagCheck();

  private Logger logger = LoggerFactory.getLogger(HtmlTagCheck.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("collect html formmat check start ");

    // 获取网页信息
    char[] htmlContext =
        context.getObject(CollectAnalyzeFlowKeyEnum.FLOW_DOWNLOAD_CONTEXT_ARRAY.getKey());

    boolean htmlCheck = HtmlFormatCheck.INSTANCE.checkHtml(htmlContext, 0);

    logger.info("collect html formmat check finish,result {} ", htmlCheck);

    return htmlCheck;
  }
}
