package com.liujun.search.engine.collect.operation.html;

import com.liujun.search.algorithm.manager.BoyerMooreManager;
import com.liujun.search.algorithm.manager.constant.CommonPatternEnum;

/**
 * 网页的内容操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/20
 */
public class HrefContentOperation {

  /** 实例信息 */
  public static final HrefContentOperation INSTANCE = new HrefContentOperation();

  public String hrefContext(String src) {

    String outValue = src;

    // 进行锚的删除
    outValue = this.anchor(outValue);

    // 进行链接首尾字符去掉
    outValue = outValue.trim();

    return outValue;
  }

  /**
   * 网页链拉中锚的处理
   *
   * @param src 原始链接
   * @return 字符串
   */
  public String anchor(String src) {

    String srcTmp = src;
    char[] hrefArray = srcTmp.toCharArray();

    // 1,查找email的标识符
    int hrefEmailFlagIndex =
        BoyerMooreManager.INSTANCE.getHrefIndex(CommonPatternEnum.LINK_HREF_ANCHOR, hrefArray, 0);
    if (hrefEmailFlagIndex == -1) {
      return src;
    } else {
      return srcTmp.substring(0, hrefEmailFlagIndex);
    }
  }
}
