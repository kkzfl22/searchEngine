package com.liujun.search.algorithm.ahoCorasick;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 测试ac自动机进行多模式串的匹配操作
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
public class TestAhoCorasick {

  /** 进行失败指针的构建 */
  @Test
  public void test01add() {
    AhoCorasick ahoCorasick = new AhoCorasick();
    ahoCorasick.insert("1234");
    ahoCorasick.insert("234");
    ahoCorasick.insert("23");
    ahoCorasick.insert("3");
    ahoCorasick.builderFailurePointer();
  }

  /** 进行多模式串的匹配操作 */
  @Test
  public void test02putAndMatch() {
    AhoCorasick ahoCorasick = new AhoCorasick();

    List<String> ahoCorasickList = new ArrayList<>();
    ahoCorasickList.add("112113");
    ahoCorasickList.add("113114");
    ahoCorasickList.add("115116");
    ahoCorasickList.add("117118");

    ahoCorasick.buildFailure(ahoCorasickList);

    Map<String, Integer> matList = ahoCorasick.matchMult("11311401151160117118");
    System.out.println(matList);
    Assert.assertNotNull(matList);
  }

  /** 进行多模式串匹配一个就返回 */
  @Test
  public void test03putAndMatchOne() {
    AhoCorasick ahoCorasick = new AhoCorasick();

    List<String> ahoCorasickList = new ArrayList<>();
    ahoCorasickList.add("112113");
    ahoCorasickList.add("113114");

    ahoCorasickList.add("117118");

    ahoCorasick.buildFailure(ahoCorasickList);

    String matValue = ahoCorasick.matchOne("0011211300117118000");
    System.out.println(matValue);
    Assert.assertNotNull(matValue);
  }
}
