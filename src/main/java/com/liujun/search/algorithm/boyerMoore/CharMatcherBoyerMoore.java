package com.liujun.search.algorithm.boyerMoore;

/**
 * 高效的字符串匹配算法bm算法
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/15
 */
public class CharMatcherBoyerMoore {

  /** 字符集大小 */
  public static final int BUFFER_SIZE = 256;

  public static CharMatcherBoyerMoore getBmInstance() {
    return new CharMatcherBoyerMoore();
  }

  /**
   * 将模式串进行预处理，使用hash来存储，计算每个字符最后出现的位置
   *
   * @param matchers
   * @return
   */
  private int[] generateBc(char[] matchers) {
    int[] bc = new int[BUFFER_SIZE];

    for (int i = 0; i < BUFFER_SIZE; i++) {
      bc[i] = -1;
    }

    for (int i = 0; i < matchers.length; i++) {
      // 计算模式串主符的asc码值，然后以asc码值为索引计算下标
      int ascIndex = matchers[i];
      if (ascIndex >= 0 && ascIndex < BUFFER_SIZE) {
        bc[ascIndex] = i;
      }
    }

    return bc;
  }

  /**
   * 使用bm算法进行字符串的匹配操作
   *
   * @param str 原始字符串
   * @param matcher 匹配的字符串
   * @param inputStart 起始索引位置
   * @return 查找到的位置
   */
  public int bm(char[] str, char[] matcher, int inputStart) {
    int matcherLength = matcher.length;
    // 记录下每个模式串出现最后出现的位置
    int[] bc = generateBc(matcher);
    // 获取suffix数据和能模式匹配的前缀子串
    int[] suffix = new int[matcherLength];
    boolean[] prefix = new boolean[matcherLength];

    // 进行公共数组suffix和prefix数组地求解
    this.generateGs(matcher, suffix, prefix);

    // 起始搜索位置
    int startIndex = inputStart;

    int srcLength = str.length;

    while (startIndex <= srcLength - matcherLength) {
      // 模式串的匹配规则，从后向前匹配
      int matchIndex;
      for (matchIndex = matcherLength - 1; matchIndex >= 0; matchIndex--) {
        // 检查当前是否不能匹配,如果不能匹配，坏字符就是当前matchIndex所对应的位置
        if (str[startIndex + matchIndex] != matcher[matchIndex]) {
          break;
        }
      }

      // 如果当前一直到模式串匹配完成，都能匹配，说明字符已经找到，则返回
      if (matchIndex < 0) {
        return startIndex;
      }

      // 如果出现坏字符，找到坏字符出现在模式串中的位置
      int badIndex = bc[str[startIndex + matchIndex]];
      // 计算械串可跳过的位数
      int jumpBits = matchIndex - badIndex;

      int moveGoodSuffix = 0;
      // 使用好后缀的规则来进行计算滑动的位数
      if (jumpBits < matcherLength - 1) {
        moveGoodSuffix = this.moveBygoodSuffix(matchIndex, matcherLength, suffix, prefix);
      }

      startIndex = startIndex + Math.max(jumpBits, moveGoodSuffix);
    }

    return -1;
  }

  /**
   * 计算得到好后缀规则移动的位数
   *
   * @param badIndex 坏字符的在模式串中的下标位置
   * @param matcherLength 模式串的长度
   * @param suffix 模式串中存在字符的索引位置
   * @param prefix 后缀能否匹配前缀字符
   * @return
   */
  private int moveBygoodSuffix(int badIndex, int matcherLength, int[] suffix, boolean[] prefix) {
    int goodSuffixLength = matcherLength - 1 - badIndex;
    // 如果当前字符在械串中存在，则计算跳过的位数
    if (suffix[goodSuffixLength] != -1) {
      return badIndex - suffix[goodSuffixLength] + 1;
    }

    // 好后缀的后缀子串是否存在跟模式串的前缀子串匹配的
    for (int i = badIndex + 2; i <= matcherLength - 1; i++) {
      if (prefix[matcherLength - i]) {
        return i;
      }
    }

    // 如果未发生匹配，则直接跳过指定的位数
    return matcherLength;
  }

  /**
   * 进行填充suffix数组与prefix数组，用来进行好后缀规则的填充操作
   *
   * @param matchers 模式串
   * @param suffix 用来记录跟后缀子串匹配的另一个前缀子串的索引位置
   * @param prefix 在后缀子串中能与当前索引所对应的后缀子串匹配的前缀子串，true表示可以匹配，false 不能匹配
   */
  public void generateGs(char[] matchers, int[] suffix, boolean[] prefix) {
    int macherLength = matchers.length;

    // 进行初始化
    for (int i = 0; i < macherLength; i++) {
      suffix[i] = -1;
      prefix[i] = false;
    }

    // 从向前向后遍历前缀子串
    for (int i = 0; i < macherLength - 1; i++) {
      int suffixIndex = i;

      // 公共后缀子串长度s
      int commSuffixLenght = 0;

      // 检查前缀子串与后缀子串是否匹配，如果匹配继续检查，不匹配，则结束
      while (suffixIndex > 0
          && matchers[suffixIndex] == matchers[macherLength - i - commSuffixLenght]) {
        suffixIndex--;
        commSuffixLenght++;
      }
      // suffixIndex + 1表示公共子串的起始下标
      if (commSuffixLenght != 0) {
        suffix[commSuffixLenght] = suffixIndex + 1;
      }
      // 如果前后相重合说明当前存在公共子串，则标训为公共子串
      if (suffixIndex == -1) {
        prefix[commSuffixLenght] = true;
      }
    }
  }
}
