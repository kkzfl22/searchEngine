package com.liujun.search.algorithm.ahoCorasick;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * trie树，只用于数字的匹配操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/08
 */
public class AhoCorasick {

  /** 特殊处理的字符 */
  private static final Map<Character, Integer> SPECIAL_MAP = new HashMap<>();

  static {
    SPECIAL_MAP.put('\t', 11);
  }

  /** 根节点 */
  private AcNode root = new AcNode('/');

  /**
   * 在tree树中插入字符
   *
   * @param src
   */
  public void insert(String src) {

    AcNode tmpRoot = root;

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
        tmpRoot.childred[index] = new AcNode(srcArrays[i]);
      }
      // 当前节点垮了指向子节点
      tmpRoot = tmpRoot.childred[index];
    }
    // 最后一个节点，需要标识为结束节点
    tmpRoot.isEndingChar = true;
  }

  public void addPointList(List<String> list) {}

  /**
   * 进行字符串的匹配操作
   *
   * @param src 字符信息
   * @return 模式串出现的位置信息
   */
  public List<Integer> matchs(String src) {
    return null;
  }

  /** trie 树的节点信息 */
  public class AcNode {

    /** 存储的数据 */
    public char data;

    /** 存储所有数字，以及一个符号，符号被转义 */
    public AcNode[] childred = new AcNode[11];

    /** 是否为结束字符 */
    public boolean isEndingChar = false;

    /** 当isEndingChar为true时记录下模式串的长度 */
    public int length = -1;

    /** 失败指针 */
    public AcNode fail;

    public AcNode(char data) {
      this.data = data;
    }
  }
}
