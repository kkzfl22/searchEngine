package com.liujun.search.engine.analyze.operation.htmlanalyze.process;

import com.liujun.search.algorithm.ahoCorasick.AhoCorasickChar;
import com.liujun.search.algorithm.ahoCorasick.pojo.MatcherBusi;
import com.liujun.search.engine.analyze.constant.HtmlTagSectionEnum;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.section.SectionEndProcess;
import com.liujun.search.engine.analyze.pojo.DataTagPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/13
 */
public class HtmlSectionTagProcess {

  public static final HtmlSectionTagProcess INSTANCE = new HtmlSectionTagProcess();

  /** ac自动机的匹配实例信息开始标签 */
  private static final AhoCorasickChar ACMATCH_START_INSTANCE = new AhoCorasickChar();

  /** ac自动机的匹配实例信息结束标签 */
  private static final AhoCorasickChar ACMATCH_END_INSTANCE = new AhoCorasickChar();

  static {
    // 获取网页标签段所有开始标签
    ACMATCH_START_INSTANCE.insert(HtmlTagSectionEnum.GetScriptStartList());
    // 进行预处理操作
    ACMATCH_START_INSTANCE.builderFailurePointer();

    // 获取网页标签段所有结束标签
    ACMATCH_END_INSTANCE.insert(HtmlTagSectionEnum.GetScriptEndList());
    // 进行预处理操作
    ACMATCH_END_INSTANCE.builderFailurePointer();
  }

  /**
   * 清除网页段标签
   *
   * @param htmlArray 网页内容
   * @return 网页信息
   */
  public char[] cleanHtmlTagSection(char[] htmlArray) {
    int startPoint = 0;

    List<DataTagPosition> dataScriptTagList = new ArrayList<>();

    int loopIndex = 0;

    // 需要去掉所有的网页段标签
    while (loopIndex <= htmlArray.length) {
      MatcherBusi busiStart = ACMATCH_START_INSTANCE.matcherOne(htmlArray, startPoint);

      if (busiStart.getMatcherIndex() != -1) {
        // 查找结束标签
        MatcherBusi busiEnd = SectionEndProcess.INSTANCE.sectionEndPos(busiStart, htmlArray);

        if (busiEnd.getMatcherIndex() != -1) {
          int endPos = busiEnd.getMatcherIndex() + busiEnd.getMatcherKey().length();
          // 更新结束位置
          startPoint = endPos;

          dataScriptTagList.add(new DataTagPosition(busiStart.getMatcherIndex(), endPos));
        } else {
          throw new RuntimeException("find error tag start :" + busiStart);
        }

      } else {
        break;
      }

      loopIndex = busiStart.getMatcherIndex() + 1;
    }

    if (loopIndex >= htmlArray.length) {
      throw new RuntimeException("loop find index : ");
    }

    return DataTagPosCommonProc.INSTANCE.htmlRegroup(htmlArray, dataScriptTagList);
  }
}
