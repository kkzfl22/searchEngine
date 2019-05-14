package com.liujun.search.engine.query.process;

import com.liujun.search.algorithm.ahoCorasick.pojo.MatcherBusi;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.getSpitWord.SpitWorkProcess;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.loader.KeyWordMap;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 进行分词处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/13
 */
public class SpitWordProcess {

  public static final SpitWordProcess INSTANCE = new SpitWordProcess();

  /**
   * 进行分词的处理
   *
   * @param data 数据停下
   */
  public Map<String, Integer> spitWord(String data) {
    if (StringUtils.isEmpty(data)) {
      return null;
    }

    char[] charContext = data.toCharArray();

    int pos = 0;
    int wordLength = 0;

    Map<String, Integer> wordMap = new HashMap<>();
    int index = 0;

    while (index < charContext.length) {
      MatcherBusi matchBusi = SpitWorkProcess.INSTANCE.spitWord(charContext, pos);

      if (matchBusi.getMatcherIndex() != -1) {
        if (matchBusi.getMatcherKey() != null) {
          // 当分词后，将分词的数据写入到map中
          queryWord(matchBusi.getMatcherKey(), wordMap);

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

    return wordMap;
  }

  /**
   * 分词数据的查询操作
   *
   * @param key 关键字
   * @param wordMap 分词的map
   */
  private void queryWord(String key, Map<String, Integer> wordMap) {

    // 通过分词获取索引号
    int index = KeyWordMap.INSTANCE.getKeyIndex(key);

    // 将分词的索引号加入到map中
    wordMap.put(key, index);
  }
}
