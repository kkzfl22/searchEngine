package com.liujun.search.engine.query.cache;

import com.liujun.search.engine.query.pojo.WordOffsetBusi;
import com.liujun.search.engine.query.process.WordOffsetReaderProcess;

import java.util.HashMap;
import java.util.Map;

/**
 * 进行网页单词偏移缓存操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/14
 */
public class WordOffsetCache {

  public static final WordOffsetCache INSTANCE = new WordOffsetCache();

  /** 分词的偏移量信息 */
  private static final Map<Integer, WordOffsetBusi> WORKOFFSET_MAP = new HashMap<>();

  static {
    loadWordOffset();
  }

  private static void loadWordOffset() {
    WordOffsetReaderProcess.INSTANCE.loadWordOffsetMap(WORKOFFSET_MAP);
  }

  public WordOffsetBusi getWordOffset(int wordId) {
    return WORKOFFSET_MAP.get(wordId);
  }
}
