package com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.loader;

import com.liujun.search.algorithm.ahoCorasick.pojo.MatcherBusi;
import com.liujun.search.algorithm.trieTree.TrieTreeChina;
import org.junit.Test;

/**
 * 进行分词的测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/22
 */
public class TestTrideTreeWord {

  @Test
  public void testTrideTree() {
    long startTime = System.currentTimeMillis();
    TrieTreeChina chinaChar = TrideTreeWord.INSTANCE.getSpitWord();
    long endTime = System.currentTimeMillis();

    System.out.println("用时:" + (endTime - startTime));

    String value = "咬定青山不放松白日依山尽黄河入海流更上一层楼";
    char[] charWords = value.toCharArray();

    int start = 0;
    while (true) {
      MatcherBusi busi = chinaChar.match(charWords, start);

      if (busi.getMatcherIndex() != -1) {
        System.out.println("匹配:" + busi.getMatcherKey());
        start = busi.getMatcherIndex() + busi.getMatcherKey().length();
      } else {
        break;
      }
    }
  }
}
