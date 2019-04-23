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
public class TrideTreeWord {

  /** 分词对象 */
  public static final TrieTreeChina TRIECHINA = new TrieTreeChina();

  static {
    // 进行tride树的加载操作
    Set<String> keys = WordLoader.INSTANCE.getKeyword();
    for (String key : keys) {
      TRIECHINA.insert(key);
    }

  }

  public static final TrideTreeWord INSTANCE = new TrideTreeWord();

  public TrieTreeChina getSpitWord() {
    return TRIECHINA;
  }
}
