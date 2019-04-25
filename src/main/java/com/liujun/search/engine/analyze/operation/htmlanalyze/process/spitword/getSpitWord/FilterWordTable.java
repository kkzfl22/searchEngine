package com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.getSpitWord;

import com.liujun.search.algorithm.ahoCorasick.pojo.MatcherBusi;
import com.liujun.search.algorithm.trieTree.TrieTreeChina;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.SpitWordFlowEnum;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.loader.TrieFilterWord;

/**
 * 进行单词的过滤操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/24
 */
public class FilterWordTable implements FlowServiceInf {

  public static final FilterWordTable INSTANCE = new FilterWordTable();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    TrieTreeChina trieFilter = TrieFilterWord.INSTANCE.getFilterWord();

    char[] contextArrays =
        context.getObject(SpitWordFlowEnum.SPITWORD_INPUT_CONTEXT_ARRAYS.getKey());
    int position = context.getObject(SpitWordFlowEnum.SPITWORD_INPUT_POSITION.getKey());

    MatcherBusi matchBusi = trieFilter.match(contextArrays, position);

    if (matchBusi.getMatcherIndex() != -1) {
      matchBusi.setMatcherIndex(matchBusi.getMatcherIndex() + matchBusi.getMatcherKey().length());
      matchBusi.setMatcherKey(null);
      context.put(SpitWordFlowEnum.SPITWORD_OUTPUT_WORDBUSI.getKey(), matchBusi);

      return false;
    }

    return true;
  }
}