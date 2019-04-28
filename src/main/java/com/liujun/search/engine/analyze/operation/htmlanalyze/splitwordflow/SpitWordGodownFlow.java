package com.liujun.search.engine.analyze.operation.htmlanalyze.splitwordflow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.AnalyzeEnum;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.SpitWordProcess;
import com.liujun.search.engine.analyze.pojo.RawDataLine;

/**
 * 使用词库进行分词操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/22
 */
public class SpitWordGodownFlow implements FlowServiceInf {

  public static final SpitWordGodownFlow INSTANCE = new SpitWordGodownFlow();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    String charContext = context.getObject(AnalyzeEnum.ANALYZE_OUTPUT_HTMLCONTEXT.getKey());

    RawDataLine data = context.getObject(AnalyzeEnum.ANALYZE_INPUT_DATALINE.getKey());

    // 进行网页的分词操作
    SpitWordProcess.INSTANCE.spitWord(charContext, (int) data.getId());

    return true;
  }
}
