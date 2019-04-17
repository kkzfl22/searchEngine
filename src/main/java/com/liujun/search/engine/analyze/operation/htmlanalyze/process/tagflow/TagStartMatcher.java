package com.liujun.search.engine.analyze.operation.htmlanalyze.process.tagflow;

import com.liujun.search.algorithm.ahoCorasick.AhoCorasickChar;
import com.liujun.search.algorithm.ahoCorasick.pojo.MatcherBusi;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.HtmlTagEnum;
import com.liujun.search.engine.analyze.constant.HtmlTagFlowEnum;

/**
 * 网页标签开始匹配处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/16
 */
public class TagStartMatcher implements FlowServiceInf {

  /** 用于标识当前状态为开始标签 */
  public static final int MATCHER_TAG_START = 1;

  /** ac自动机的匹配实例信息开始标签 */
  private static final AhoCorasickChar ACMATCH_START_INSTANCE = new AhoCorasickChar();

  static {
    // 加载所有html标签，类型为<a></a>类似带开始与结束标签
    ACMATCH_START_INSTANCE.insert(HtmlTagEnum.GetHtmlStartTagList());
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

    if (matchBusi.getMatcherIndex() != -1) {
      // 标识当前当前带开始结束标签
      context.put(HtmlTagFlowEnum.TAG_PROC_FLAG_STARTEND_TYPE.getKey(), MATCHER_TAG_START);
      context.put(HtmlTagFlowEnum.TAG_PROC_FLAG_STARTMATCHER.getKey(), matchBusi);
      // 更新索引位置
      position = position + matchBusi.getMatcherKey().length();
      context.put(HtmlTagFlowEnum.TAG_INPUT_POSITION_START.getKey(), position);
    }

    // 一共有3种匹配的情况
    // 情况1,<a>
    // 情况2,<br/>
    // 情况3,</a>

    return true;
  }
}
