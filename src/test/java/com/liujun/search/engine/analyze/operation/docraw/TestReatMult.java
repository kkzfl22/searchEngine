package com.liujun.search.engine.analyze.operation.docraw;

import com.liujun.search.engine.analyze.pojo.RawDataLine;
import com.liujun.search.engine.collect.operation.docraw.DocRawWriteProc;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/07
 */
public class TestReatMult {

  private static final int FORMAT_LENGTH = 11;

  @Before
  public void init() {

    DocRawWriteProc.INSTANCE.cleanAll();
    DocRawWriteProc.INSTANCE.openFile();
    DocRawWriteProc.INSTANCE.threadInit();
    DocRawReaderProc.INSTANCE.setFinish(false);
  }

  @Test
  public void testOneBufferMore1() {

    int writeLength = DocRawWriteProc.BUFFER_SIZE - FORMAT_LENGTH;

    writeHtml(1, writeLength);
    writeHtml(2, writeLength);
    writeHtml(3, writeLength);

    // 在写入完成后，初始化读取操作
    DocRawReaderProc.InitFlow();

    List<RawDataLine> list = DocRawReaderProc.INSTANCE.reader(3);

    for (RawDataLine item : list) {
      Assert.assertEquals(item.getHtmlContext().length(), writeLength);
    }

    Assert.assertEquals(3, list.size());
  }

  @Test
  public void testOneBufferMore2() {

    int writeLength = DocRawWriteProc.BUFFER_SIZE - FORMAT_LENGTH;

    writeHtml(1, writeLength);
    writeHtml(2, writeLength);
    writeHtml(3, writeLength);

    // 在写入完成后，初始化读取操作
    DocRawReaderProc.InitFlow();

    List<RawDataLine> list = DocRawReaderProc.INSTANCE.reader(5);

    for (RawDataLine item : list) {
      Assert.assertEquals(item.getHtmlContext().length(), writeLength);
    }

    Assert.assertEquals(3, list.size());
  }

  @Test
  public void testOneBufferMore3() {

    int writeLength = DocRawWriteProc.BUFFER_SIZE - FORMAT_LENGTH;

    writeHtml(1, writeLength);
    writeHtml(2, writeLength);
    writeHtml(3, writeLength);
    writeHtml(4, writeLength);

    int writeLengthOut = DocRawWriteProc.BUFFER_SIZE - FORMAT_LENGTH - 100;
    writeHtml(5, writeLengthOut);
    writeHtml(6, writeLengthOut);

    // 在写入完成后，初始化读取操作
    DocRawReaderProc.InitFlow();

    List<RawDataLine> list = DocRawReaderProc.INSTANCE.reader(7);

    for (int i = 0; i < list.size(); i++) {
      RawDataLine item = list.get(i);

      Assert.assertEquals(item.getLength(), item.getHtmlContext().length());

      if (i < 4) {
        Assert.assertEquals(item.getHtmlContext().length(), writeLength);
      } else {
        Assert.assertEquals(item.getHtmlContext().length(), writeLengthOut);
      }
    }

    Assert.assertEquals(6, list.size());
  }

  @Test
  public void testOneBufferMore4() {

    int writeLength = DocRawWriteProc.BUFFER_SIZE - FORMAT_LENGTH;

    writeHtml(1, writeLength);
    writeHtml(2, writeLength);
    writeHtml(3, writeLength);
    writeHtml(4, writeLength);

    int writeLengthOut = DocRawWriteProc.BUFFER_SIZE - FORMAT_LENGTH - 100;
    writeHtml(5, writeLengthOut);
    writeHtml(6, writeLengthOut);

    int writeLengthOut2 = (DocRawWriteProc.BUFFER_SIZE - FORMAT_LENGTH) / 3;
    writeHtml(7, writeLengthOut2);
    writeHtml(8, writeLengthOut2);
    writeHtml(9, writeLengthOut2);
    writeHtml(10, writeLengthOut2);
    writeHtml(11, writeLengthOut2);

    // 在写入完成后，初始化读取操作
    DocRawReaderProc.InitFlow();
    List<RawDataLine> list = DocRawReaderProc.INSTANCE.reader(12);

    for (int i = 0; i < list.size(); i++) {
      RawDataLine item = list.get(i);

      Assert.assertEquals(item.getLength(), item.getHtmlContext().length());

      if (i < 4) {
        Assert.assertEquals(item.getHtmlContext().length(), writeLength);
      } else if (i >= 4 && i < 6) {
        Assert.assertEquals(item.getHtmlContext().length(), writeLengthOut);
      } else if (i >= 6 && i < 12) {
        Assert.assertEquals(item.getHtmlContext().length(), writeLengthOut2);
      }
    }

    Assert.assertEquals(11, list.size());
  }

  /** 分多段进行匹配 */
  @Test
  public void testOneBufferMore5() {

    int writeLength = DocRawWriteProc.BUFFER_SIZE - FORMAT_LENGTH;

    writeHtml(1, writeLength);
    writeHtml(2, writeLength);
    writeHtml(3, writeLength);
    writeHtml(4, writeLength);

    int writeLengthOut = DocRawWriteProc.BUFFER_SIZE - FORMAT_LENGTH - 100;
    writeHtml(10, writeLengthOut);
    writeHtml(11, writeLengthOut);
    writeHtml(12, writeLengthOut);
    writeHtml(13, writeLengthOut);

    int writeLengthOut2 = (DocRawWriteProc.BUFFER_SIZE - FORMAT_LENGTH) / 3;
    writeHtml(20, writeLengthOut2);
    writeHtml(21, writeLengthOut2);
    writeHtml(22, writeLengthOut2);
    writeHtml(23, writeLengthOut2);

    // 在写入完成后，初始化读取操作
    DocRawReaderProc.InitFlow();

    List<RawDataLine> allCons = new ArrayList<>();

    List<RawDataLine> list1 = DocRawReaderProc.INSTANCE.reader(4);
    List<RawDataLine> list2 = DocRawReaderProc.INSTANCE.reader(4);
    List<RawDataLine> list3 = DocRawReaderProc.INSTANCE.reader(4);
    List<RawDataLine> list4 = DocRawReaderProc.INSTANCE.reader(4);

    allCons.addAll(list1);
    allCons.addAll(list2);
    allCons.addAll(list3);
    allCons.addAll(list4);

    for (int i = 0; i < allCons.size(); i++) {
      RawDataLine item = allCons.get(i);

      Assert.assertEquals(item.getLength(), item.getHtmlContext().length());

      if (i < 4) {
        Assert.assertEquals(item.getHtmlContext().length(), writeLength);
      } else if (i >= 4 && i < 8) {
        Assert.assertEquals(item.getHtmlContext().length(), writeLengthOut);
      } else if (i >= 8 && i < 11) {
        Assert.assertEquals(item.getHtmlContext().length(), writeLengthOut2);
      }
    }

    Assert.assertEquals(12, allCons.size());
  }

  private void writeHtml(int seqId, int writeMax) {

    StringBuilder outMsg = new StringBuilder();

    for (int i = 0; i < writeMax; i++) {
      outMsg.append(i % 10);
    }
    int writeLength = DocRawWriteProc.INSTANCE.putHtml(seqId, outMsg.toString());

    System.out.println(
        "index :" + seqId + ",write length :" + writeLength + ",context length:" + outMsg.length());
  }

  @After
  public void clean() {
    DocRawReaderProc.INSTANCE.cleanAll();
    DocRawWriteProc.INSTANCE.cleanAll();
  }
}
