package com.liujun.search.engine.query.process;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Ordering;
import org.junit.Test;

import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/16
 */
public class UseBiMap {

  @Test
  public void useBiMap() {
    BiMap<Long, Integer> map = HashBiMap.create();

    map.put(1l, 10);
    map.put(2l, 9);
    map.put(3l, 6);
    map.put(4l, 60);

    Ordering<Integer> order = Ordering.natural().reverse();

    List<Integer> sortvalue = order.sortedCopy(map.values());

    System.out.println(sortvalue);
  }
}
