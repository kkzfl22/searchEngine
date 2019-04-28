package com.liujun.search.engine.analyze.operation.htmlanalyze.splitwordflow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.AnalyzeEnum;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.HtmlTagBeforeProcess;

/**
 * 进行全标签处理之前的处理
 *
 * <p>目前是需要去掉标签中的空格，例如< a这类的空格
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/13
 */
public class HtmlTagBeforeFlow implements FlowServiceInf {

  public static final HtmlTagBeforeFlow INSTANCE = new HtmlTagBeforeFlow();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    char[] htmlArray = context.getObject(AnalyzeEnum.ANALYZE_INPUT_HTMLCONTEXT_ARRAY.getKey());

    // 去掉网页中的注释段
    char[] cleanBefore = HtmlTagBeforeProcess.INSTANCE.beforeProc(htmlArray);

    context.remove(AnalyzeEnum.ANALYZE_INPUT_HTMLCONTEXT_ARRAY.getKey());
    context.put(AnalyzeEnum.ANALYZE_INPUT_HTMLCONTEXT_ARRAY.getKey(), cleanBefore);

    return true;
  }
}
