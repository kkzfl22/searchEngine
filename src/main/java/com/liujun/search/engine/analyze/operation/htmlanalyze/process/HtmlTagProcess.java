package com.liujun.search.engine.analyze.operation.htmlanalyze.process;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.HtmlTagFlowEnum;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.tagflow.*;
import com.liujun.search.engine.analyze.pojo.DataTagPosition;
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
  private static final int MAX_LOOP_NUM = 1000;

  private static final List<FlowServiceInf> FLOW = new ArrayList<>();

  static {
    // 进行开始标签的查找
    FLOW.add(TagStartMatcher.INSTANCE);
    // 进行结束标签的查找
    FLOW.add(TagStartFinishMatcher.INSTANCE);
    // 进行单标签的开始查找
    FLOW.add(TagOneStartMatcher.INSTANCE);
    // 进行单标签的结束查找
    FLOW.add(TagOneStartFinishMatcher.INSTANCE);
    // 进行开始带结束标签的结束查找
    FLOW.add(TagStartEndMatcher.INSTANCE);
    // 匹配完成后，则做退出处理
    FLOW.add(TagFinishOut.INSTANCE);
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

    return null;
  }

  /**
   * 查找所有的标签信息
   *
   * @param outArrays
   * @return
   */
  private List<DataTagPosition> findTagList(char[] outArrays) {
    FlowServiceContext context = new FlowServiceContext();

    context.put(HtmlTagFlowEnum.TAG_INPUT_CONTEXT_ARRAY.getKey(), outArrays);
    context.put(HtmlTagFlowEnum.TAG_INPUT_POSITION_START.getKey(), 0);
    context.put(
        HtmlTagFlowEnum.TAG_INOUTP_LIST_POSITION.getKey(), new ArrayList<DataTagPosition>());

    try {
      while (true) {

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
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("HtmlTagProcess findTagList Exception: ", e);
    }

    return context.getObject(HtmlTagFlowEnum.TAG_INOUTP_LIST_POSITION.getKey());
  }
}
