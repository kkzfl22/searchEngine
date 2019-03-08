package com.liujun.search.algorithm.trieTree;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * trie树，只用于数字的匹配操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/08
 */
public class TrieTreeInt {

  /** 特殊处理的字符 */
  private static final Map<Character, Integer> SPECIAL_MAP = new HashMap<>();

  static {
    SPECIAL_MAP.put('\t', 11);
  }

  /** 根节点 */
  private TrieNode root = new TrieNode('/');

  /**
   * 在tree树中插入字符
   *
   * @param src
   */
  public void insert(String src) {

    TrieNode tmpRoot = root;

    char[] srcArrays = src.toCharArray();

    int index = 0;
    Integer getSpecialIndex = null;
    for (int i = 0; i < srcArrays.length; i++) {
      // 如果存在特殊字符串中，优先使用特殊字符
      getSpecialIndex = SPECIAL_MAP.get(srcArrays[i]);
      if (getSpecialIndex != null) {
        index = getSpecialIndex;
      }
      // 如果未找到，则直接转化原始位置索引
      else {
        index = Character.getNumericValue(srcArrays[i]);
      }

      // 如果当前子未存储数据,则直接存储
      if (tmpRoot.childred[index] == null) {
        tmpRoot.childred[index] = new TrieNode(srcArrays[i]);
      }
      // 当前节点垮了指向子节点
      tmpRoot = tmpRoot.childred[index];
    }
    // 最后一个节点，需要标识为结束节点
    tmpRoot.isEndingChar = true;
  }

  /**
   * 进行字符串的匹配操作
   *
   * @param find
   */
  public int match(String find) {
    if (StringUtils.isEmpty(find)) {
      return -1;
    }
    char[] findChars = find.toCharArray();
    TrieNode tempRoot = root;

    // 进行字符串的匹配查找
    int index = 0;
    Integer getSpecialIndex = null;
    for (int i = 0; i < findChars.length; i++) {
      getSpecialIndex = SPECIAL_MAP.get(findChars[i]);
      if (getSpecialIndex != null) {
        index = getSpecialIndex;
      } else {
        index = Character.getNumericValue(findChars[i]);
      }

      // 检查是否可以匹配
      if (tempRoot.childred[index] == null) {
        return -1;
      }

      tempRoot = tempRoot.childred[index];
    }

    // 如果当前非结束字符，说明部署匹配
    if (!tempRoot.isEndingChar) {
      return 0;
    } else {
      return 1;
    }
  }

  /** trie 树的节点信息 */
  public class TrieNode {

    /** 存储的数据 */
    public char data;

    /** 存储所有数字，以及一个符号，符号被转义 */
    public TrieNode[] childred = new TrieNode[11];

    /** 是否为结束字符 */
    public boolean isEndingChar = false;

    public TrieNode(char data) {
      this.data = data;
    }
  }
}
