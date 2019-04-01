package com.liujun.search.engine.collect.operation.html.hrefget;

import com.liujun.search.algorithm.manager.BoyerMooreManager;
import com.liujun.search.algorithm.manager.constant.BMHtmlTagEnum;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.HrefGetEnum;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/31
 */
public class HrefScriptSearch implements FlowServiceInf {

  public static final HrefScriptSearch INSTANCE = new HrefScriptSearch();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    // 搜索script标签需要查找script标签对

    char[] htmlContext = context.getObject(HrefGetEnum.HTML_CONTEXT_BYTES.getHrefKey());
    int startPostion = context.getObject(HrefGetEnum.HREFA_START_POSITION.getHrefKey());

    // 如果为首次，则进行查找
    if (startPostion == 0) {
      // 1,以script标签开始查找
      int scriptStartIndex =
          BoyerMooreManager.INSTANCE.getHrefIndex(
              BMHtmlTagEnum.HTML_SCRIPT.getBegin(), htmlContext, startPostion);

      // 1,以script标签开始查找结束标签
      int scriptEndIndex =
          BoyerMooreManager.INSTANCE.getHrefIndex(
              BMHtmlTagEnum.HTML_SCRIPT.getEnd(), htmlContext, scriptStartIndex);

      context.put(HrefGetEnum.HREF_GET_SCRIPTSTART_INDEX.getHrefKey(), scriptStartIndex);
      context.put(HrefGetEnum.HREF_GET_SCRIPTEND_INDEX.getHrefKey(), scriptEndIndex);

      return true;
    }

    // 如果当前出现<a标签的位置大于了script的结束位置，则查找位置下一个script的位置
    if (startPostion > 0) {
      int lastScriptEndIndex =
          context.getValueOrDef(HrefGetEnum.HREF_GET_SCRIPTEND_INDEX.getHrefKey(), 0);

      if (startPostion > lastScriptEndIndex) {
        // 1,以script标签开始查找
        int scriptStartIndex =
            BoyerMooreManager.INSTANCE.getHrefIndex(
                BMHtmlTagEnum.HTML_SCRIPT.getBegin(), htmlContext, startPostion);

        // 1,以script标签开始查找结束标签
        int scriptEndIndex =
            BoyerMooreManager.INSTANCE.getHrefIndex(
                BMHtmlTagEnum.HTML_SCRIPT.getEnd(), htmlContext, scriptStartIndex);

        context.put(HrefGetEnum.HREF_GET_SCRIPTSTART_INDEX.getHrefKey(), scriptStartIndex);
        context.put(HrefGetEnum.HREF_GET_SCRIPTEND_INDEX.getHrefKey(), scriptEndIndex);
      }
    }

    return true;
  }
}
