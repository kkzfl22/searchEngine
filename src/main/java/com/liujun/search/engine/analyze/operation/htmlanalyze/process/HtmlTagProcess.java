package com.liujun.search.engine.analyze.operation.htmlanalyze.process;

import com.liujun.search.algorithm.ahoCorasick.AhoCorasickChar;
import com.liujun.search.algorithm.ahoCorasick.pojo.MatcherBusi;
import com.liujun.search.engine.analyze.constant.HtmlTagEnum;
import com.liujun.search.engine.analyze.constant.HtmlTagOneEnum;
import com.liujun.search.engine.analyze.constant.HtmlTagSectionEnum;
import com.liujun.search.engine.analyze.pojo.DataTagPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * 网页标签处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/13
 */
public class HtmlTagProcess {

  public static final HtmlTagProcess INSTANCE = new HtmlTagProcess();

  /** ac自动机的匹配实例信息开始标签 */
  private static final AhoCorasickChar ACMATCH_START_INSTANCE = new AhoCorasickChar();

  /** 防止死循环操作 */
  private static final int MAX_LOOP_NUM = 1000;

  static {
    // 加载所有html标签，类型为<a></a>类似带开始与结束标签
    ACMATCH_START_INSTANCE.insert(HtmlTagEnum.GetHtmlStartTagList());
    // 加载所有单标签的开始前部分标签
    ACMATCH_START_INSTANCE.insert(HtmlTagOneEnum.GetHtmlStartTagList());
    // 进行预处理操作
    ACMATCH_START_INSTANCE.builderFailurePointer();
  }

  /**
   * 清除所有网页标签
   *
   * @param htmlContext 网页内容
   * @return 网页信息
   */
  public String cleanHtmlTag(String htmlContext) {

    // 1，将所额字符进行小写
    String outContext = htmlContext.toLowerCase();
    char[] outArrays = outContext.toCharArray();

    // 2, 查找字符开始标签
    int start = 0;

    MatcherBusi findBusi = null;

    while (true) {
      findBusi = ACMATCH_START_INSTANCE.matcherOne(outArrays, start);

      //当所有都匹配完成后，则返回-1
      if (findBusi.getMatcherIndex() == -1) {
        break;
      }
      else
      {
        //否则将继续查找
        start = findBusi.getMatcherIndex();
      }
    }

    return null;
  }
}
