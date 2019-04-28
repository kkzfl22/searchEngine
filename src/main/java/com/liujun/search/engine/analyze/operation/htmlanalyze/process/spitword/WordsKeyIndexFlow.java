package com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.WordFLowEnum;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.loader.KeyWordMap;

/**
 * 获取当前的分词的编号
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/24
 */
public class WordsKeyIndexFlow implements FlowServiceInf {

  public static final WordsKeyIndexFlow INSTANCE = new WordsKeyIndexFlow();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    String keys = context.getObject(WordFLowEnum.WORD_INPUT_WORD.getKey());

    //通过分词获取索引号
    int index = KeyWordMap.INSTANCE.getKeyIndex(keys);

    context.put(WordFLowEnum.WORD_PROC_INDEX.getKey(), index);

    return true;
  }
}
