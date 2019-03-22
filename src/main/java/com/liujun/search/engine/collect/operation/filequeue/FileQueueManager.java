package com.liujun.search.engine.collect.operation.filequeue;

import com.liujun.search.engine.collect.constant.WebEntryEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件队列管理代码的实现
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/01
 */
public class FileQueueManager {

  /** 实例对象 */
  private static final Map<WebEntryEnum, FileQueue> ENTRY_INSTANCE_MAP = new HashMap<>();

  static {
    for (WebEntryEnum entry : WebEntryEnum.values()) {
      ENTRY_INSTANCE_MAP.put(entry, FileQueue.GetQueue(entry));
    }
  }

  public static final FileQueueManager INSTANCE = new FileQueueManager();

  private FileQueueManager() {}

  /**
   * 获取文件队列信息
   *
   * @param entry 入口信息
   * @return 文件队列信息
   */
  public FileQueue getFileQueue(WebEntryEnum entry) {

    return ENTRY_INSTANCE_MAP.get(entry);
  }
}
