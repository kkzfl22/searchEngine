package com.liujun.search.engine.analyze.operation.htmlanalyze.splitwordflow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;

/**
 * 清除所有网页的标签
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/13
 */
public class HtmlTagClean implements FlowServiceInf {

  public static final HtmlTagClean INSTANCE = new HtmlTagClean();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {
    return false;
  }
}
