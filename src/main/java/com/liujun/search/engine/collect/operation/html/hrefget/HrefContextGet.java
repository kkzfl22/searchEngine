package com.liujun.search.engine.collect.operation.html.hrefget;

import com.liujun.search.algorithm.ahoCorasick.AhoCorasickChar;
import com.liujun.search.algorithm.ahoCorasick.constatnt.AcHtmlTagEnum;
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

  /** href的链接标识 */
  private static final AhoCorasickChar HREF_START = new AhoCorasickChar();

  /** href的链接结束 */
  private static final AhoCorasickChar HREF_END = new AhoCorasickChar();

  static {
    // 网页href属性的开始位置扫描
    HREF_START.insert(AcHtmlTagEnum.HREF_TAG_START.getAckey());
    // 生成失败指针
    HREF_START.builderFailurePointer();
    // 网页href属性的结束位置
    HREF_END.insert(AcHtmlTagEnum.HREF_TAG_END.getAckey());
    // 生成失败指针
    HREF_END.builderFailurePointer();
  }

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    char[] htmlContext = context.getObject(HrefGetEnum.HTML_CONTEXT_BYTES.getHrefKey());
    int hrefAstartIndex = context.getObject(HrefGetEnum.HREF_CON_ASTART_POSITION.getHrefKey());

    // 2,然后以A标签为起始点，查找href属性,href=“、'采用多模式串查找算法
    int hrefUrlIndex = HREF_START.matcherIndex(htmlContext, hrefAstartIndex);

    int hrefUrlEndIndex = 0;
    String hrefContext = null;

    // 如果当前<a 存在href属性，则进行查找中间的标签属性
    if (hrefUrlIndex != -1) {
      hrefUrlIndex = hrefUrlIndex + AcHtmlTagEnum.HREF_TAG_START.getOneLength();

      // 3,再以href的结束点为起点查找链接的结束
      hrefUrlEndIndex = HREF_END.matcherIndex(htmlContext, hrefUrlIndex);
      // 封装网页内容
      hrefContext = new String(htmlContext, hrefUrlIndex, hrefUrlEndIndex - hrefUrlIndex);

      hrefContext = hrefContext.trim();

      // 结束索引位置
      hrefUrlEndIndex = hrefUrlEndIndex + AcHtmlTagEnum.HREF_TAG_END.getOneLength();
    }
    // 标签中没有href属性，则需要跳过中间部分的查找
    else {
      hrefUrlEndIndex = hrefAstartIndex;
    }

    // 4,查找结束标签
    int hrefEndIndex =
        BoyerMooreManager.INSTANCE.getHrefIndex(
            BMHtmlTagEnum.HTML_HREF.getEnd(), htmlContext, hrefUrlEndIndex);

    // 当结束标签未查找到，则返回最后查找到的href属性的结束位置
    if (hrefEndIndex == -1) {
      // 封装返回对象
      AnalyzeBusi hrefBusi = new AnalyzeBusi(hrefContext);
      hrefBusi.setEndPostion(hrefUrlEndIndex);

      context.put(HrefGetEnum.HREF_RESULT_OBJECT.getHrefKey(), hrefBusi);
    } else {
      hrefEndIndex = hrefEndIndex + BMHtmlTagEnum.HTML_HREF.getEnd().length();

      // 封装返回对象
      AnalyzeBusi hrefBusi = new AnalyzeBusi(hrefContext);
      hrefBusi.setEndPostion(hrefEndIndex);

      context.put(HrefGetEnum.HREF_RESULT_OBJECT.getHrefKey(), hrefBusi);
    }

    if (StringUtils.isEmpty(hrefContext)) {
      return false;
    }

    return true;
  }
}
