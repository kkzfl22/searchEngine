package com.liujun.search.engine.query.process;

import com.liujun.search.engine.query.pojo.WordOffsetBusi;

import java.util.List;
import java.util.Map;

/**
 * 进行倒排索引文件的读取操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/14
 */
public class DescIndexReaderProcess {

  public static final DescIndexReaderProcess INSTANCE = new DescIndexReaderProcess();

  public Map<Integer, List<Long>> getWordList(int fileIndex, List<WordOffsetBusi> wordList) {
    return null;
  }
}
