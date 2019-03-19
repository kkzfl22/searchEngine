package com.liujun.search.algorithm.manager;

import com.liujun.search.algorithm.boyerMoore.use.CharMatcherBMBadChars;
import com.liujun.search.algorithm.manager.constant.CommonPatternEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用bm算法进行字符串匹配的算法管理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/19
 */
public class BoyerMooreManager {

  /** 模式串 */
  private static final Map<CommonPatternEnum, CharMatcherBMBadChars> PATTERN_MAP = new HashMap<>();

  static {
    CommonPatternEnum[] values = CommonPatternEnum.values();

    /** 构建坏字符与好后缀的规则，进行单模式串的匹配操作 */
    for (CommonPatternEnum parrern : values) {
      PATTERN_MAP.put(parrern, CharMatcherBMBadChars.getGoodSuffixInstance(parrern.getPattern()));
    }
  }

  /**
   * 获取单模式串匹配的实例对象
   *
   * @param pattern 公共模式串信息
   * @return 模式串实例对象
   */
  public CharMatcherBMBadChars getCharMatchar(CommonPatternEnum pattern) {
    return PATTERN_MAP.get(pattern);
  }
}
