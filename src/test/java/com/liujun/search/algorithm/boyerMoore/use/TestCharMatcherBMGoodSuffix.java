package com.liujun.search.algorithm.boyerMoore.use;

import org.junit.Assert;
import org.junit.Test;

/**
 * 在坏字符规则的基础上，加入好后缀规则，进行字符串的匹配操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/17
 */
public class TestCharMatcherBMGoodSuffix {

  /** 字符串的查找操作，加入好后缀规则验证坏字符的规则 */
  @Test
  public void test01GoogSuffixCharMatcher() {
    String matchers = "abd";
    CharMatcherBMBadChars instance = CharMatcherBMGoodSuffix.getInstance(matchers);

    String src = "abcacabdc";

    int startIndex = 0;

    int findIndex = instance.matcherIndex(src.toCharArray(), startIndex);
    Assert.assertEquals(5, findIndex);
  }

  /** 字符串的查找操作,加入好后缀规则 */
  @Test
  public void test02GoodSuffixCharMatcher() {

    String src = "abcacabcbacabc";
    String matchers = "cbacabc";
    int index = 0;

    CharMatcherBMBadChars instance = CharMatcherBMGoodSuffix.getInstance(matchers);

    int findIndex = instance.matcherIndex(src.toCharArray(), index);
    Assert.assertEquals(7, findIndex);
  }
}
