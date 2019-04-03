package com.liujun.search.engine.collect.operation.html.hrefget;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.HrefGetEnum;
import com.liujun.search.engine.collect.operation.html.HrefContentOperation;
import com.liujun.search.engine.collect.operation.html.HtmlHrefFilter;
import com.liujun.search.engine.collect.pojo.AnalyzeBusi;

import java.util.Set;

/**
 * 链接的检查以及添加到集合中
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/31
 */
public class HrefAddList implements FlowServiceInf {

  public static final HrefAddList INSTANCE = new HrefAddList();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    AnalyzeBusi busi = context.getObject(HrefGetEnum.HREF_RESULT_OBJECT.getHrefKey());

    String hrefContex = busi.getHref();


    Set<String> hrefList = context.getObject(HrefGetEnum.HREF_RESULT_SET_OBJECT.getHrefKey());
    // 进行链接内容的处理
    hrefContex = HrefContentOperation.INSTANCE.hrefContext(hrefContex);

    hrefList.add(hrefContex);

    return true;
  }
}
