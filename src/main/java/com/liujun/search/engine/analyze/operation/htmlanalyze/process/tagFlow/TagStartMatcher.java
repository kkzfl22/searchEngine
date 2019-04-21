package com.liujun.search.engine.analyze.operation.htmlanalyze.process.tagFlow;

import com.liujun.search.algorithm.ahoCorasick.AhoCorasickChar;
import com.liujun.search.algorithm.ahoCorasick.pojo.MatcherBusi;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.HtmlTagEnum;
import com.liujun.search.engine.analyze.constant.HtmlTagFlowEnum;
import com.liujun.search.engine.analyze.constant.HtmlTagOneEnum;

/**
 * 网页标签开始匹配处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/16
 */
public class TagStartMatcher implements FlowServiceInf {

  /** ac自动机的匹配实例信息开始标签 */
  private static final AhoCorasickChar ACMATCH_START_INSTANCE = new AhoCorasickChar();

  static {
    // 加载所有html标签，类型为<a></a>类似带开始与结束标签
    ACMATCH_START_INSTANCE.insert(HtmlTagEnum.GetHtmlStartTagList());
    // 加载所有html标签，仅有前段标签，无结束标签，如<br/>
    ACMATCH_START_INSTANCE.insert(HtmlTagOneEnum.GetHtmlStartTagList());
    // 结束标签匹配
    ACMATCH_START_INSTANCE.insert(HtmlTagEnum.GetHtmlEndTagList());
    // 进行预处理操作
    ACMATCH_START_INSTANCE.builderFailurePointer();
  }

  public static final TagStartMatcher INSTANCE = new TagStartMatcher();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {
    // 1,通过字符串匹配算法，提取开始与结束位置
    char[] mainChars = context.getObject(HtmlTagFlowEnum.TAG_INPUT_CONTEXT_ARRAY.getKey());
    int position = context.getObject(HtmlTagFlowEnum.TAG_INPUT_POSITION_START.getKey());

    // 进行不区分大小写的匹配操作
    MatcherBusi matchBusi = ACMATCH_START_INSTANCE.matcherIgnoreCaseOne(mainChars, position);

    context.put(HtmlTagFlowEnum.TAG_PROC_FLAG_STARTMATCHER.getKey(), matchBusi);

    if (matchBusi.getMatcherIndex() != -1) {
      // 更新索引位置
      position = matchBusi.getMatcherIndex() + matchBusi.getMatcherKey().length();
      context.put(HtmlTagFlowEnum.TAG_INPUT_POSITION_START.getKey(), position);
    }

    // 进行检查当前是否为结束标签，如果为结束则需要进行返回操作

    // 一共有3种匹配的情况
    // 情况1,<a>
    // 情况2,<br/>
    // 情况3,</a>

    return true;
  }
}
