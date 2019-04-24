package com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.getSpitWord;

import com.liujun.search.algorithm.ahoCorasick.pojo.MatcherBusi;
import com.liujun.search.algorithm.trieTree.TrieTreeChina;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.SpitWordFlowEnum;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.loader.TrieTreeWord;

/**
 * 进行分词表进行分词操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/24
 */
public class SpitWordTable implements FlowServiceInf {

  public static final SpitWordTable INSTANCE = new SpitWordTable();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    TrieTreeChina trieWord = TrieTreeWord.INSTANCE.getSpitWord();

    char[] contextArrays =
        context.getObject(SpitWordFlowEnum.SPITWORD_INPUT_CONTEXT_ARRAYS.getKey());
    int position = context.getObject(SpitWordFlowEnum.SPITWORD_INPUT_POSITION.getKey());

    MatcherBusi matchBusi = trieWord.match(contextArrays, position);

    // 当单词搜索到了以后，则退出当前的搜索操作
    if (matchBusi != null && matchBusi.getMatcherKey() != null) {
      context.put(SpitWordFlowEnum.SPITWORD_OUTPUT_WORDBUSI.getKey(), matchBusi);

      return false;
    }

    // 当前分词未成功，则继续分词操作
    return true;
  }
}
