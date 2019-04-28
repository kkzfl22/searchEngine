package com.liujun.search.engine.analyze.operation.htmlanalyze.process.section;

import com.liujun.search.algorithm.ahoCorasick.pojo.MatcherBusi;
import com.liujun.search.algorithm.boyerMoore.CommCharMatcherInstance;
import com.liujun.search.algorithm.boyerMoore.use.CharMatcherBMBadChars;
import com.liujun.search.engine.analyze.constant.HtmlTagSectionEnum;
import com.liujun.search.utilscode.io.constant.SymbolMsg;

import java.util.HashMap;
import java.util.Map;

/**
 * 进网页段结束标签查找
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/27
 */
public class SectionEndProcess {

  /** 网页段结束标签查找实例 */
  private static final Map<String, CharMatcherBMBadChars> SECTION_END_MAP = new HashMap<>();

  static {
    HtmlTagSectionEnum[] sectionList = HtmlTagSectionEnum.values();

    // 创建实例
    for (HtmlTagSectionEnum section : sectionList) {
      SECTION_END_MAP.put(
          section.getSectionStart(),
          CharMatcherBMBadChars.getGoodSuffixInstance(section.getSectionEnd()));
    }
  }

  public static final SectionEndProcess INSTANCE = new SectionEndProcess();

  /**
   * 查找段标签的结束标签
   *
   * @param busiStart 开始标签
   * @param mainChars 主串
   * @return
   */
  public MatcherBusi sectionEndPos(MatcherBusi busiStart, char[] mainChars) {

    CharMatcherBMBadChars bmInstance = SECTION_END_MAP.get(busiStart.getMatcherKey());
    // 通过开始标签查找结束标签
    HtmlTagSectionEnum sectionTag = HtmlTagSectionEnum.GetTag(busiStart.getMatcherKey());

    // 进行模式串的匹配操作
    int endPosition = bmInstance.matcherIndex(mainChars, busiStart.getMatcherIndex());

    if (endPosition != -1) {
      MatcherBusi result = new MatcherBusi();

      result.setMatcherKey(sectionTag.getSectionEnd());
      result.setMatcherIndex(endPosition);

      return result;
    }

    // 如果当前查找不到结束标签只能查找单标签的结束
    // 则使用bm算法查找>结束位置
    endPosition =
        CommCharMatcherInstance.HTML_TAG_END_MATCHER.matcherIndex(
            mainChars, busiStart.getMatcherIndex());

    MatcherBusi result = new MatcherBusi();
    result.setMatcherKey(SymbolMsg.RIGHT_ANGLE);
    result.setMatcherIndex(endPosition);

    return result;
  }
}
