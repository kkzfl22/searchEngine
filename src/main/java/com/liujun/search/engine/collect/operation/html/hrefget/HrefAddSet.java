package com.liujun.search.engine.collect.operation.html.hrefget;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.HrefGetEnum;
import com.liujun.search.engine.collect.operation.html.HrefContentOperation;
import com.liujun.search.engine.collect.pojo.AnalyzeBusi;

import java.util.Set;

/**
 * 将处理完成的链接添加到列表中
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/31
 */
public class HrefAddSet implements FlowServiceInf {

  public static final HrefAddSet INSTANCE = new HrefAddSet();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    AnalyzeBusi busi = context.getObject(HrefGetEnum.HREF_RESULT_OBJECT.getHrefKey());

    String hrefContex = busi.getHref();

    Set<String> hrefList = context.getObject(HrefGetEnum.HREF_RESULT_SET_OBJECT.getHrefKey());

    hrefList.add(hrefContex);

    return true;
  }
}
