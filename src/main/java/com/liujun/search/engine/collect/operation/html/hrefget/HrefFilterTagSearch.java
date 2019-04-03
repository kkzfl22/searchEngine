package com.liujun.search.engine.collect.operation.html.hrefget;

import com.liujun.search.algorithm.manager.BoyerMooreManager;
import com.liujun.search.algorithm.manager.constant.BMHtmlTagEnum;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.HrefGetEnum;
import com.liujun.search.engine.collect.pojo.FilterTagPostionBusi;

import java.util.ArrayList;
import java.util.List;

/**
 * 查找网页中注释的内容，在提取时需要跳过处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/31
 */
public class HrefFilterTagSearch implements FlowServiceInf {

  public static final HrefFilterTagSearch INSTANCE = new HrefFilterTagSearch();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    // 搜索script标签需要查找script标签对

    char[] htmlContext = context.getObject(HrefGetEnum.HTML_CONTEXT_BYTES.getHrefKey());
    int startPostion = context.getObject(HrefGetEnum.HREFA_START_POSITION.getHrefKey());

    // 如果为首次，则进行查找
    if (startPostion == 0) {

      List<FilterTagPostionBusi> filterList = new ArrayList<>();
      // 1,查找所有注释的标签,添加到过滤列表中
      this.queryFilter(htmlContext, startPostion, BMHtmlTagEnum.HTML_ANNOTATION, filterList);
      // 2，查找所有script标签，汪厍到过滤列表中
      this.queryFilter(htmlContext, startPostion, BMHtmlTagEnum.HTML_SCRIPT, filterList);

      context.put(HrefGetEnum.HTEF_FILTER_SCOPE.getHrefKey(), filterList);

      return true;
    }

    return true;
  }

  /**
   * 搜索网页标签中所过滤的标签的位置
   *
   * @param htmlContext
   * @param startPostionInput 输入的位置
   * @return
   */
  private void queryFilter(
      char[] htmlContext,
      int startPostionInput,
      BMHtmlTagEnum htmlTag,
      List<FilterTagPostionBusi> filterList) {
    int searchIndex = startPostionInput;
    int maxLength = htmlContext.length;

    while (searchIndex < maxLength) {

      // 1,以标签开始查找
      int startIndex =
          BoyerMooreManager.INSTANCE.getHrefIndex(htmlTag.getBegin(), htmlContext, searchIndex);

      if (startIndex == -1) {
        break;
      }

      // 1,以标签开始查找结束标签
      int endIndex =
          BoyerMooreManager.INSTANCE.getHrefIndex(htmlTag.getEnd(), htmlContext, startIndex);

      if (endIndex == -1) {
        endIndex = maxLength;
      }

      // 添加过滤对象
      FilterTagPostionBusi tabPostionBusi = new FilterTagPostionBusi(startIndex, endIndex);
      filterList.add(tabPostionBusi);

      // 以此次的结束位置作为下一次的开始位置
      searchIndex = endIndex;
    }
  }
}
