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
    if (startPostion >= scriptStartPos && startPostion <= scriptEndPos) {
      AnalyzeBusi busi = new AnalyzeBusi(null);
      busi.setEndPostion(scriptEndPos);

      context.put(HrefGetEnum.HREF_RESULT_OBJECT.getHrefKey(), busi);

      return false;
    }

    return true;
  }
}
