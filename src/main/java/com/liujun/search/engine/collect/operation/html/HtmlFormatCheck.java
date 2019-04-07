package com.liujun.search.engine.collect.operation.html;

import com.liujun.search.algorithm.ahoCorasick.AhoCorasickChar;
import com.liujun.search.algorithm.ahoCorasick.constatnt.AcHtmlTagEnum;

/**
 * 网页的格式检查
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/07
 */
public class HtmlFormatCheck {

  private static AhoCorasickChar AHOHTML_CHECK = new AhoCorasickChar();

  static {
    AHOHTML_CHECK.insert(AcHtmlTagEnum.DIV_TAG_START.getAckey());
    AHOHTML_CHECK.insert(AcHtmlTagEnum.DIV_TAG_END.getAckey());
    AHOHTML_CHECK.insert(AcHtmlTagEnum.HTML_TAG_START.getAckey());
    AHOHTML_CHECK.insert(AcHtmlTagEnum.HTML_TAG_END.getAckey());

    AHOHTML_CHECK.builderFailurePointer();
  }

  public static final HtmlFormatCheck INSTANCE = new HtmlFormatCheck();

  /**
   * 检查当前是否为一个网页，用于简单的判断
   *
   * @param htmlArray 网页内容信息
   * @param startPosition 开始位置
   * @return true 当前为网页，false当前非网页
   */
  public boolean checkHtml(char[] htmlArray, int startPosition) {
    int index = AHOHTML_CHECK.matcherIndex(htmlArray, startPosition);

    if (index != -1) {
      return true;
    }
    return false;
  }
}
