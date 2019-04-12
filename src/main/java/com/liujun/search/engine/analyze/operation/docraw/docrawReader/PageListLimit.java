package com.liujun.search.engine.analyze.operation.docraw.docrawReader;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.DocrawReaderEnum;
import com.liujun.search.engine.analyze.pojo.RawDataLine;

import java.util.List;

/**
 * 数据转换，将数据转换为完整的网页信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/24
 */
public class PageListLimit implements FlowServiceInf {

  public static final PageListLimit INSTANCE = new PageListLimit();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    List<RawDataLine> dataLinesArrays =
        context.getObject(DocrawReaderEnum.DOCRAW_INOUTPUT_RESULT_LIST.getKey());

    Boolean outFinish = context.getObject(DocrawReaderEnum.DOCRAW_OUTPUT_FINISH_FLAG.getKey());

    int limit = context.getObject(DocrawReaderEnum.DOCRAW_INPUT_PAGELIMIT.getKey());

    if (dataLinesArrays.size() == limit || (outFinish != null && outFinish)) {
      context.put(DocrawReaderEnum.DOCRAW_OUTPUT_RETURN_FLAG.getKey(), true);
      return false;
    }

    return true;
  }
}
