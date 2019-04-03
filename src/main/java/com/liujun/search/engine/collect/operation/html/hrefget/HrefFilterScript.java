package com.liujun.search.engine.collect.operation.html.hrefget;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.HrefGetEnum;
import com.liujun.search.engine.collect.pojo.AnalyzeBusi;
import com.liujun.search.engine.collect.pojo.FilterTagPostionBusi;

import java.util.List;

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

    List<FilterTagPostionBusi> scriptStartPos =
        context.getObject(HrefGetEnum.HTEF_FILTER_SCOPE.getHrefKey());

    if (null != scriptStartPos && !scriptStartPos.isEmpty()) {
      for (FilterTagPostionBusi tabPostion : scriptStartPos) {

        // 如果标签在过滤的范围，直接跳过处理
        if (startPostion >= tabPostion.getStartPostion()
            && startPostion <= tabPostion.getEndPostion()) {
          // 获取结束位置对象
          AnalyzeBusi busi = new AnalyzeBusi(null);
          busi.setEndPostion(tabPostion.getEndPostion());

          context.put(HrefGetEnum.HREF_RESULT_OBJECT.getHrefKey(), busi);

          return false;
        }
      }
    }

    return true;
  }
}
