package com.liujun.search.common.number;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 计数器
 *
 * @author liujun
 * @date 2014年6月10日
 * @vsersion 0.0.1
 */
public class NumberSeq {

  public static final NumberSeq INSTANCE = new NumberSeq();

  private NumberSeq() {}

  /** 序列 */
  private static final AtomicLong SEQ_LONG = new AtomicLong(0);

  /**
   * 以原子方式进行加1 ，得到序列值
   *
   * @return
   */
  public long NextSeqValue() {
    boolean exec = SEQ_LONG.compareAndSet(Long.MAX_VALUE, 0);

    if (!exec) {
      return SEQ_LONG.incrementAndGet();
    }

    return 0;
  }

  /**
   * 得到当前序列的值
   *
   * @return
   */
  public long getCurrSeqValue() {
    return SEQ_LONG.get();
  }
}
