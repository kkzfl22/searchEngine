package com.liujun.search.engine.analyze.operation.docraw.docrawReader.lineProcess;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.DocrawReaderEnum;
import com.liujun.search.engine.analyze.operation.docraw.docrawReader.LineEndMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/07
 */
public class LineDataProcFlow {



  /** 流程 */
  private static final List<FlowServiceInf> FLOW = new ArrayList<>();

  static {
    // 进行集合转换为实例对象
    FLOW.add(BytesToEntityConvert.INSTANCE);
    // 进行限制的检查
    FLOW.add(PageListLimit.INSTANCE);
  }

  public static final LineDataProcFlow INSTANCE = new LineDataProcFlow();

  private Logger logger = LoggerFactory.getLogger(LineEndMatcher.class);

  /**
   * 转换对象，并检查是否到达了，阈值的限制
   *
   * @return
   */
  public boolean parseToListAndCheck(FlowServiceContext context) {

    try {
      for (FlowServiceInf flowItem : FLOW) {
        if (!flowItem.runFlow(context)) {
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("line end matcher parseToListAndCheck Exception", e);
    }

    Boolean limitFlag = context.getObject(DocrawReaderEnum.DOCRAW_OUTPUT_RETURN_FLAG.getKey());

    if (null != limitFlag && limitFlag) {
      // context.remove(DocrawReaderEnum.DOCRAW_OUTPUT_RETURN_FLAG.getKey());
      return true;
    }

    return false;
  }


}
