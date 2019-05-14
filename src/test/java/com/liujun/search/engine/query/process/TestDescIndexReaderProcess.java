package com.liujun.search.engine.query.process;

import com.liujun.search.engine.query.pojo.WordOffsetBusi;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 进行倒排索文件的读取操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/14
 */
public class TestDescIndexReaderProcess {

  /** 测试倒排索引文件的读取操作 */
  @Test
  public void testDescIndexReader() {
    List<WordOffsetBusi> busiList = new ArrayList<>();

    WordOffsetBusi busi = new WordOffsetBusi();

    busi.setFileIndex(0);
    busi.setOffset(0);
    busi.setWordId(0);
    busi.setLength(3443758);
    busiList.add(busi);

    WordOffsetBusi busi2 = new WordOffsetBusi();

    busi2.setFileIndex(0);
    busi2.setWordId(253041);
    busi2.setOffset(337730599);
    busi2.setLength(12);
    busiList.add(busi2);

    int index = 0;

    Map<Integer, List<Long>> list = DescIndexReaderProcess.INSTANCE.getWordList(index, busiList);

    Assert.assertNotNull(list);
    Assert.assertNotEquals(0, list.size());
  }
}
