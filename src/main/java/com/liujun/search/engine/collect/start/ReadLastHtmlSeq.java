package com.liujun.search.engine.collect.start;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.common.number.NumberLoopSeq;
import com.liujun.search.common.number.SeqManager;
import com.liujun.search.common.number.SeqNameEnum;
import com.liujun.search.engine.collect.operation.DocIdproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取网页最后的序列号文件
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/06
 */
public class ReadLastHtmlSeq implements FlowServiceInf {

  public static final ReadLastHtmlSeq INSTANCE = new ReadLastHtmlSeq();

  /** 日志 */
  private Logger logger = LoggerFactory.getLogger(ReadLastHtmlSeq.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("collect start read last sequence id start..");

    // 读取上一次的最后一个id
    long lastId = DocIdproc.INSTANCE.getLastHrefId();

    // 设置序列号的开始值为上一次的最后一个id
    NumberLoopSeq seqInstance = SeqManager.INSTANCE.getOrCreateSeqNum(SeqNameEnum.SEQ_HTML_DOC_ID);
    seqInstance.start(lastId);

    logger.info("collect start read last sequence id : {} , finish", lastId);

    return true;
  }
}
