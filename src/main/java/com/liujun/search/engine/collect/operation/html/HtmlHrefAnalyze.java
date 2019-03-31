package com.liujun.search.engine.collect.operation.html;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.common.utils.ByteCode;
import com.liujun.search.engine.collect.constant.HrefGetEnum;
import com.liujun.search.engine.collect.operation.html.hrefget.*;
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

  /** 业务运行的流程 */
  private static final FlowServiceInf[] FLOW = new FlowServiceInf[5];

  static {
    // 进行开始的<a标签的查找
    FLOW[0] = HrefASstartSearch.INSTANCE;
    // 网页中script标签位置查找
    FLOW[1] = HrefScriptSearch.INSTANCE;
    // 当处于script中间的a标签需要被过滤
    FLOW[2] = HrefFilterScript.INSTANCE;
    // 进行最后的网页内容处理
    FLOW[3] = HrefContextGet.INSTANCE;
    // 将验证通过的数据添加到集合中
    FLOW[4] = HrefCheckAndAddList.INSTANCE;
  }

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

    char[] anchorBytes = htmlContext.toCharArray();

    FlowServiceContext context = new FlowServiceContext();

    // 存储集合的数据
    context.put(HrefGetEnum.HREF_RESULT_SET_OBJECT.getHrefKey(), result);
    context.put(HrefGetEnum.HTML_CONTEXT_BYTES.getHrefKey(), anchorBytes);

    try {
      while (starPos < anchorBytes.length) {
        // 遍历进行链接的提取操作
        context.put(HrefGetEnum.HREFA_START_POSITION.getHrefKey(), starPos);

        for (FlowServiceInf analyze : FLOW) {
          boolean flowRsp = analyze.runFlow(context);

          // 如果当前执行失败，则继续退出处理
          if (!flowRsp) {
            break;
          }
        }

        AnalyzeBusi busi = context.getObject(HrefGetEnum.HREF_RESULT_OBJECT.getHrefKey());

        // 当发生-1说明搜索结束
        if (busi.getEndPostion() == -1) {
          break;
        }

        starPos = busi.getEndPostion();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return result;
  }
}
