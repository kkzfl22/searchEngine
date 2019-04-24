package com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword;

import com.liujun.search.algorithm.ahoCorasick.pojo.MatcherBusi;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.getSpitWord.SpitWorkProcess;
import org.junit.Test;

/**
 * 测试分词的流程
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/24
 */
public class TestSpitWordProcess {

  @Test
  public void testSpitWord1() {
    StringBuilder words = new StringBuilder();
    words.append("手机软件测试");
    words.append(",");
    words.append("新闻出版总署立法程序规定");

    // 进行分词的测试
    this.runSpitWord(words.toString());
  }

  /** 进行分词的测试 */
  @Test
  public void testSpitWord2() {
    StringBuilder words = new StringBuilder();
    words.append("天津市电力设施保护管理办法");
    words.append("*(*23");
    words.append("中文");

    // 进行分词的测试
    this.runSpitWord(words.toString());
  }

  /**
   * 进行分词操作
   *
   * @param words 文本信息
   */
  private void runSpitWord(String words) {
    char[] charContext = words.toCharArray();
    int pos = 0;

    int wordLength = 0;

    while (true) {
      MatcherBusi matchBusi = SpitWorkProcess.INSTANCE.spitWord(charContext, pos);

      if (matchBusi.getMatcherIndex() != -1) {
        if (matchBusi.getMatcherKey() != null) {
          System.out.println("当前分配的单词:" + matchBusi.getMatcherKey());
          wordLength = matchBusi.getMatcherKey().length();
        } else {
          wordLength = 0;
        }
        pos = matchBusi.getMatcherIndex() + wordLength;
      } else {
        break;
      }
    }
  }
}
