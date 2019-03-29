package com.liujun.search.common.number;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 全局序列号生成器
 *
 * <p>循环序列，当数列到达最大值时，即重置为0，重新开始序列号
 *
 * @author liujun
 * @date 2014年6月10日
 * @vsersion 0.0.1
 */
public class NumberLoopSeq {

  /**
   * 获取序列信息
   *
   * @return 结果
   */
  public static NumberLoopSeq getNewInstance() {
    return new NumberLoopSeq();
  }

  /** 序列 */
  private static final AtomicLong SEQ_LONG = new AtomicLong(0);

  /**
   * 设置网页编号的起始值
   *
   * @param value
   */
  public void start(long value) {

    if (getCurrSeqValue() != 0) {
      throw new RuntimeException("seq init already");
    }
    // 仅能修改一次
    SEQ_LONG.compareAndSet(0, value);
  }

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
