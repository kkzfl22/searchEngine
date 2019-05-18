package com.liujun.search.engine.collect.operation.html;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.HrefGetEnum;
import com.liujun.search.engine.collect.operation.html.hrefget.*;
import com.liujun.search.engine.collect.pojo.AnalyzeBusi;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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
  private static final List<FlowServiceInf> FLOW = new ArrayList<>(6);

  static {
    // 进行开始的<a标签的查找
    FLOW.add(HrefASstartSearch.INSTANCE);
    // 网页中过滤的标签查找
    FLOW.add(HrefFilterTagSearch.INSTANCE);
    // 进行过滤操作
    FLOW.add(HrefFilterScript.INSTANCE);
    // 进行最后的网页内容处理
    FLOW.add(HrefContextGet.INSTANCE);
    // 将验证通过的数据添加到集合中
    FLOW.add(HrefCheckFilter.INSTANCE);
    // 进行网页链接编码
    FLOW.add(HrefEncoder.INSTANCE);
    // 进行网页链接的内容处理
    FLOW.add(HrefContextProc.INSTANCE);
    // 检查网页链接是否已经下载过
    FLOW.add(HrefBloomFilter.INSTANCE);
    // 将已经处理好的链接添加到集合中
    FLOW.add(HrefAddSet.INSTANCE);
  }

  private Logger logger = LoggerFactory.getLogger(HtmlHrefAnalyze.class);

  /**
   * 获取网页链接信息
   *
   * @param htmlContext 岁页信息
   * @return 网页的链接地址信息
   */
  public Set<String> getHref(String htmlContext) {

    if (StringUtils.isEmpty(htmlContext)) {
      return Collections.EMPTY_SET;
    }

    char[] anchorBytes = htmlContext.toCharArray();

    return getHref(anchorBytes);
  }

  /**
   * 获取网页链接信息
   *
   * @param anchorBytes 岁页信息
   * @return 网页的链接地址信息
   */
  public Set<String> getHref(char[] anchorBytes) {
    {
      if (anchorBytes == null || anchorBytes.length == 0) {
        return Collections.EMPTY_SET;
      }

      Set<String> result = new HashSet<>(32);

      int starPos = 0;

      FlowServiceContext context = new FlowServiceContext();

      // 存储集合的数据
      context.put(HrefGetEnum.HREF_RESULT_SET_OBJECT.getHrefKey(), result);
      context.put(HrefGetEnum.HTML_CONTEXT_BYTES.getHrefKey(), anchorBytes);

      int lastPos = 0;
      int topPostion = 0;
      try {
        while (starPos < anchorBytes.length) {

          if (starPos <= lastPos && starPos != 0) {
            throw new RuntimeException(
                "html analyze is error , position:" + starPos + ",top postion:" + topPostion);
          }

          // 进行错误位置的记录
          topPostion = lastPos;
          lastPos = starPos;

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
        // 当发生异常时，清空已经解析的数据，确保数据完整
        result.clear();
        logger.error("HtmlHrefAnalyze exception", e);
      }

      return result;
    }
  };
}
