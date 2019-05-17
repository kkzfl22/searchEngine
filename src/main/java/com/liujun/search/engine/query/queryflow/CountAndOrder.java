package com.liujun.search.engine.query.queryflow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.query.constant.QueryFlowEnum;
import com.liujun.search.engine.query.pojo.SortDocIdBusi;

import java.util.*;

/**
 * 进行网页的计算与排序
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/16
 */
public class CountAndOrder implements FlowServiceInf {

  public static final CountAndOrder INSTANCE = new CountAndOrder();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    Map<Integer, List<Long>> groupOffsetList =
        context.getObject(QueryFlowEnum.PROC_WORDOFFSET.getKey());

    // 进行统计操作
    Map<Long, Integer> groupMap = this.countMap(groupOffsetList);

    // 进行转换操作
    List<SortDocIdBusi> list = this.getCountList(groupMap);

    // 进行排序
    Collections.sort(list);

    context.put(QueryFlowEnum.PROC_DOCLIST.getKey(), list);

    return true;
  }

  /**
   * 转换并且按统计数据进行排序
   *
   * @param groupMap
   * @return
   */
  private List<SortDocIdBusi> getCountList(Map<Long, Integer> groupMap) {
    List<SortDocIdBusi> list = new ArrayList<>(groupMap.size());

    for (Map.Entry<Long, Integer> groupItem : groupMap.entrySet()) {
      list.add(new SortDocIdBusi(groupItem.getKey(), groupItem.getValue()));
    }

    return list;
  }

  /**
   * 进行网页id出现次数的统计操作
   *
   * @param groupOffsetList
   * @return
   */
  private Map<Long, Integer> countMap(Map<Integer, List<Long>> groupOffsetList) {
    // 进行网页统计操作
    Map<Long, Integer> countSortMap = new HashMap<>();

    int countValue = 0;

    for (Map.Entry<Integer, List<Long>> groupItem : groupOffsetList.entrySet()) {
      for (long countId : groupItem.getValue()) {
        if (!countSortMap.containsKey(countId)) {
          countSortMap.put(countId, 1);
        } else {
          countValue = countSortMap.get(countId);
          countSortMap.put(countId, countValue + 1);
        }
      }
    }

    return countSortMap;
  }
}
