package com.liujun.search.engine.collect.flow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.common.number.NumberLoopSeq;
import com.liujun.search.common.number.SeqManager;
import com.liujun.search.common.number.SeqNameEnum;
import com.liujun.search.engine.collect.constant.CollectFlowKeyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 进行网页id的分配操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/17
 */
public class HtmlNumberId implements FlowServiceInf {

  /** 实例对象 */
  public static final HtmlNumberId INSTANCE = new HtmlNumberId();

  private Logger logger = LoggerFactory.getLogger(HtmlNumberId.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("collect number seq start ...");
    // 分配网页id
    NumberLoopSeq seqNum = SeqManager.INSTANCE.getOrCreateSeqNum(SeqNameEnum.SEQ_HTML_DOC_ID);

    long value = seqNum.NextSeqValue();

    logger.info("collect number seq finish ,sequenid {}", value);

    // 放入序列号
    context.put(CollectFlowKeyEnum.FLOW_CONTEXT_NUMBER_SEQID.getKey(), value);

    return true;
  }
}
