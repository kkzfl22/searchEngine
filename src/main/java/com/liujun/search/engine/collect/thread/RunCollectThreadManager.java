package com.liujun.search.engine.collect.thread;

import com.liujun.search.engine.collect.constant.WebEntryEnum;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用来进行收集数据线程的管理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/07
 */
public class RunCollectThreadManager {

  /** 用来进行收集线程的管理操作 */
  private static final ConcurrentHashMap<WebEntryEnum, HtmCollectThread> THREAD_MAP =
      new ConcurrentHashMap<>();

  public static final RunCollectThreadManager INTANACE = new RunCollectThreadManager();

  /**
   * 添加网页线程
   *
   * @param key　网页入口的key
   * @param thread 网页的线程
   */
  public void putThread(WebEntryEnum key, HtmCollectThread thread) {
    THREAD_MAP.put(key, thread);
  }

  /** 将所有运行运程的线程改为false未运行 */
  public void stopThread() {
    for (Map.Entry<WebEntryEnum, HtmCollectThread> entry : THREAD_MAP.entrySet()) {
      entry.getValue().getRunFlag().set(false);
    }
  }

  /**
   * 检查任务是否已经结束
   *
   * @return true 已经结束 false 任务未结束
   */
  public boolean checkFinish() {
    for (Map.Entry<WebEntryEnum, HtmCollectThread> entry : THREAD_MAP.entrySet()) {

      // 检查当前是否已经结束
      if (!entry.getValue().getFinishFlag().get()) {
        return false;
      }
    }

    return true;
  }

  /** 将所有运行运程的线程改为false未运行 */
  public void closeResource() {
    for (Map.Entry<WebEntryEnum, HtmCollectThread> entry : THREAD_MAP.entrySet()) {
      entry.getValue().cleanResource();
    }
  }
}
