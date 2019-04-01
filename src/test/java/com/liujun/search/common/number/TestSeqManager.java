package com.liujun.search.common.number;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 进行序列号管理器的测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/28
 */
public class TestSeqManager {

  /** 测试获取并创建序列号管理器 */
  @Before
  public void testStart() {
    NumberLoopSeq seq = SeqManager.INSTANCE.getOrCreateSeqNum(SeqNameEnum.SEQ_HTML_DOC_ID);
    // 进行重置操作
    seq.reset();
  }

  /** 测试获取并创建序列号管理器 */
  @Test
  public void testSeqMnager() {
    NumberLoopSeq seq = SeqManager.INSTANCE.getOrCreateSeqNum(SeqNameEnum.SEQ_HTML_DOC_ID);

    // 设置起始值
    int start = 899;
    seq.start(start);

    int addNum = 1000;
    for (int i = 0; i < addNum; i++) {
      seq.NextSeqValue();
    }

    Assert.assertEquals(seq.getCurrSeqValue(), start + addNum);
  }

  /** 测试多线程环境中进行序列的生成与累加操作 */
  @Test
  public void testThreadsSeqManager() throws InterruptedException {
    int threadNum = 4;
    SeqManager.INSTANCE.getOrCreateSeqNum(SeqNameEnum.SEQ_HTML_DOC_ID).start(0);

    ExecutorService exec = Executors.newFixedThreadPool(threadNum);

    CountDownLatch downLatchStart = new CountDownLatch(1);
    CountDownLatch downLatchOver = new CountDownLatch(4);

    for (int i = 0; i < threadNum; i++) {
      exec.submit(
          () -> {
            try {
              downLatchStart.await();

              NumberLoopSeq seqManagerObj =
                  SeqManager.INSTANCE.getOrCreateSeqNum(SeqNameEnum.SEQ_HTML_DOC_ID);

              for (int j = 0; j < 1000; j++) {
                seqManagerObj.NextSeqValue();
              }

              downLatchOver.countDown();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          });
    }

    Thread.sleep(500);
    // 1,任务同时开启
    downLatchStart.countDown();

    downLatchOver.await();

    NumberLoopSeq seqManagerObj =
        SeqManager.INSTANCE.getOrCreateSeqNum(SeqNameEnum.SEQ_HTML_DOC_ID);

    long value = seqManagerObj.getCurrSeqValue();

    Assert.assertEquals(4000, value);
  }

  /** 测试获取并创建序列号管理器 */
  @After
  public void testClean() {
    NumberLoopSeq seq = SeqManager.INSTANCE.getOrCreateSeqNum(SeqNameEnum.SEQ_HTML_DOC_ID);

    // 进行重置操作
    seq.reset();
  }
}
