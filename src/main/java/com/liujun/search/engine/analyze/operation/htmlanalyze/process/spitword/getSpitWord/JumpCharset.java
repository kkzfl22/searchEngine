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
public class JumpCharset implements FlowServiceInf {

  public static final JumpCharset INSTANCE = new JumpCharset();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    char[] contextArrays =
        context.getObject(SpitWordFlowEnum.SPITWORD_INPUT_CONTEXT_ARRAYS.getKey());
    int position = context.getObject(SpitWordFlowEnum.SPITWORD_INPUT_POSITION.getKey());

    if (position < contextArrays.length) {
      MatcherBusi matchBusi = new MatcherBusi();
      matchBusi.setMatcherIndex(position + 1);
      matchBusi.setMatcherKey(null);
      context.put(SpitWordFlowEnum.SPITWORD_OUTPUT_WORDBUSI.getKey(), matchBusi);
      return false;
    }
    // 当已经匹配结束，则直接返回
    else {
      MatcherBusi matchBusi = new MatcherBusi();
      matchBusi.setMatcherIndex(-1);
      matchBusi.setMatcherKey(null);
      context.put(SpitWordFlowEnum.SPITWORD_OUTPUT_WORDBUSI.getKey(), matchBusi);
      return true;
    }
  }
}
