package com.liujun.search.engine.collect.operation.html.hrefget;

import com.liujun.search.algorithm.manager.BoyerMooreManager;
import com.liujun.search.algorithm.manager.constant.BMHtmlTagEnum;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.HrefGetEnum;

/**
 * 查找网页中注释的内容，在提取时需要跳过处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/31
 */
public class HrefAnnotationSearch implements FlowServiceInf {

  public static final HrefAnnotationSearch INSTANCE = new HrefAnnotationSearch();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    // 搜索script标签需要查找script标签对

    char[] htmlContext = context.getObject(HrefGetEnum.HTML_CONTEXT_BYTES.getHrefKey());
    int startPostion = context.getObject(HrefGetEnum.HREFA_START_POSITION.getHrefKey());

    // 如果为首次，则进行查找
    if (startPostion == 0) {
      // 1,以注释的开始标签<!--进行查找
      int annotationStartIndex =
          BoyerMooreManager.INSTANCE.getHrefIndex(
              BMHtmlTagEnum.HTML_ANNOTATION.getBegin(), htmlContext, startPostion);

      // 1,以注释-->标签开始查找
      int annotationEndIndex =
          BoyerMooreManager.INSTANCE.getHrefIndex(
              BMHtmlTagEnum.HTML_ANNOTATION.getEnd(), htmlContext, annotationStartIndex);

      context.put(HrefGetEnum.HREF_GET_ANNOTATION_START_INDEX.getHrefKey(), annotationStartIndex);
      context.put(HrefGetEnum.HREF_GET_ANNOTATION_END_INDEX.getHrefKey(), annotationEndIndex);

      return true;
    }

    // 如果当前出现<a标签的位置大于了annotation的结束位置，则查找位置下一个annotation的位置
    if (startPostion > 0) {
      int lastAnnotationEndIndex =
          context.getValueOrDef(HrefGetEnum.HREF_GET_ANNOTATION_END_INDEX.getHrefKey(), 0);

      if (startPostion > lastAnnotationEndIndex) {
        // 1,以<!--标签开始查找
        int annotationStartIndex =
            BoyerMooreManager.INSTANCE.getHrefIndex(
                BMHtmlTagEnum.HTML_ANNOTATION.getBegin(), htmlContext, startPostion);

        // 1,以-->标签开始查找结束标签
        int annotationEndIndex =
            BoyerMooreManager.INSTANCE.getHrefIndex(
                BMHtmlTagEnum.HTML_ANNOTATION.getEnd(), htmlContext, annotationStartIndex);

        context.put(HrefGetEnum.HREF_GET_ANNOTATION_START_INDEX.getHrefKey(), annotationStartIndex);
        context.put(HrefGetEnum.HREF_GET_ANNOTATION_END_INDEX.getHrefKey(), annotationEndIndex);
      }
    }

    return true;
  }
}
