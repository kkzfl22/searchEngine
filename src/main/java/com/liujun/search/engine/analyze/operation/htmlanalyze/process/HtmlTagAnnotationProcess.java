package com.liujun.search.engine.analyze.operation.htmlanalyze.process;

import com.liujun.search.algorithm.boyerMoore.use.CharMatcherBMBadChars;
import com.liujun.search.engine.analyze.pojo.DataTagPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * 网页注释标签处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/13
 */
public class HtmlTagAnnotationProcess {

  /** 注释开始 */
  public static final String ANNOTATION_START_STR = "<!--";

  /** 注释的结束标签 */
  public static final String ANNOTATION_END_STR = "-->";

  /** 最大循环次数 */
  private static final int LOOP_MAX_NUM = 10000;

  public static final HtmlTagAnnotationProcess INSTANCE = new HtmlTagAnnotationProcess();

  /** 注释的开始 */
  private static final CharMatcherBMBadChars ANNOTATION_START =
      CharMatcherBMBadChars.getGoodSuffixInstance(ANNOTATION_START_STR);

  /** 注释的结束 */
  private static final CharMatcherBMBadChars ANNOTATION_END =
      CharMatcherBMBadChars.getGoodSuffixInstance(ANNOTATION_END_STR);

  /**
   * 网页注释段的特殊处理
   *
   * @param htmlContextArrays 网页内容
   * @return 网页信息
   */
  public char[] annotationProc(char[] htmlContextArrays, int startPos) {

    List<DataTagPosition> dataTagList = new ArrayList<>();

    int loopIndex = 0;
    while (loopIndex < LOOP_MAX_NUM) {

      // 查找注释的开始标签
      int positonStart = ANNOTATION_START.matcherIndex(htmlContextArrays, startPos);

      if (positonStart == -1) {
        break;
      }

      // 进行结束标签的查找
      int postitionEnd =
          ANNOTATION_END.matcherIndex(
              htmlContextArrays, positonStart + ANNOTATION_START_STR.length());

      if (postitionEnd == -1) {
        break;
      }

      DataTagPosition tagPost = new DataTagPosition();
      tagPost.setStart(positonStart);
      tagPost.setEnd(postitionEnd + ANNOTATION_END_STR.length());

      dataTagList.add(tagPost);

      startPos = postitionEnd + ANNOTATION_END_STR.length();

      loopIndex++;
    }

    if (loopIndex >= LOOP_MAX_NUM) {
      throw new RuntimeException("loop annotation tag process Error");
    }

    return DataTagPosCommonProc.INSTANCE.htmlRegroup(htmlContextArrays, dataTagList);
  }
}
