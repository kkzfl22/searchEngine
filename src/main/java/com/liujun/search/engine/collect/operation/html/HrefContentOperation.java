package com.liujun.search.engine.collect.operation.html;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.HrefContextEnum;
import com.liujun.search.engine.collect.operation.html.hrefContext.HrefDeleteAnchor;
import com.liujun.search.engine.collect.operation.html.hrefContext.HrefPrefix;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 网页的内容操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/20
 */
public class HrefContentOperation {

  /** 实例信息 */
  public static final HrefContentOperation INSTANCE = new HrefContentOperation();

  /** 链接处理 */
  public static final FlowServiceInf[] RUNFLOW = new FlowServiceInf[2];

  static {
    RUNFLOW[0] = HrefDeleteAnchor.INSTANCE;
    RUNFLOW[1] = HrefPrefix.INSTANCE;
  }

  private Logger logger = LoggerFactory.getLogger(HrefContentOperation.class);

  public String hrefContext(String src) {

    if (StringUtils.isEmpty(src)) {
      return src;
    }

    // 进行链接首尾字符去掉
    src = src.trim();

    FlowServiceContext context = new FlowServiceContext();
    // 将网页放入到流程中
    context.put(HrefContextEnum.HREF_OUT.getKey(), src);

    try {
      for (FlowServiceInf runFlow : RUNFLOW) {
        runFlow.runFlow(context);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return context.getObject(HrefContextEnum.HREF_OUT.getKey());
  }
}
