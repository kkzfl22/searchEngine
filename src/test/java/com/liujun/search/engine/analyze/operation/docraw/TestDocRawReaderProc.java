package com.liujun.search.engine.analyze.operation.docraw;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 进行文件读取的测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/09
 */
public class TestDocRawReaderProc {

  @Test
  public void testRead() {
    int readSize = 10;
    List<DocRawReaderProc> list = DocRawReaderProc.INSTANCE.reader(readSize);
    Assert.assertEquals(readSize, list.size());
  }
}
