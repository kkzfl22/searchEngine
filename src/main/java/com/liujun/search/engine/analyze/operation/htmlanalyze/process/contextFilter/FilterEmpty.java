package com.liujun.search.engine.analyze.operation.htmlanalyze.process.contextFilter;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.HtmlTagFlowEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * 进行判空操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/18
 */
public class FilterEmpty implements FlowServiceInf {

  public static final FilterEmpty INSTANCE = new FilterEmpty();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    String lineData = context.getObject(HtmlTagFlowEnum.TAG_AFTER_FILTER_INPUT_CONTEXT.getKey());

    // 当发现为null时直接返回
    if (StringUtils.isEmpty(lineData)) {
      return false;
    }

    return true;
  }
}
