package com.liujun.search.engine.analyze.operation.htmlanalyze.process;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.HtmlTagFlowEnum;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.tabBefore.BeforeSpaceProc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 网页标签前置处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/13
 */
public class HtmlTagBeforeProcess {

  /** 进行业务流程的执行操作 */
  private static final List<FlowServiceInf> RUNFLOW = new ArrayList<>(1);

  static {
    // 进行标签的空格替换操作
    RUNFLOW.add(BeforeSpaceProc.INSTANCE);
  }

  public static final HtmlTagBeforeProcess INSTANCE = new HtmlTagBeforeProcess();

  private Logger logger = LoggerFactory.getLogger(HtmlTagBeforeProcess.class);

  /**
   * 前置处理
   *
   * @param htmlContextArrays 网页内容
   * @return 网页信息
   */
  public char[] beforeProc(char[] htmlContextArrays) {

    FlowServiceContext context = new FlowServiceContext();

    context.put(HtmlTagFlowEnum.TAG_BEFORE_INPUT_CONTEXT_ARRAY.getKey(), htmlContextArrays);

    try {
      for (FlowServiceInf flowItem : RUNFLOW) {
        if (!flowItem.runFlow(context)) {
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("html tag before process exception", e);
    }

    return context.getObject(HtmlTagFlowEnum.TAG_BEFORE_OUTPUT_CONTEXT.getKey());
  }
}
