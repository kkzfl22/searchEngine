package com.liujun.search.engine.collect.operation.html.hrefget;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.HrefGetEnum;
import com.liujun.search.engine.collect.pojo.AnalyzeBusi;

/**
 * 需要对在<script></script>标签对中的网页链接进行过滤
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/31
 */
public class HrefFilterScript implements FlowServiceInf {

  public static final HrefFilterScript INSTANCE = new HrefFilterScript();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    int startPostion = context.getObject(HrefGetEnum.HREF_CON_ASTART_POSITION.getHrefKey());

    int scriptStartPos = context.getObject(HrefGetEnum.HREF_GET_SCRIPTSTART_INDEX.getHrefKey());
    int scriptEndPos = context.getObject(HrefGetEnum.HREF_GET_SCRIPTEND_INDEX.getHrefKey());

    // 如果当前查找的位置在script中间，则跳过当前的查找
    if (this.checkScope(startPostion, scriptStartPos, scriptEndPos)) {

      // 获取结束位置对象
      AnalyzeBusi busi = this.getAnalyze(scriptEndPos);

      context.put(HrefGetEnum.HREF_RESULT_OBJECT.getHrefKey(), busi);

      return false;
    }

    int annotationStartPos =
        context.getObject(HrefGetEnum.HREF_GET_ANNOTATION_START_INDEX.getHrefKey());
    int annotationEndPos =
        context.getObject(HrefGetEnum.HREF_GET_ANNOTATION_END_INDEX.getHrefKey());

    // 如果当前查找的位置在script中间，则跳过当前的查找
    if (this.checkScope(startPostion, annotationStartPos, annotationEndPos)) {
      // 获取结束位置对象
      AnalyzeBusi busiAnnotation = this.getAnalyze(annotationEndPos);

      context.put(HrefGetEnum.HREF_RESULT_OBJECT.getHrefKey(), busiAnnotation);

      return false;
    }

    return true;
  }

  /**
   * 检查范围
   *
   * @param startPostion 开始位置
   * @param scopeStart 范围开始位置
   * @param scopeEnd 范围结束位置
   * @return 是否在此区间
   */
  private boolean checkScope(int startPostion, int scopeStart, int scopeEnd) {

    if (startPostion >= scopeStart && startPostion <= scopeEnd) {

      return true;
    }
    return false;
  }

  /**
   * 获取分析的结束标签
   *
   * @param endPos 结束位置
   * @return 分析的信息
   */
  private AnalyzeBusi getAnalyze(int endPos) {
    AnalyzeBusi busiAnnotation = new AnalyzeBusi(null);
    busiAnnotation.setEndPostion(endPos);

    return busiAnnotation;
  }
}
