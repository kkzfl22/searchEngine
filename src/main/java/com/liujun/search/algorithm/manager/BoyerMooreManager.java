package com.liujun.search.algorithm.manager;

import com.liujun.search.algorithm.boyerMoore.use.CharMatcherBMBadChars;
import com.liujun.search.algorithm.manager.constant.BMHtmlTagContextEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 使用bm算法进行字符串匹配的算法管理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/19
 */
public class BoyerMooreManager {

  /** 实例对象 */
  public static final BoyerMooreManager INSTANCE = new BoyerMooreManager();

  /** 模式串,模式匹配的对象的对应关系 */
  private static final Map<String, CharMatcherBMBadChars> PATTERN_MAP = new HashMap<>();

  static {
    Set<String> parrernChars = InitParrern.INSTANCE.getInitParrertChars();

    // 初始化械串
    for (String patterns : parrernChars) {
      PATTERN_MAP.put(patterns, CharMatcherBMBadChars.getGoodSuffixInstance(patterns));
    }
  }

  /**
   * 添加好后缀模式串
   *
   * @param patternKey 模式串信息
   */
  public static void putPmGoodSuffix(String patternKey) {
    PATTERN_MAP.put(patternKey, CharMatcherBMBadChars.getGoodSuffixInstance(patternKey));
  }

  /**
   * 添加坏字符的规则
   *
   * @param patternKey 模式串信息
   */
  public static void putPmBadChar(String patternKey) {
    PATTERN_MAP.put(patternKey, CharMatcherBMBadChars.getGoodSuffixInstance(patternKey));
  }

  /**
   * 获取单模式串匹配的实例对象
   *
   * @param patternKey 公共模式串信息
   * @return 模式串实例对象
   */
  public CharMatcherBMBadChars getCharMatchar(String patternKey) {
    return PATTERN_MAP.get(patternKey);
  }

  /**
   * 通过bm算法进行模式串的匹配操作
   *
   * @param parrern 当前的模式串
   * @param htmlContext 当前网页容信息byte数组，仅限小字符集即byte以内
   * @param startPostion 开始位置
   * @return
   */
  public int getHrefIndex(String parrern, byte[] htmlContext, int startPostion) {
    // 通过模式串查找索引位置
    CharMatcherBMBadChars hrefAstart = getCharMatchar(parrern);
    int hrefIndex = hrefAstart.matcherIndex(htmlContext, startPostion);

    return hrefIndex;
  }

  /**
   * 通过bm算法进行模式串的匹配操作
   *
   * @param parrern 当前的模式串
   * @param htmlContext 当前网页容字符数据
   * @param startPostion 开始位置
   * @return
   */
  public int getHrefIndex(String parrern, char[] htmlContext, int startPostion) {
    // 通过模式串查找索引位置
    CharMatcherBMBadChars hrefAstart = getCharMatchar(parrern);
    int hrefIndex = hrefAstart.matcherIndex(htmlContext, startPostion);

    return hrefIndex;
  }
}
