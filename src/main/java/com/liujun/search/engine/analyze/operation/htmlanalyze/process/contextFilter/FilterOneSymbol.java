package com.liujun.search.engine.analyze.operation.htmlanalyze.process.contextFilter;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.HtmlTagFlowEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 进行符号的过滤操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/18
 */
public class FilterOneSymbol implements FlowServiceInf {

  private static final List<String> SYMBOLLIST = new ArrayList<>();

  static {
    SYMBOLLIST.add("&nbsp;");
    SYMBOLLIST.add("/");
    SYMBOLLIST.add("|");
    SYMBOLLIST.add("&gt;");
    SYMBOLLIST.add("&lt;");
    SYMBOLLIST.add("·");
    SYMBOLLIST.add("+");
    SYMBOLLIST.add("\"/>");
    SYMBOLLIST.add("-");
    SYMBOLLIST.add(":");
    SYMBOLLIST.add("[");
    SYMBOLLIST.add("]");
    SYMBOLLIST.add("：");
    SYMBOLLIST.add("丨");
    SYMBOLLIST.add("┊");
  }

  public static final FilterOneSymbol INSTANCE = new FilterOneSymbol();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    String lineData = context.getObject(HtmlTagFlowEnum.TAG_AFTER_FILTER_INPUT_CONTEXT.getKey());

    // 当检查数据为配制中的错误数时，直接跳过
    for (String data : SYMBOLLIST) {
      if (data.equals(lineData)) {
        return false;
      }
    }

    return true;
  }
}
