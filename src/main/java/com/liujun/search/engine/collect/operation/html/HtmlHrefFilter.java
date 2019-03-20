package com.liujun.search.engine.collect.operation.html;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.FilterChainEnum;
import com.liujun.search.engine.collect.operation.html.filter.FilterHrefAll;
import com.liujun.search.engine.collect.operation.html.filter.FilterHrefEmail;
import com.liujun.search.engine.collect.operation.html.filter.FilterHrefPrefix;
import com.liujun.search.engine.collect.operation.html.filter.FilterHrefSuffix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 进行网页链接
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/20
 */
public class HtmlHrefFilter {

  /** 日志信息 */
  private Logger loggger = LoggerFactory.getLogger(HtmlHrefFilter.class);

  /** 网页过滤条件 */
  public static final HtmlHrefFilter INSTANCE = new HtmlHrefFilter();

  /** 进行过滤任务的操作 */
  private static final FlowServiceInf[] FLOW = new FlowServiceInf[4];

  static {
    // 进行整个地址的过滤操作
    FLOW[0] = FilterHrefAll.INSTANCE;
    // 1，进行前缀过滤操作
    FLOW[1] = FilterHrefPrefix.INSTANCE;
    // 2,进行后缀的过滤操作
    FLOW[2] = FilterHrefSuffix.INSTANCE;
    // 3,进行email过滤操作
    FLOW[3] = FilterHrefEmail.INSTANCE;
  }

  /**
   * 进行过滤的检查
   *
   * @param src 待检查的字符串
   * @return true 当前满足被过滤的条件，false 当前不满足被过滤的条件
   */
  public boolean filterCheck(String src) {

    boolean checkRsp = false;

    FlowServiceContext context = new FlowServiceContext();
    context.put(FilterChainEnum.FILTER_SRC.getKey(), src);

    try {
      for (FlowServiceInf flowItem : FLOW) {
        if (checkRsp = flowItem.runFlow(context)) {
          break;
        }
      }
    } catch (Exception e) {
      loggger.error("HtmlHrefFilter filterCheck Exception", e);
    }

    return checkRsp;
  }
}
