package com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.loader;

import com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.loader.FilterWordLoader;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;

/**
 * 测试分词程序
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/20
 */
public class TestFilterWordLoader {

  @Test
  public void spitLoad() {
    HashSet<String> set = FilterWordLoader.INSTANCE.getFilterSet();
    System.out.println(set.size());
    Assert.assertNotEquals(0, set.size());
  }
}
