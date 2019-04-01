package com.liujun.search.engine.collect.operation.html.hrefget;

import com.liujun.search.algorithm.manager.BoyerMooreManager;
import com.liujun.search.algorithm.manager.constant.BMHtmlTagContextEnum;
import com.liujun.search.algorithm.manager.constant.BMHtmlTagEnum;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.HrefGetEnum;
import com.liujun.search.engine.collect.pojo.AnalyzeBusi;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * 网页内容获取
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/31
 */
public class HrefContextGet implements FlowServiceInf {

  public static final HrefContextGet INSTANCE = new HrefContextGet();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    char[] htmlContext = context.getObject(HrefGetEnum.HTML_CONTEXT_BYTES.getHrefKey());
    int hrefAstartIndex = context.getObject(HrefGetEnum.HREF_CON_ASTART_POSITION.getHrefKey());

    // 2,然后以A标签为起始点，查找href属性
    int hrefUrlIndex =
        BoyerMooreManager.INSTANCE.getHrefIndex(
            BMHtmlTagContextEnum.HTML_HREF_URL_START.getPattern(), htmlContext, hrefAstartIndex);
    hrefUrlIndex = hrefUrlIndex + BMHtmlTagContextEnum.HTML_HREF_URL_START.getPattern().length();

    // 3,再以href的结束点为起点查找链接的结束
    int hrefUrlEndIndex =
        BoyerMooreManager.INSTANCE.getHrefIndex(
            BMHtmlTagContextEnum.HTML_HREF_URL_END.getPattern(), htmlContext, hrefUrlIndex);

    // 封装网页内容
    String hrefContext = new String(htmlContext, hrefUrlIndex, hrefUrlEndIndex - hrefUrlIndex);

    hrefContext = hrefContext.trim();
    if (StringUtils.isEmpty(hrefContext)) {
      return false;
    }

    // 4,查找结束标签
    int hrefEndIndex =
        BoyerMooreManager.INSTANCE.getHrefIndex(
            BMHtmlTagEnum.HTML_HREF.getEnd(), htmlContext, hrefUrlEndIndex);
    hrefEndIndex = hrefEndIndex + BMHtmlTagEnum.HTML_HREF.getEnd().length();

    // 封装返回对象
    AnalyzeBusi hrefBusi = new AnalyzeBusi(hrefContext);
    hrefBusi.setEndPostion(hrefEndIndex);

    context.put(HrefGetEnum.HREF_RESULT_OBJECT.getHrefKey(), hrefBusi);

    return true;
  }
}
