package com.liujun.search.engine.collect.operation.html.hrefget;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.HrefGetEnum;
import com.liujun.search.engine.collect.operation.html.HrefContentOperation;
import com.liujun.search.engine.collect.pojo.AnalyzeBusi;

import java.util.Set;

/**
 * 进行链接的处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/31
 */
public class HrefContextProc implements FlowServiceInf {

  public static final HrefContextProc INSTANCE = new HrefContextProc();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    AnalyzeBusi busi = context.getObject(HrefGetEnum.HREF_RESULT_OBJECT.getHrefKey());

    String hrefContex = busi.getHref();

    // 进行链接内容的处理
    hrefContex = HrefContentOperation.INSTANCE.hrefContext(hrefContex);

    busi.setHref(hrefContex);

    return true;
  }
}
