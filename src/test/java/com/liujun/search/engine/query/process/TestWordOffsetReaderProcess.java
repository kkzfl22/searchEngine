package com.liujun.search.engine.query.process;

import com.liujun.search.engine.query.pojo.WordOffsetBusi;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试分词索引的偏移信息处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/13
 */
public class TestWordOffsetReaderProcess {

  @Test
  public void testwordOffsetMap() {
    Map<Integer, WordOffsetBusi> wordoffsetMap = new HashMap<>();
    WordOffsetReaderProcess.INSTANCE.loadWordOffsetMap(wordoffsetMap);

    Assert.assertNotNull(wordoffsetMap);
    Assert.assertNotEquals(0, wordoffsetMap.size());
  }
}
