package com.liujun.search.algorithm.trieTree;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * 测试trie树
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/08
 */
// Junit测试顺序：@FixMethodOrder
// ** MethodSorters.DEFAULT **（默认）
// 默认顺序由方法名hashcode值来决定，如果hash值大小一致，则按名字的字典顺序确定。
// ** MethodSorters.NAME_ASCENDING （推荐） **
//   按方法名称的进行排序，由于是按字符的字典顺序，所以以这种方式指定执行顺序会始终保持一致；
// ** MethodSorters.JVM **
//    按JVM返回的方法名的顺序执行，此种方式下测试方法的执行顺序是不可预测的，即每次运行的顺序可能
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestTrieTree {

  /** 测试字符串的匹配操作 */
  @Test
  public void test01Match() {

    TrieTreeInt instance = new TrieTreeInt();

    // 进行字符串的插入操作
    instance.insert("10111111010101");
    instance.insert("101010101010");
    instance.insert("101010");
    instance.insert("1010101");
    instance.insert("10101");
    instance.insert("1011011");
    // 进行字符串的匹配操作
    // 1,测试1，部分匹配
    int offsetIndex = instance.match("1011");
    Assert.assertEquals(0, offsetIndex);

    // 2,完全不匹配的情况
    int notMatchIndex = instance.match("2012");
    Assert.assertEquals(-1, notMatchIndex);

    // 3，完全匹配的情况
    int matchFull = instance.match("101010");
    Assert.assertEquals(1, matchFull);
  }
}
