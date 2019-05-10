package com.liujun.search.engine.analyze.operation.docraw;

import com.liujun.search.engine.analyze.pojo.RawDataLine;
import com.liujun.search.engine.collect.operation.docraw.DocRawWriteProc;
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
    int readSize = 800;
    List<RawDataLine> list = DocRawReaderProc.INSTANCE.reader(readSize);
    Assert.assertEquals(readSize, list.size());
  }

  @Test
  public void testWriteAndReader() {

    DocRawWriteProc.INSTANCE.openFile();
    DocRawWriteProc.INSTANCE.threadInit();

    int max = 32 * 1024 + 2;
    int readSize = 1;

    List<RawDataLine> list = this.putAndGet(max, readSize, 1);

    Assert.assertEquals(readSize, list.size());
  }

  @Test
  public void testWriteAndReader2() {

    DocRawWriteProc.INSTANCE.openFile();
    DocRawWriteProc.INSTANCE.threadInit();

    int max = 32 * 1024 + 2;
    int readSize = 1;

    List<RawDataLine> list = this.putAndGet(max, readSize, 1);

    Assert.assertEquals(1, list.size());
    max = 32 * 1024 + 3;
    readSize = 1;

    list = this.putAndGet(max, readSize, 2);

    Assert.assertEquals(readSize, list.size());
  }

  private List<RawDataLine> putAndGet(int max, int readSize, long seqId) {

    StringBuilder outMsg = new StringBuilder();

    for (int i = 0; i < max; i++) {
      outMsg.append(i % 10);
    }

    DocRawWriteProc.INSTANCE.putHtml(seqId, outMsg.toString());

    return DocRawReaderProc.INSTANCE.reader(readSize);
  }

  // @After
  public void clean() {
    DocRawWriteProc.INSTANCE.threadClean();
    DocRawReaderProc.INSTANCE.cleanAll();
    DocRawWriteProc.INSTANCE.cleanAll();
  }

  /** 进行循环的读取数据 */
  @Test
  public void loopReader() {
    int sum = 0;
    while (!DocRawReaderProc.INSTANCE.checkFinish()) {
      List<RawDataLine> list = DocRawReaderProc.INSTANCE.reader(100);
      Assert.assertNotNull(list);

      // this.print(list);

      sum += list.size();
    }

    System.out.println("总条数:" + sum);

    Assert.assertEquals(true, DocRawReaderProc.INSTANCE.checkFinish());
  }

  private void print(List<RawDataLine> list) {
    int index = 0;
    for (RawDataLine data : list) {
      System.out.print(data.getId() + "\t");
      if (index % 10 == 0) {
        System.out.println();
      }
      index++;
    }
  }
}
