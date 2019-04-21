package com.liujun.search.engine.analyze.operation.htmlanalyze;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.AnalyzeEnum;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.HtmlTagAnnotationProcess;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.HtmlTagBeforeProcess;
import com.liujun.search.engine.analyze.operation.htmlanalyze.splitwordflow.HtmlTagAnnotationFlow;
import com.liujun.search.engine.analyze.operation.htmlanalyze.splitwordflow.HtmlTagBeforeFlow;
import com.liujun.search.engine.analyze.operation.htmlanalyze.splitwordflow.HtmlTagProcessFLow;
import com.liujun.search.engine.analyze.operation.htmlanalyze.splitwordflow.HtmlTagSectionFlow;
import com.liujun.search.engine.analyze.pojo.RawDataLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 进行网页的分析操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/11
 */
public class AnalyzeService {

  /** 进行网页的分词流程 */
  private static final List<FlowServiceInf> FLOW = new ArrayList<>();

  /** 日志 */
  private Logger logger = LoggerFactory.getLogger(AnalyzeService.class);

  static {
    // 进行网页的标签段处理
    FLOW.add(HtmlTagSectionFlow.INSTANCE);
    // 2,注释的特殊处理
    FLOW.add(HtmlTagAnnotationFlow.INSTANCE);
    // 3,进行网页的开始处理
    FLOW.add(HtmlTagBeforeFlow.INSTANCE);
    // 进行网页标签的处理，去掉所有标签
    FLOW.add(HtmlTagProcessFLow.INSTANCE);
    // 开始分词处理
  }

  /**
   * 进行网页的处理
   *
   * @param rawList
   */
  private void analyzeFlow(List<RawDataLine> rawList) {}

  /**
   * 进行一个网页的分词流程
   *
   * @param rawData
   */
  private void analyzeHtml(RawDataLine rawData) {

    FlowServiceContext context = new FlowServiceContext();

    context.put(AnalyzeEnum.ANALYZE_INPUT_DATALINE.getKey(), rawData);
    context.put(
        AnalyzeEnum.ANALYZE_INPUT_HTMLCONTEXT_ARRAY.getKey(),
        rawData.getHtmlContext().toCharArray());

    try {
      for (FlowServiceInf flowItem : FLOW) {
        // 检查到返回结果流程已经结束，则做退出处理
        if (!flowItem.runFlow(context)) {
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("AnalyzeService analyzeHtml Exception", e);
    }
  }
}
