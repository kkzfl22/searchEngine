package com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword;

import com.liujun.search.algorithm.ahoCorasick.pojo.MatcherBusi;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.getSpitWord.SpitWorkProcess;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/24
 */
public class SpitWordFlow {

  public void spitWord(String data) {
    if (StringUtils.isEmpty(data)) {
      return;
    }

    char[] charContext = data.toCharArray();

    int pos = 0;
    int wordLength = 0;

    int index = 0;

    while (index < charContext.length) {
      MatcherBusi matchBusi = SpitWorkProcess.INSTANCE.spitWord(charContext, pos);

      if (matchBusi.getMatcherIndex() != -1) {
        if (matchBusi.getMatcherKey() != null) {
          // 当匹配到字符后，则进行当前分词的流程处理
          wordLength = matchBusi.getMatcherKey().length();
        } else {
          wordLength = 0;
        }
        pos = matchBusi.getMatcherIndex() + wordLength;
      } else {
        break;
      }
      index++;
    }
  }
}
