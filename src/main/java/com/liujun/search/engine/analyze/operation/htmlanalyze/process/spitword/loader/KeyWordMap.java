package com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.loader;

import java.util.Map;
import java.util.Set;

/**
 * 分词的关键词处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/25
 */
public class KeyWordMap {

  /** 获取键的map */
  private static final Map<String, Integer> KEYWORD = WordLoader.INSTANCE.getKeywordMap();


  public static final KeyWordMap INSTANCE = new KeyWordMap();

  /**
   * 获取所有关系词人信息
   *
   * @return
   */
  public Set<String> getKeys() {
    return KEYWORD.keySet();
  }

  /**
   * 获取键的索引
   *
   * @param key 键信息
   * @return 索引号
   */
  public int getKeyIndex(String key) {
    Integer ins = KEYWORD.get(key);

    if (ins != null) {
      return ins;
    }

    return -1;
  }
}
