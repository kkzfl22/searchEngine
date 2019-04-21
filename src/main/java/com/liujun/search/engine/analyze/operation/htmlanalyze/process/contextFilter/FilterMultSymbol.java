package com.liujun.search.engine.analyze.operation.htmlanalyze.process.contextFilter;

import com.liujun.search.algorithm.ahoCorasick.AhoCorasickChar;
import com.liujun.search.algorithm.ahoCorasick.pojo.MatcherBusi;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.HtmlTagFlowEnum;

/**
 * 进行符号的过滤操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/18
 */
public class FilterMultSymbol implements FlowServiceInf {

  /** ac自动机的匹配标签带空格的情况 */
  private static final AhoCorasickChar MULT_SYMBOL = new AhoCorasickChar();

  /** 最大匹配次数，用于防止死循环 */
  private static final int MAX_NUM = 1000;

  static {
    // 空格
    MULT_SYMBOL.insert("&nbsp;");
    // <	小于号
    MULT_SYMBOL.insert("&lt;");
    // >	大于号
    MULT_SYMBOL.insert("&gt;");
    // &	和号
    MULT_SYMBOL.insert("&amp;");
    // "	引号
    MULT_SYMBOL.insert("&quot;");
    // '	撇号
    MULT_SYMBOL.insert("&apos;");
    // ￠	分（cent）
    MULT_SYMBOL.insert("&cent;");
    // £	镑（pound）
    MULT_SYMBOL.insert("&pound;");
    // ¥	元（yen）
    MULT_SYMBOL.insert("&yen;");
    // €	欧元（euro）
    MULT_SYMBOL.insert("&euro;");
    // §	小节
    MULT_SYMBOL.insert("&sect;");
    // ©	版权（copyright）
    MULT_SYMBOL.insert("&copy;");
    // ®	注册商标
    MULT_SYMBOL.insert("&reg;");
    // ™	商标
    MULT_SYMBOL.insert("&trade;");
    // ×	乘号
    MULT_SYMBOL.insert("&times;");
    // ÷	除号
    MULT_SYMBOL.insert("&divide;");
    // 空格
    MULT_SYMBOL.insert(" ");
    // 换行符
    MULT_SYMBOL.insert("\n");

    // 进行坏指针的生成操作
    MULT_SYMBOL.builderFailurePointer();
  }

  public static final FilterMultSymbol INSTANCE = new FilterMultSymbol();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    String lineData = context.getObject(HtmlTagFlowEnum.TAG_AFTER_FILTER_INPUT_CONTEXT.getKey());

    char[] lineDataChars = lineData.toCharArray();

    int startPos = 0;
    MatcherBusi busi = null;

    int lengthCount = 0;

    int index = 0;

    while (index < MAX_NUM) {
      busi = MULT_SYMBOL.matcherOne(lineDataChars, startPos);

      if (busi.getMatcherIndex() == -1) {
        break;
      }

      lengthCount += busi.getMatcherKey().length();
      startPos = busi.getMatcherIndex() + busi.getMatcherKey().length();
      index++;
    }

    // 当匹配的字符与原数据完成相同时，说明全为特殊符号，需要过滤掉
    if (lengthCount == lineDataChars.length) {
      return false;
    }

    return true;
  }
}
