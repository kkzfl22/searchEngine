package com.liujun.search.engine.collect;

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

  public static List<String> INTEL_ENTRY_LIST = new ArrayList<>();

  static {
    INTEL_ENTRY_LIST.add("https://www.qq.com");
    INTEL_ENTRY_LIST.add("http://www.sohu.com");
    INTEL_ENTRY_LIST.add("https://www.163.com");
  }

  public void collect() {

    LinkedList<String> queue = new LinkedList<>();

    // 1,将入放入到队列中作为起妈搜索点


  }
}
