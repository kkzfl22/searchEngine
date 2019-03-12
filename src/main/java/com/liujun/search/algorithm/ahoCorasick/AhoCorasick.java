package com.liujun.search.algorithm.ahoCorasick;

import java.util.*;

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

  /** ac自动机的大小 */
  private static final int AC_SIZE = 12;

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

    for (int i = 0; i < srcArrays.length; i++) {
      // 如果存在特殊字符串中，优先使用特殊字符
      index = getIndex(srcArrays[i]);

      // 如果当前子未存储数据,则直接存储
      if (tmpRoot.childred[index] == null) {
        tmpRoot.childred[index] = new AcNode(srcArrays[i]);
      }
      // 当前节点垮了指向子节点
      tmpRoot = tmpRoot.childred[index];
    }
    // 最后一个节点，需要标识为结束节点
    tmpRoot.isEndingChar = true;
    tmpRoot.length = srcArrays.length;
  }

  /** 构建失败指针 */
  public void builderFailurePointer() {
    Queue<AcNode> queue = new LinkedList<>();

    root.fail = null;
    // 1,首先放入root节点
    queue.add(root);

    while (!queue.isEmpty()) {
      AcNode qItemTmp = queue.remove();

      // 进行遍历当前节点下的子节点，进行失败指针的指向
      for (int i = 0; i < AC_SIZE; i++) {
        AcNode qchildTmp = qItemTmp.childred[i];
        // 如果当前节点未存储，则跳过
        if (qchildTmp == null) {
          continue;
        }

        // 存储了数据，则检查是否为root节点
        if (qItemTmp == root) {
          qchildTmp.fail = root;
        }
        // 其他节点则需要进行遍历指向
        else {
          // 取得当前子节点的失败指针
          AcNode qFailTemp = qItemTmp.fail;

          while (qFailTemp != null) {
            // 1,在上一级失败指针中查找，看能否找到指向当前节点的指针
            int index = this.getIndex(qchildTmp.data);
            AcNode topFailTmp = qFailTemp.childred[index];

            if (topFailTmp != null) {
              qchildTmp.fail = topFailTmp;
              break;
            }
            // 如果为空，则再从上一级节点查找,一直查找到根节点为止
            qFailTemp = qFailTemp.fail;
          }

          // 如果当前未找到，则直接指向root节点
          if (qFailTemp == null) {
            qchildTmp.fail = root;
          }
        }

        queue.add(qchildTmp);
      }
    }
  }

  /**
   * 获取索引节点
   *
   * @param srcArray
   * @return
   */
  public int getIndex(int srcArray) {
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

  /**
   * 添加模式串，并构建失败指针
   *
   * @param list
   */
  public void buildFailure(List<String> list) {

    for (String acMode : list) {
      this.insert(acMode);
    }

    // 构建失败指针
    this.builderFailurePointer();
  }

  /**
   * 进行字符串的匹配操作
   *
   * @param src 字符信息
   * @return 模式串出现的位置信息
   */
  public List<Integer> matchs(String src) {

    // 进行字符串的匹配操作
    char[] mainChar = src.toCharArray();

    AcNode pmatch = root;

    // 进行主串遍历
    for (int i = 0; i < mainChar.length; i++) {
      int index = this.getIndex(mainChar[i]);

      // 失败指针的检查,如果当前字符的下一个字符不能被找到，并且不是根节点
      while (pmatch.childred[index] == null && pmatch != root) {
        pmatch = pmatch.fail;
      }

      // 获取当前字符在失败指针中的位置
      pmatch = pmatch.childred[index];

      // 如果不能被找到，则重新从root节点开始匹配
      if (pmatch == null) {
        pmatch = root;
      }

      AcNode tmpMatch = pmatch;

      while (tmpMatch != root) {
        if (tmpMatch.isEndingChar == true) {
          int matPostion = i - tmpMatch.length + 1;
          System.out.println("当前匹配下标:" + matPostion + ";长度:" + tmpMatch.length);
        }
        tmpMatch = tmpMatch.fail;
      }
    }

    return null;
  }

  /** trie 树的节点信息 */
  public class AcNode {

    /** 存储的数据 */
    public char data;

    /** 存储所有数字，以及一个符号，符号被转义 */
    public AcNode[] childred = new AcNode[AC_SIZE];

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
