package com.liujun.search.common.number;

import org.junit.Assert;
import org.junit.Test;

/**
 * 进行序列id的测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/28
 */
public class TestNumberSeq {

  /** 进行序列值的获取 */
  @Test
  public void seqTest() {
    int start = 1000;

    NumberLoopSeq instance = NumberLoopSeq.getNewInstance();

    int runNum = 1000;
    instance.start(start);

    for (int i = 0; i < runNum; i++) {
      instance.NextSeqValue();
    }

    Assert.assertEquals(instance.getCurrSeqValue(), start + runNum);
  }
}
