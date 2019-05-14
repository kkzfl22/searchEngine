package com.liujun.search.engine.query.process;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * 进行分词的测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/13
 */
public class TestSpitWordProcess {

  @Test
  public void testSpitWord() {
    String data = "上海火车站 地下商场";

    Map<String, Integer> wordMap = SpitWordProcess.INSTANCE.spitWord(data);
    System.out.println(wordMap);
    Assert.assertNotNull(wordMap);
    Assert.assertNotEquals(0, wordMap);
  }
}
