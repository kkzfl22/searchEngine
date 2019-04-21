package com.liujun.search.engine.analyze.operation.htmlanalyze.process.tabBefore;

import com.liujun.search.algorithm.ahoCorasick.AhoCorasickChar;
import com.liujun.search.algorithm.ahoCorasick.pojo.MatcherBusi;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.HtmlTagFlowEnum;

/**
 * 前置标签空格的替换操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/19
 */
public class BeforeSpaceProc implements FlowServiceInf {

  private static final String LEFT_ANGLE = "<";

  private static final String RIGHT_ANGLE = "</";

  /** 最大匹配次数 */
  private static final int MAX_MATCHER_NUM = 5000;

  /** ac自动机的匹配标签带空格的情况 */
  private static final AhoCorasickChar ACMATCH_START_INSTANCE = new AhoCorasickChar();

  static {
    ACMATCH_START_INSTANCE.insert("< ");
    ACMATCH_START_INSTANCE.insert("<\t");
    ACMATCH_START_INSTANCE.insert("</ ");
    ACMATCH_START_INSTANCE.insert("</\t");
    ACMATCH_START_INSTANCE.builderFailurePointer();
  }

  public static final BeforeSpaceProc INSTANCE = new BeforeSpaceProc();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    char[] htmlContext = context.getObject(HtmlTagFlowEnum.TAG_BEFORE_INPUT_CONTEXT_ARRAY.getKey());

    // 移动标签内的空格数据
    int moveNum = this.tagSpaceMove(htmlContext);

    char[] newDataContext = this.copyDataArray(htmlContext, moveNum);

    // 重新将文件进行计算
    context.put(HtmlTagFlowEnum.TAG_BEFORE_OUTPUT_CONTEXT.getKey(), newDataContext);
    // 进行原始无用的数据移除操作
    context.remove(HtmlTagFlowEnum.TAG_BEFORE_INPUT_CONTEXT_ARRAY.getKey());

    return true;
  }

  /**
   * 进行数据拷贝操作
   *
   * @param htmlContext 原始数据
   * @param moveNum 移动的位数
   * @return 拷贝完成的数据
   */
  private char[] copyDataArray(char[] htmlContext, int moveNum) {
    int newDataLength = htmlContext.length - moveNum;
    char[] newHtmlContext = new char[newDataLength];

    //进行数据的拷贝操作
    System.arraycopy(htmlContext, 0, newHtmlContext, 0, newDataLength);

    return newHtmlContext;
  }

  /**
   * 进行标签内空格移动的计算
   *
   * @param context
   * @return
   */
  int tagSpaceMove(char[] context) {
    int moveNum = 0;

    int startPos = 0;

    int index = 0;

    // 使用可计算循环，防止死循环操作
    while (index < MAX_MATCHER_NUM) {
      MatcherBusi busi = ACMATCH_START_INSTANCE.matcherOne(context, startPos);

      // 当字符都查找结束后，结束查找
      if (busi.getMatcherIndex() == -1) {
        break;
      }

      // 当字符查找后，进行去掉空格操作
      moveNum += this.cleanSpace(context, busi);

      // 重新计算开始标签,留在原地，用于处理多空格的问题,可做多次的替换操作
      startPos = busi.getMatcherIndex();
    }

    if (index >= MAX_MATCHER_NUM) {
      throw new RuntimeException("loop tag space ,index : " + index);
    }

    return moveNum;
  }

  /**
   * 进行空白符的清理操作
   *
   * @param context
   * @param busi
   * @return
   */
  int cleanSpace(char[] context, MatcherBusi busi) {

    // 检查是否为右尖括号
    if (busi.getMatcherKey().startsWith(RIGHT_ANGLE)) {
      int moveNum = busi.getMatcherKey().length() - RIGHT_ANGLE.length();
      this.moveContext(context, busi.getMatcherIndex() + RIGHT_ANGLE.length(), moveNum);
      return moveNum;
    }

    // 检查是否为左尖括号
    if (busi.getMatcherKey().startsWith(LEFT_ANGLE)) {
      int moveNum = busi.getMatcherKey().length() - LEFT_ANGLE.length();
      this.moveContext(context, busi.getMatcherIndex() + LEFT_ANGLE.length(), moveNum);
      return moveNum;
    }

    return 0;
  }

  /**
   * 进行位置的移动操作
   *
   * @param context
   * @param start
   * @param moveNum
   */
  void moveContext(char[] context, int start, int moveNum) {
    for (int i = start; i < context.length - moveNum; i++) {
      context[i] = context[i + moveNum];
    }
  }
}
