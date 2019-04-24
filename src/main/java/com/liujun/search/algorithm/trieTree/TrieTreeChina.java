package com.liujun.search.algorithm.trieTree;

import com.liujun.search.algorithm.ahoCorasick.pojo.MatcherBusi;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * trie树，构建可以匹配所有字符的
 *
 * <p>//1,方案1，使用全中文字符集，然后直接数据装载，但由于字符集太大，消耗内存惊人，需要更换自满以解决内存问题
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/08
 */
public class TrieTreeChina {

  /** 根节点 */
  private TrieNode root = new TrieNode('/');

  /**
   * 在tree树中插入字符
   *
   * @param src
   */
  public void insert(String src) {

    if (StringUtils.isEmpty(src)) {
      return;
    }

    if(src.indexOf("深入人心") != -1)
    {
      System.out.println("//");
    }

    TrieNode tmpRoot = root;

    char[] srcArrays = src.toCharArray();

    for (int i = 0; i < srcArrays.length; i++) {
      // 如果当前子未存储数据,则直接存储
      if (tmpRoot.getChildren(srcArrays[i]) == null) {
        tmpRoot.addChildren(srcArrays[i]);
      }
      // 当前节点垮了指向子节点
      tmpRoot = tmpRoot.getChildren(srcArrays[i]);
    }
    // 最后一个节点，需要标识为结束节点
    tmpRoot.isEndingChar = true;
    tmpRoot.nocdeValue = src;
  }

  /**
   * 进行字符串的匹配操作
   *
   * @param findChars 字符信息
   */
  public MatcherBusi match(char[] findChars, int startPos) {
    MatcherBusi result = new MatcherBusi();

    TrieNode tempRoot = root;

    // 进行字符串的匹配查找
    int i;
    for (i = startPos; i < findChars.length; i++) {
      if (null != tempRoot) {
        // 检查是否可以匹配
        if (tempRoot.getChildren(findChars[i]) == null) {
          break;
        }

        tempRoot = tempRoot.getChildren(findChars[i]);
      }
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

    /** 对于存储的结构进行改进，以解决内存浪费的问题 */
    private Map<Character, TrieNode> childredMap = new HashMap<>();

    /** 是否为结束字符 */
    public boolean isEndingChar = false;

    /** 在标识为结束字符时，将存储全部字符 */
    public String nocdeValue;

    public TrieNode(char data) {
      this.data = data;
    }

    /**
     * 获取子节点信息
     *
     * @param childChar 子节点的字符串标识
     * @return
     */
    public TrieNode getChildren(char childChar) {
      return childredMap.get(childChar);
    }

    public void addChildren(char childChar) {
      TrieNode tmpNode = childredMap.get(childChar);

      if (tmpNode == null) {
        tmpNode = new TrieNode(childChar);
        childredMap.put(childChar, tmpNode);
      }
    }
  }
}
