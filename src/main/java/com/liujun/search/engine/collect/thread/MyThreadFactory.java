package com.liujun.search.engine.collect.thread;

import java.util.concurrent.ThreadFactory;

/** 自定义的线程工厂实现 */
public class MyThreadFactory implements ThreadFactory {

  private static final String PREFIX = "WORK-";

  @Override
  public Thread newThread( Runnable r) {

    StringBuilder outThreadName = new StringBuilder();
    outThreadName.append(PREFIX);

    // 仅设置一个线程名称
    Thread currThread = new Thread(r, outThreadName.toString());

    return currThread;
  }
}
