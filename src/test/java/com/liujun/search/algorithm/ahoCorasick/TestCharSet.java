package com.liujun.search.algorithm.ahoCorasick;

import org.junit.Test;

/**
 * 进行字符的测试，以便直接进行大小写的检查
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/16
 */
public class TestCharSet {

  @Test
  public void outCharUpperCase() {
    for (int i = 65; i <= 90; i++) {
      char outChar = (char) i;
      System.out.println("输出字符:" + outChar);
    }
  }

  @Test
  public void outCharUpperCaseToLower() {
    for (int i = 65; i <= 90; i++) {

      char outBefore = (char) i;
      int outIndex = i + (97 - 65);
      char outChar = (char) outIndex;

      System.out.println("之前字符:" + outBefore + "<-->" + outChar);
    }
  }

  @Test
  public void outCharLowerCase() {
    for (int i = 97; i <= 122; i++) {
      char outChar = (char) i;
      System.out.println("输出字符:" + outChar);
    }
  }
}
