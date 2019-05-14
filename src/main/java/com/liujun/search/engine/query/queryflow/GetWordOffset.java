package com.liujun.search.engine.query.queryflow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.query.cache.WordOffsetCache;
import com.liujun.search.engine.query.constant.QueryFlowEnum;
import com.liujun.search.engine.query.pojo.WordOffsetBusi;

import java.util.HashMap;
import java.util.Map;

/**
 * 获得单词所对应的网页的偏移信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/13
 */
public class GetWordOffset implements FlowServiceInf {

  public static final GetWordOffset INSTANCE = new GetWordOffset();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    Map<String, Integer> wordList = context.getObject(QueryFlowEnum.PROC_SPITWORD.getKey());

    Map<Integer, WordOffsetBusi> wordMap = new HashMap<>();

    for (int wordId : wordList.values()) {
      wordMap.put(wordId, WordOffsetCache.INSTANCE.getWordOffset(wordId));
    }

    context.put(QueryFlowEnum.PROC_WORDOFFSET.getKey(), wordMap);

    return true;
  }
}
