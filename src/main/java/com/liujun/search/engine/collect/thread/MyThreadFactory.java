package com.liujun.search.engine.collect.thread;

import com.sun.istack.internal.NotNull;

import java.util.concurrent.ThreadFactory;

/** 自定义的线程工厂实现 */
public class MyThreadFactory implements ThreadFactory {

  private static final String PREFIX = "WORK-";

  @Override
  public Thread newThread(@NotNull Runnable r) {

    long threadId = Thread.currentThread().getId();

    StringBuilder outThreadName = new StringBuilder();
    outThreadName.append(PREFIX);

    outThreadName.append(threadId);

    // 仅设置一个线程名称
    Thread currThread = new Thread(r, outThreadName.toString());

    return currThread;
  }
}
