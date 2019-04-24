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
public class TrieFilterWord {

  /** 分词对象 */
  private static final TrieTreeChina TRIE_FILTER_WORD = new TrieTreeChina();

  static {
    // 进行过滤的词组的加载
    Set<String> keys = FilterWordLoader.INSTANCE.getFilterSet();
    for (String key : keys) {
      TRIE_FILTER_WORD.insert(key);
    }
  }

  public static final TrieFilterWord INSTANCE = new TrieFilterWord();

  public TrieTreeChina getFilterWord() {
    return TRIE_FILTER_WORD;
  }
}