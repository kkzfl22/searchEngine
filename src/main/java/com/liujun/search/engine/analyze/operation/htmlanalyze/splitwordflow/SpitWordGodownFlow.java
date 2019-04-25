package com.liujun.search.engine.analyze.operation.htmlanalyze.splitwordflow;

import com.liujun.search.algorithm.ahoCorasick.pojo.MatcherBusi;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.AnalyzeEnum;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.getSpitWord.SpitWorkProcess;

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

    // 进行网页的分词流程
    // 1,使用分词表进行分词操作,如果分词成功，返回，未成功分词继续
    // 2,使用过滤的词进行匹配，匹配上，返回索引位置，未匹配上则继续
    // 3,当前面两种都示匹配时，则对当前的字符做跳过一位的处理,即索引位置向前进1

    String charContext = context.getObject(AnalyzeEnum.ANALYZE_OUTPUT_HTMLCONTEXT.getKey());

    return false;
  }
}
