package com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.loader;

import com.liujun.search.algorithm.trieTree.TrieTreeChina;

import java.util.Set;

/**
 * 用来构建用于分词的trie树
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/22
 */
public class TrieTreeWord {

  /** 分词对象 */
  private static final TrieTreeChina TRIE_WORD_CHINA = new TrieTreeChina();

  static {
    // 进行tride树的加载操作
    Set<String> keys = WordLoader.INSTANCE.getKeys();
    for (String key : keys) {
      TRIE_WORD_CHINA.insert(key);
    }
  }

  public static final TrieTreeWord INSTANCE = new TrieTreeWord();

  public TrieTreeChina getSpitWord() {
    return TRIE_WORD_CHINA;
  }
}
