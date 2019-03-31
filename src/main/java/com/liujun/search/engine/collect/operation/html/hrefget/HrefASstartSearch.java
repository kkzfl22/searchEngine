package com.liujun.search.engine.collect.operation.html.hrefget;

import com.liujun.search.algorithm.manager.BoyerMooreManager;
import com.liujun.search.algorithm.manager.constant.BMHtmlTagContextEnum;
import com.liujun.search.algorithm.manager.constant.BMHtmlTagEnum;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.HrefGetEnum;
import com.liujun.search.engine.collect.pojo.AnalyzeBusi;

/**
 * 查找href开始的位置
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/31
 */
public class HrefASstartSearch implements FlowServiceInf {

  public static final HrefASstartSearch INSTANCE = new HrefASstartSearch();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    char[] htmlContext = context.getObject(HrefGetEnum.HTML_CONTEXT_BYTES.getHrefKey());
    int startPostion = context.getObject(HrefGetEnum.HREFA_START_POSITION.getHrefKey());

    // 1,以a标签为起始点，开始查找
    int hrefAstartIndex =
        BoyerMooreManager.INSTANCE.getHrefIndex(
            BMHtmlTagEnum.HTML_HREF.getBegin(), htmlContext, startPostion);

    // 当未找到结束标识则直接返回不再继续搜索
    if (hrefAstartIndex == -1) {
      AnalyzeBusi busi = new AnalyzeBusi(null);
      busi.setEndPostion(-1);

      context.put(HrefGetEnum.HREF_RESULT_OBJECT.getHrefKey(), busi);

      return false;
    }

    hrefAstartIndex = hrefAstartIndex + BMHtmlTagEnum.HTML_HREF.getBegin().length();

    context.put(HrefGetEnum.HREF_CON_ASTART_POSITION.getHrefKey(), hrefAstartIndex);
    return true;
  }
}
