package com.liujun.search.engine.collect.operation.html;

import com.liujun.search.algorithm.manager.BoyerMooreManager;
import com.liujun.search.algorithm.manager.constant.CommonPatternEnum;
import com.liujun.search.engine.collect.pojo.AnalyzeBusi;

import java.nio.charset.StandardCharsets;

/**
 * 进行网页的链接读取
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/20
 */
public class HtmlHrefGet {

  public static final HtmlHrefGet INSTANCE = new HtmlHrefGet();

  /**
   * 获取网页链接信息
   *
   * @param htmlContext 岁页信息
   * @param startPostion 网页地址
   * @return
   */
  public AnalyzeBusi getHref(byte[] htmlContext, int startPostion) {

    // 1,以a标签为起始点，开始查找
    int hrefAstartIndex =
        BoyerMooreManager.INSTANCE.getHrefIndex(
            CommonPatternEnum.LINK_HREF_START, htmlContext, startPostion);

    // 当未找到结束标识则直接返回不再继续搜索
    if (hrefAstartIndex == -1) {
      AnalyzeBusi busi = new AnalyzeBusi(null);
      busi.setEndPostion(-1);
      return busi;
    }

    hrefAstartIndex = hrefAstartIndex + CommonPatternEnum.LINK_HREF_START.getPattern().length();

    // 2,然后以A标签为起始点，查找href属性
    int hrefUrlIndex =
        BoyerMooreManager.INSTANCE.getHrefIndex(
            CommonPatternEnum.LINK_HREF_URL_START, htmlContext, hrefAstartIndex);
    hrefUrlIndex = hrefUrlIndex + CommonPatternEnum.LINK_HREF_URL_START.getPattern().length();

    // 3,再以href的结束点为起点查找链接的结束
    int hrefUrlEndIndex =
        BoyerMooreManager.INSTANCE.getHrefIndex(
            CommonPatternEnum.LINK_HREF_URL_END, htmlContext, hrefUrlIndex);

    String hrefContext =
        new String(
            htmlContext, hrefUrlIndex, hrefUrlEndIndex - hrefUrlIndex, StandardCharsets.UTF_8);

    // 4,查找结束标签
    int hrefEndIndex =
        BoyerMooreManager.INSTANCE.getHrefIndex(
            CommonPatternEnum.LINK_HREF_END, htmlContext, hrefUrlEndIndex);
    hrefEndIndex = hrefEndIndex + CommonPatternEnum.LINK_HREF_END.getPattern().length();

    AnalyzeBusi hrefBusi = new AnalyzeBusi(hrefContext);
    hrefBusi.setEndPostion(hrefEndIndex);

    return hrefBusi;
  }
}
