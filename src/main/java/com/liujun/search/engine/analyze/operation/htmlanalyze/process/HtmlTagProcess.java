package com.liujun.search.engine.analyze.operation.htmlanalyze.process;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.HtmlTagFlowEnum;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.contextFilter.FilterEmpty;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.contextFilter.FilterMultSymbol;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.contextFilter.FilterOneSymbol;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.tagFlow.*;
import com.liujun.search.engine.analyze.pojo.DataTagPosition;
import com.liujun.search.common.constant.SymbolMsg;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 网页标签处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/13
 */
public class HtmlTagProcess {

  public static final HtmlTagProcess INSTANCE = new HtmlTagProcess();

  /** 防止死循环操作 */
  private static final int MAX_LOOP_NUM = 50000;

  private static final List<FlowServiceInf> FLOW = new ArrayList<>();

  private static final List<FlowServiceInf> FLOWFILTER = new ArrayList<>();

  static {
    // 进行开始标签的查找
    FLOW.add(TagStartMatcher.INSTANCE);
    // 进行单标签的结束查找
    FLOW.add(TagOneStartFinishMatcher.INSTANCE);
    // 匹配完成后，则做退出处理
    FLOW.add(TagFinishOut.INSTANCE);

    // 进行空的处理
    FLOWFILTER.add(FilterEmpty.INSTANCE);
    // 进行标签的过滤操作
    FLOWFILTER.add(FilterOneSymbol.INSTANCE);
    // 进行多种组合的过滤操作
    FLOWFILTER.add(FilterMultSymbol.INSTANCE);
  }

  /** 日志 */
  private Logger logger = LoggerFactory.getLogger(HtmlTagProcess.class);

  /**
   * 清除所有网页标签
   *
   * @param htmlContextArrays 网页内容
   * @return 网页信息
   */
  public String cleanHtmlTag(char[] htmlContextArrays) {

    // 查找所有标签
    List<DataTagPosition> tagList = this.findTagList(htmlContextArrays);

    // 2,提取出所有有字符段信息
    return getContext(tagList, htmlContextArrays);
  }

  /**
   * 将标签之之外的信息抽取
   *
   * @param tagList
   * @return
   */
  public String getContext(List<DataTagPosition> tagList, char[] contArray) {

    if (null == tagList || tagList.isEmpty()) {
      return null;
    }

    StringBuilder outData = new StringBuilder();

    DataTagPosition item = null;
    String conData = null;
    int start = 0;

    FlowServiceContext filterContext = new FlowServiceContext();

    for (int i = 0; i < tagList.size(); i++) {
      conData = null;

      item = tagList.get(i);

      // 最头上的标签
      if (i == 0) {
        if (item.getStart() == 0) {
          start = item.getEnd();
          continue;
        } else {
          conData = new String(contArray, 0, item.getStart());
        }

      }
      // 最末尾的标签
      else if (i == tagList.size() - 1) {
        if (item.getEnd() == contArray.length) {
          continue;
        } else {
          conData = new String(contArray, item.getEnd(), contArray.length - item.getEnd());
        }
      }
      // 中间的标签
      else {
        if (item.getStart() - start > 0) {
          conData = new String(contArray, start, item.getStart() - start);
        }
      }

      // 进行数据行的处理操作
      conData = lineProcess(conData, filterContext);

      if (null != conData) {
        outData.append(conData);
        outData.append(SymbolMsg.LINE);
      }

      start = item.getEnd();
    }

    return outData.toString();
  }

  /**
   * 进行行数据的处理操作
   *
   * @param conData 行内容信息
   * @return 数据处理
   */
  private String lineProcess(String conData, FlowServiceContext filterContext) {
    conData = StringUtils.trimToEmpty(conData);

    if (null != conData && !"".equals(conData) && conData.length() > 0) {

      conData = conData.trim();
      // 文件的输入
      filterContext.put(HtmlTagFlowEnum.TAG_AFTER_FILTER_INPUT_CONTEXT.getKey(), conData);

      boolean filterRsp = true;
      try {

        for (FlowServiceInf service : FLOWFILTER) {
          if (!service.runFlow(filterContext)) {
            filterRsp = false;
            break;
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
        logger.error("line data filter exception:", e);
      }

      // 如果当前为有效的数据则返回，否则返回无效
      if (filterRsp) {
        return conData;
      }
    }
    return null;
  }

  /**
   * 查找所有的标签信息
   *
   * @param outArrays 输出的字符信息
   * @return 提取的标签信息
   */
  private List<DataTagPosition> findTagList(char[] outArrays) {
    FlowServiceContext context = new FlowServiceContext();

    context.put(HtmlTagFlowEnum.TAG_INPUT_CONTEXT_ARRAY.getKey(), outArrays);
    context.put(HtmlTagFlowEnum.TAG_INPUT_POSITION_START.getKey(), 0);
    context.put(
        HtmlTagFlowEnum.TAG_INOUTP_LIST_POSITION.getKey(), new ArrayList<DataTagPosition>());

    try {
      int loopIndex = 0;
      while (loopIndex < MAX_LOOP_NUM) {

        for (FlowServiceInf flowItem : FLOW) {
          if (!flowItem.runFlow(context)) {
            break;
          }
        }

        Boolean finishFlag = context.getObject(HtmlTagFlowEnum.TAG_OUTPUT_FINISH_FLAG.getKey());

        // 当检查当前前已经结束，则退出
        if (null != finishFlag && finishFlag) {
          break;
        }
        loopIndex++;

        context.remove(HtmlTagFlowEnum.TAG_PROC_FLAG_STARTMATCHER.getKey());
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("HtmlTagProcess findTagList Exception: ", e);
    }

    return context.getObject(HtmlTagFlowEnum.TAG_INOUTP_LIST_POSITION.getKey());
  }
}
