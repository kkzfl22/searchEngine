package com.liujun.search.engine.analyze.operation.htmlanalyze.splitwordflow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.AnalyzeEnum;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.HtmlSectionTagProcess;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.HtmlTagProcess;

/**
 * 清除所有网页的标签
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/13
 */
public class HtmlTagProcessFLow implements FlowServiceInf {

  public static final HtmlTagProcessFLow INSTANCE = new HtmlTagProcessFLow();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    char[] htmlArray = context.getObject(AnalyzeEnum.ANALYZE_INPUT_DATALINE.getKey());

    // 进行网页标签的处理，去掉网页标签段
    String htmlTagFinish = HtmlTagProcess.INSTANCE.cleanHtmlTag(htmlArray);

    context.remove(AnalyzeEnum.ANALYZE_INPUT_DATALINE.getKey());
    context.put(AnalyzeEnum.ANLYZE_OUTPUT_HTMLCONTEXT.getKey(), htmlTagFinish);

    return true;
  }
}
