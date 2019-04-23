package com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.loader;

import com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.loader.WordLoader;
import org.junit.Test;

/**
 * 测试分词程序
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/20
 */
public class TestSpitWordLoader {

  /** 分词测试 */
  @Test
  public void testSpit() {
    System.out.println(Character.MAX_VALUE);
    System.out.println((int) Character.MAX_VALUE);

    char startChar = '\u4e00';
    char endChar = '\u9fa5';

    int start = (int) startChar;
    int end = (int) endChar;
    System.out.println("开始:" + start);
    System.out.println("结束:" + end);
    System.out.println("单词范围:" + (end - start));

    for (int i = 0; i <= Character.MAX_VALUE; i++) {
      char out = (char) i;

      System.out.print(out + "\t");

      if (i % 100 == 0) {
        System.out.println();
      }
    }
  }

  @Test
  public void spitLoad() {
    WordLoader.INSTANCE.toString();
  }
}
