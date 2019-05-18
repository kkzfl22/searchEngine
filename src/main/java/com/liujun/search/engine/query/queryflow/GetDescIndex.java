package com.liujun.search.engine.query.queryflow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.query.constant.QueryFlowEnum;
import com.liujun.search.engine.query.pojo.WordOffsetBusi;
import com.liujun.search.engine.query.process.DescIndexReaderProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提取倒提成索引的数据信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/16
 */
public class GetDescIndex implements FlowServiceInf {

  public static final GetDescIndex INSTANCE = new GetDescIndex();

  private Logger logger = LoggerFactory.getLogger(GetDescIndex.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    Map<Integer, List<WordOffsetBusi>> groupOffsetList =
        context.getObject(QueryFlowEnum.PROC_GROUPOFFSET.getKey());

    Map<Integer, List<Long>> wordList = new HashMap<>();

    // 获得所有的网页编号
    for (Map.Entry<Integer, List<WordOffsetBusi>> offsetItem : groupOffsetList.entrySet()) {
      wordList.putAll(
          DescIndexReaderProcess.INSTANCE.getWordList(offsetItem.getKey(), offsetItem.getValue()));
    }

    // 通过网页分词获得了所有的网页id
    context.put(QueryFlowEnum.PROC_WORDOFFSET.getKey(), wordList);

    logger.info("query desc index size {}",wordList.size());

    return true;
  }
}
