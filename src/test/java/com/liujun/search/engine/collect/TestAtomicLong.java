package com.liujun.search.engine.collect;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/07
 */
public class TestAtomicLong {

  @Test
  public void add() {
    AtomicLong atLong = new AtomicLong(100);

    atLong.addAndGet(200);
    System.out.println(atLong.get());
  }
}
