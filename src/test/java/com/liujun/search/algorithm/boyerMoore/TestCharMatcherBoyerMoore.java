package com.liujun.search.algorithm.boyerMoore;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * 使用bm算法的字符串匹配测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/15
 */
// Junit测试顺序：@FixMethodOrder
// ** MethodSorters.DEFAULT **（默认）
// 默认顺序由方法名hashcode值来决定，如果hash值大小一致，则按名字的字典顺序确定。
// ** MethodSorters.NAME_ASCENDING （推荐） **
//   按方法名称的进行排序，由于是按字符的字典顺序，所以以这种方式指定执行顺序会始终保持一致；
// ** MethodSorters.JVM **
//    按JVM返回的方法名的顺序执行，此种方式下测试方法的执行顺序是不可预测的，即每次运行的顺序可能
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCharMatcherBoyerMoore {

  /** 字符串的查找操作，验证坏字符的规则 */
  @Test
  public void test01BadCharMatcher() {
    CharMatcherBoyerMoore instance = CharMatcherBoyerMoore.getBmInstance();

    String src = "abcacabdc";
    String matchers = "abd";
    int index = 0;

    int findIndex = instance.bm(src.toCharArray(), matchers.toCharArray(), index);
    Assert.assertEquals(5, findIndex);
  }

  /** 字符串的查找操作，验证好后缀的规则 */
  @Test
  public void test02GoodSuffixMatcher() {
    CharMatcherBoyerMoore instance = CharMatcherBoyerMoore.getBmInstance();
    String src = "abcacabcbacabc";
    String matchers = "cbacabc";
    int index = 0;

    int findIndex = instance.bm(src.toCharArray(), matchers.toCharArray(), index);
    Assert.assertEquals(7, findIndex);
  }
}
