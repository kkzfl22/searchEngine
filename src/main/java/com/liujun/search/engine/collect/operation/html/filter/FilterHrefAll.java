package com.liujun.search.engine.collect.operation.html.filter;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.FilterChainEnum;

import java.util.HashSet;
import java.util.Set;

/**
 * 进行字符串的完整匹配
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/20
 */
public class FilterHrefAll implements FlowServiceInf {

  public static final FilterHrefAll INSTANCE = new FilterHrefAll();

  /** 完整匹配的过滤 */
  private static final Set<String> FILTER_ALL = new HashSet<>();

  static {
    FILTER_ALL.add("/");
  }

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    String src = context.getObject(FilterChainEnum.FILTER_SRC.getKey());

    for (String code : FILTER_ALL) {
      if (code.equals(src)) {
        return true;
      }
    }

    return false;
  }
}
