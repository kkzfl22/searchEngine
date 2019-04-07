package com.liujun.search.engine.collect.operation.html.filter;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.FilterChainEnum;

import java.util.HashSet;
import java.util.Set;

/**
 * 进行内容的过滤，如链接中不存在.说明当前链接存在问题，可直接忽略
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/20
 */
public class FilterHrefContext implements FlowServiceInf {

  public static final FilterHrefContext INSTANCE = new FilterHrefContext();

  /** 完整匹配的过滤 */
  private static final Set<String> FILTER_CONTEXT = new HashSet<>();

  static {
    FILTER_CONTEXT.add(".");
  }

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    String src = context.getObject(FilterChainEnum.FILTER_SRC.getKey());

    for (String code : FILTER_CONTEXT) {
      if (src.indexOf(code) == -1) {
        return true;
      }
    }

    return false;
  }
}
