package com.liujun.search.engine.collect.operation.html.filter;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.FilterChainEnum;

import java.util.HashSet;
import java.util.Set;

/**
 * 进行链接的过滤，当前过滤后缀规则
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/20
 */
public class FilterHrefSuffix implements FlowServiceInf {

  public static final FilterHrefSuffix INSTANCE = new FilterHrefSuffix();

  /** 完整匹配的过滤 */
  private static final Set<String> FILTER_SUffix = new HashSet<>();

  static {
    FILTER_SUffix.add(".jpg");
    FILTER_SUffix.add(".ico");
    FILTER_SUffix.add(".png");
    FILTER_SUffix.add(".gif");
  }

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    String src = context.getObject(FilterChainEnum.FILTER_SRC.getKey());

    for (String code : FILTER_SUffix) {
      if (src.indexOf(code) != -1) {
        return true;
      }
    }

    return false;
  }
}
