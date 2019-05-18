package com.liujun.search.engine.query.queryflow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.query.constant.QueryFlowEnum;
import com.liujun.search.engine.query.process.SpitWordProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 进行分词操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/13
 */
public class SpitWord implements FlowServiceInf {

  public static final SpitWord INSTANCE = new SpitWord();

  private Logger logger = LoggerFactory.getLogger(SpitWord.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    String inputContext = context.getObject(QueryFlowEnum.INPUT_CONTEXT.getKey());

    Map<String, Integer> wordList = SpitWordProcess.INSTANCE.spitWord(inputContext);

    context.put(QueryFlowEnum.PROC_SPITWORD.getKey(), wordList);

    logger.info("query spit word {}", wordList);

    return true;
  }
}
