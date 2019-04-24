package com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.loader;

import com.liujun.search.algorithm.ahoCorasick.pojo.MatcherBusi;
import com.liujun.search.algorithm.trieTree.TrieTreeChina;
import org.junit.Assert;
import org.junit.Test;

/**
 * 进行分词的测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/22
 */
public class TestTrideFilterWord {

  @Test
  public void testTrideTreeFilter() {
    long startTime = System.currentTimeMillis();
    TrieTreeChina filterMatcher = TrieFilterWord.INSTANCE.getFilterWord();
    long endTime = System.currentTimeMillis();

    System.out.println("用时:" + (endTime - startTime));

    StringBuilder testFile = new StringBuilder();
    testFile.append("...................");
    testFile.append("7");
    testFile.append("//");
    testFile.append("φ．");
    testFile.append("Ψ");
    testFile.append("⑥");
    testFile.append("∪φ∈");
    testFile.append("长话短说");
    testFile.append("＠");
    testFile.append("ＬＩ");
    testFile.append("［①①］");
    testFile.append("［③ｄ］");
    testFile.append("～＋");
    testFile.append("￥");
    testFile.append("ｎｇ昉");


    char[] charWords = testFile.toString().toCharArray();

    int start = 0;
    while (true) {
      MatcherBusi busi = filterMatcher.match(charWords, start);

      if (busi.getMatcherIndex() != -1) {
        Assert.assertNotNull(busi.getMatcherKey());
        System.out.println("匹配:" + busi.getMatcherKey());
        start = busi.getMatcherIndex() + busi.getMatcherKey().length();
      } else {
        break;
      }
    }
  }
}
