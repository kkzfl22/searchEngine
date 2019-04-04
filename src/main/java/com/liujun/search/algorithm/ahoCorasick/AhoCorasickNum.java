package com.liujun.search.algorithm.ahoCorasick;

import java.util.*;

/**
 * ac自动机算法，只用于数字的匹配操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/08
 */
public class AhoCorasickNum extends AhoCorasick {

  /** 特殊处理的字符 */
  private static final Map<Character, Integer> SPECIAL_MAP = new HashMap<>();

  /** ac自动机的大小 */
  private static final int AC_SIZE = 12;

  static {
    SPECIAL_MAP.put('\t', 11);
  }

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
  public static AhoCorasickNum GetAhoCorasickInstance(List<String> matchStr) {
    AhoCorasickNum ahoCorasick = new AhoCorasickNum();
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
    int index = -1;
    Integer getSpecialIndex = SPECIAL_MAP.get(srcArray);
    if (getSpecialIndex != null) {
      index = getSpecialIndex;
    }
    // 如果未找到，则直接转化原始位置索引
    else {
      index = Character.getNumericValue(srcArray);
    }

    return index;
  }
}
