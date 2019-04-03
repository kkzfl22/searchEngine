package com.liujun.search.engine.collect.operation.html.hrefContext;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.HrefContextEnum;
import com.liujun.search.utilscode.io.constant.SymbolMsg;

/**
 * 进行链接的前缀处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/29
 */
public class HrefPrefix implements FlowServiceInf {

  /** */
  public static final HrefPrefix INSTANCE = new HrefPrefix();

  /** 特殊的网页链接开始头 */
  public static final String HREF_START = "//";

  /** 特殊的网页链接开始头 */
  public static final String HREF_START_ONE = "/";

  /** 普通网页的前缀 */
  private static final String PREFIX__HTTP = "http://";

  /** 加密的网页前缀 */
  private static final String PREFIX__HTTPS = "https://";

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    String hrefContext = context.getObject(HrefContextEnum.HREF_OUT.getKey());

    // 进行前缀//的删除操作
    if (hrefContext.startsWith(HREF_START)) {
      hrefContext = hrefContext.substring(HREF_START.length());
    }

    // 进行前缀/的删除操作
    if (hrefContext.startsWith(HREF_START_ONE)) {
      hrefContext = hrefContext.substring(HREF_START_ONE.length());
    }

    // 如果不包括http也不包括https则加上前缀,http
    if (!hrefContext.startsWith(PREFIX__HTTP) && !hrefContext.startsWith(PREFIX__HTTPS)) {
      hrefContext = PREFIX__HTTP + hrefContext;
    }

    // 将值重新放入到上下文中
    context.put(HrefContextEnum.HREF_OUT.getKey(), hrefContext);

    return true;
  }
}
