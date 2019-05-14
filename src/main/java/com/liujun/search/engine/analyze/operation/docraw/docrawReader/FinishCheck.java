package com.liujun.search.engine.analyze.operation.docraw.docrawReader;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.DocrawReaderEnum;
import com.liujun.search.engine.analyze.pojo.RawDataLine;

import java.util.List;

/**
 * 结束符检查
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/24
 */
public class FinishCheck implements FlowServiceInf {

  public static final FinishCheck INSTANCE = new FinishCheck();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    Boolean outFinish = context.getObject(DocrawReaderEnum.DOCRAW_OUTPUT_FINISH_FLAG.getKey());

    if (outFinish != null && outFinish) {
      context.put(DocrawReaderEnum.DOCRAW_OUTPUT_RETURN_FLAG.getKey(), true);
      return false;
    }

    return true;
  }
}
