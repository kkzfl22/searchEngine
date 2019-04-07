package com.liujun.search.engine.collect.pojo;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 进行网页统计的业务 信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/07
 */
public class CountBusi {

  /** 进行次数的统计 */
  private AtomicLong count = new AtomicLong();

  /** 最后一次的时间 */
  private AtomicLong lastTime = new AtomicLong();

  /** 开始时间 */
  private AtomicLong startTime = new AtomicLong();
}
