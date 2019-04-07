package com.liujun.search.engine.collect.thread;

import com.liujun.search.engine.collect.constant.WebEntryEnum;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 进行网页的统计操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/07
 */
public class HtmlCount {

  /** 网页计算的操作 */
  private ConcurrentHashMap<WebEntryEnum, AtomicLong> ENTRY_COUNT_MAP = new ConcurrentHashMap<>();

  /**
   * 进行网页入口的添加操作
   *
   * @param entry 入口
   */
  public void putOrAddNum(WebEntryEnum entry) {

    AtomicLong atomicLong = ENTRY_COUNT_MAP.get(entry);

    if (null == atomicLong) {
      AtomicLong newValue = new AtomicLong(0);

      AtomicLong tmpValue = ENTRY_COUNT_MAP.putIfAbsent(entry, newValue);

      if (tmpValue == null) {
        atomicLong = newValue;
      } else {
        atomicLong = tmpValue;
      }
    }

    // 进行变量的加1操作
    atomicLong.incrementAndGet();
  }
}
