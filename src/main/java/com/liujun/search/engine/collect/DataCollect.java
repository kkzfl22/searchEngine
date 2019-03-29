package com.liujun.search.engine.collect;

import com.liujun.search.engine.collect.constant.WebEntryEnum;
import com.liujun.search.engine.collect.thread.CollectThreadPool;
import com.liujun.search.engine.collect.thread.HtmCollectThread;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 数据采集操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/01
 */
public class DataCollect {

  public static final DataCollect INSTANCE = new DataCollect();

  public void collect() {

    for (WebEntryEnum entry : WebEntryEnum.values()) {
      // 遍动网页收集线程
      CollectThreadPool.INSTANCE.submitTask(new HtmCollectThread(entry));
    }
  }

  public static void main(String[] args) {

    // 启动网页的收集操作
    DataCollect.INSTANCE.collect();
  }
}
