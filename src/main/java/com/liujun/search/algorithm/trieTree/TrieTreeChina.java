package com.liujun.search.algorithm.trieTree;

import com.liujun.search.algorithm.ahoCorasick.pojo.MatcherBusi;
import org.apache.commons.lang3.StringUtils;

/**
 * trie树，构建可以匹配所有字符的
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/08
 */
public class TrieTreeChina {

  /** 英文字符的结束范围 */
  private static final int ENGLISH_END = 256;

  /** 字符开始 */
  private static final char CHINA_CHAR_START = '\u4e00';

  /** 字符结束 */
  private static final char CHINA_CHAR_END = '\u9fa5';

  /** 最大字符集的范围 */
  private static final int MAX_CHARSET = CHINA_CHAR_END - CHINA_CHAR_START + ENGLISH_END;

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

    int index;

    for (int i = 0; i < srcArrays.length; i++) {
      // 如果存在特殊字符串中，优先使用特殊字符
      index = this.getCharIndex(srcArrays[i]);

      if (index == -1) {
        continue;
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
    tmpRoot.nocdeValue = src;
  }

  /**
   * 获取字符的索引号
   *
   * @param charVals 字符信息
   * @return 返回的索引号
   */
  private int getCharIndex(char charVals) {
    int charSetIndex = charVals;

    // 检查是否在中文范围
    if (charSetIndex >= CHINA_CHAR_START && charSetIndex <= CHINA_CHAR_END) {
      return charSetIndex - CHINA_CHAR_START;
    }

    if (charSetIndex >= 0 && charSetIndex <= ENGLISH_END) {
      return charSetIndex;
    }

    return -1;
  }

  /**
   * 进行字符串的匹配操作
   *
   * @param find
   */
  public MatcherBusi match(String find, int startPos) {
    MatcherBusi result = new MatcherBusi();

    if (StringUtils.isEmpty(find)) {
      result.setMatcherIndex(-1);
      return result;
    }
    char[] findChars = find.toCharArray();
    TrieNode tempRoot = root;

    // 进行字符串的匹配查找
    int index = 0;
    int i = 0;

    for (i = startPos; i < findChars.length; i++) {
      index = this.getCharIndex(findChars[i]);

      // 检查是否可以匹配
      if (tempRoot.childred[index] == null) {
        break;
      }

      tempRoot = tempRoot.childred[index];
    }

    // 如果当前非结束字符，说明部分匹配
    if (!tempRoot.isEndingChar) {
      result.setMatcherIndex(-1);
      return result;
    } else {
      result.setMatcherKey(tempRoot.nocdeValue);
      result.setMatcherIndex(i - result.getMatcherKey().length());
      return result;
    }
  }

  /** trie 树的节点信息 */
  public class TrieNode {

    /** 存储的数据 */
    public char data;

    /** 存储所有数字，以及一个符号，符号被转义 */
    public TrieNode[] childred = new TrieNode[MAX_CHARSET];

    /** 是否为结束字符 */
    public boolean isEndingChar = false;

    /** 在标识为结束字符时，将存储全部字符 */
    public String nocdeValue;

    public TrieNode(char data) {
      this.data = data;
    }
  }
}
