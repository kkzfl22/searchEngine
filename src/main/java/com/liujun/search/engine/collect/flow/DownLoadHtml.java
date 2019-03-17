package com.liujun.search.engine.collect.flow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;

/**
 * 下载html网页
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/17
 */
public class DownLoadHtml implements FlowServiceInf {

  /** 实例对象 */
  public static final DownLoadHtml INSTANCE = new DownLoadHtml();


    @Override
    public boolean runFlow(FlowServiceContext context) throws Exception {


        return true;
    }
}
