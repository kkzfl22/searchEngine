package com.liujun.search.engine.collect.operation.html.filter;

import com.liujun.search.algorithm.manager.BoyerMooreManager;
import com.liujun.search.algorithm.manager.constant.BMHtmlTagContextEnum;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.common.utils.ByteCode;
import com.liujun.search.engine.collect.constant.FilterChainEnum;

/**
 * 进行链接的过滤，进行邮箱的过滤操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/20
 */
public class FilterHrefEmail implements FlowServiceInf {

  public static final FilterHrefEmail INSTANCE = new FilterHrefEmail();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    String src = context.getObject(FilterChainEnum.FILTER_SRC.getKey());

    byte[] anchorBytes = ByteCode.GetBytes(src);

    int startPostion = 0;

    // 1,查找email的标识符
    int hrefEmailFlagIndex =
        BoyerMooreManager.INSTANCE.getHrefIndex(
            BMHtmlTagContextEnum.HTML_HREF_FILTER_EMAIL_FLAG.getPattern(),
            anchorBytes,
            startPostion);

    if (hrefEmailFlagIndex == -1) {
      return false;
    }

    hrefEmailFlagIndex =
        hrefEmailFlagIndex + BMHtmlTagContextEnum.HTML_HREF_FILTER_EMAIL_FLAG.getPattern().length();

    // 2,然后查找.com的信息
    int hrefComIndex =
        BoyerMooreManager.INSTANCE.getHrefIndex(
            BMHtmlTagContextEnum.HTML_HREF_FILTER_EMAIL_COM.getPattern(),
            anchorBytes,
            hrefEmailFlagIndex);
    hrefComIndex =
        hrefComIndex + BMHtmlTagContextEnum.HTML_HREF_FILTER_EMAIL_COM.getPattern().length();

    // 2,然后查找.com的信息
    int hrefCnIndex =
        BoyerMooreManager.INSTANCE.getHrefIndex(
            BMHtmlTagContextEnum.HTML_HREF_FILTER_EMAIL_CN.getPattern(),
            anchorBytes,
            hrefEmailFlagIndex);
    hrefComIndex =
        hrefComIndex + BMHtmlTagContextEnum.HTML_HREF_FILTER_EMAIL_CN.getPattern().length();

    // 检查规则出现@符号，并且出现.com或者.cn
    if (hrefCnIndex != -1 || hrefComIndex != -1) {
      return true;
    }

    return false;
  }
}
