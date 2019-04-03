package com.liujun.search.algorithm.ahoCorasick;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ac自动机算法，用于字符的多模式串匹配操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/08
 */
public class AhoCorasickChar extends AhoCorasick {

  /** ac自动机的大小 */
  private static final int AC_SIZE = 256;

  @Override
  protected int getAcSize() {
    return AC_SIZE;
  }

  /**
   * 获取ac自动机的实例对象，通过传入的模式串数组
   *
   * @param matchStr 模式串信息
   * @return ac自动机的实例对象
   */
  public static AhoCorasickChar GetAhoCorasickInstance(List<String> matchStr) {
    AhoCorasickChar ahoCorasick = new AhoCorasickChar();
    ahoCorasick.buildFailure(matchStr);

    return ahoCorasick;
  }

  /**
   * 获取索引节点
   *
   * @param srcArray
   * @return
   */
  @Override
  public int getIndex(char srcArray) {
    int index = (int) srcArray;
    return index;
  }
}
