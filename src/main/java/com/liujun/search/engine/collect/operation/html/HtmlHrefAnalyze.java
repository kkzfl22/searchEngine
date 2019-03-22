package com.liujun.search.engine.collect.operation.html;

import com.liujun.search.engine.collect.pojo.AnalyzeBusi;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 网页链接分析操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/20
 */
public class HtmlHrefAnalyze {

  public static final HtmlHrefAnalyze INSTANCE = new HtmlHrefAnalyze();

  /**
   * 获取网页链接信息
   *
   * @param htmlContext 岁页信息
   * @return 网页的链接地址信息
   */
  public Set<String> getHref(String htmlContext) {

    Set<String> result;
    if (StringUtils.isEmpty(htmlContext)) {
      result = new HashSet<>();
    } else {
      result = new HashSet<>(32);
    }

    int starPos = 0;
    char[] context = htmlContext.toCharArray();

    while (starPos < context.length) {
      AnalyzeBusi busi = HtmlHrefGet.INSTANCE.getHref(context, starPos);

      // 当发生-1说明搜索结束
      if (busi.getEndPostion() == -1) {
        break;
      }

      // 进行过滤操作
      boolean filter = HtmlHrefFilter.INSTANCE.filterCheck(busi.getHref());
      // 如果不被过滤，则加入当前集合中
      if (!filter) {
        String hrefContex = busi.getHref();

        // 进行链接内容的处理
        hrefContex = HrefContentOperation.INSTANCE.hrefContext(hrefContex);

        

        result.add(hrefContex);
      }
      starPos = busi.getEndPostion();
    }

    return result;
  }
}
