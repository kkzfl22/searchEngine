package com.liujun.search.engine.analyze.operation.htmlanalyze.splitwordflow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.AnalyzeEnum;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.HtmlTagAnnotationProcess;

/**
 * 去掉网页中的注释段
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/13
 */
public class HtmlTagAnnotationFlow implements FlowServiceInf {

  public static final HtmlTagAnnotationFlow INSTANCE = new HtmlTagAnnotationFlow();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    char[] htmlArray = context.getObject(AnalyzeEnum.ANALYZE_INPUT_DATALINE.getKey());

    // 去掉网页中的注释段
    char[] cleanAnnotation = HtmlTagAnnotationProcess.INSTANCE.annotationProc(htmlArray, 0);

    context.remove(AnalyzeEnum.ANALYZE_INPUT_DATALINE.getKey());
    context.put(AnalyzeEnum.ANALYZE_INPUT_DATALINE.getKey(), cleanAnnotation);

    return true;
  }
}
