package com.liujun.search.engine.query.queryflow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.query.constant.QueryFlowEnum;
import com.liujun.search.engine.query.pojo.WordOffsetBusi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取单词的所对应的网页编号
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/13
 */
public class GroupFileOffset implements FlowServiceInf {

  public static final GroupFileOffset INSTANCE = new GroupFileOffset();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    Map<String, Integer> wordList = context.getObject(QueryFlowEnum.PROC_SPITWORD.getKey());

    // 获取单启所对应的偏移信息
    Map<Integer, WordOffsetBusi> wordOffsetMap =
        context.getObject(QueryFlowEnum.PROC_SPITWORD.getKey());

    // 提取单词偏移信息
    List<WordOffsetBusi> offsetList = this.getWordOffsetList(wordList, wordOffsetMap);

    // 进行按文件的分组查询操作
    Map<Integer, List<WordOffsetBusi>> groupOffsetList = this.groupWordOffset(offsetList);

    context.put(QueryFlowEnum.PROC_GROUPOFFSET.getKey(), groupOffsetList);

    return true;
  }

  /**
   * 进行按文件的分组操作
   *
   * @param offsetList 文件偏移集合信息
   * @return 分组后的文件偏移信息
   */
  private Map<Integer, List<WordOffsetBusi>> groupWordOffset(List<WordOffsetBusi> offsetList) {
    Map<Integer, List<WordOffsetBusi>> result = new HashMap<>();

    List<WordOffsetBusi> listOffset = null;

    for (WordOffsetBusi busi : offsetList) {
      listOffset = result.get(busi.getFileIndex());

      if (null == listOffset) {
        listOffset = new ArrayList<>();
        result.put(busi.getFileIndex(), listOffset);
      }

      listOffset.add(busi);
    }

    return result;
  }

  /**
   * 获取单词偏移的集合信息
   *
   * @param wordList 单词集合
   * @param wordMap 单词所对应的偏移信息
   * @return 集合偏移信息
   */
  private List<WordOffsetBusi> getWordOffsetList(
      Map<String, Integer> wordList, Map<Integer, WordOffsetBusi> wordMap) {
    // 提取得到单启编号所对应的偏移信息
    List<WordOffsetBusi> offsetList = new ArrayList<>(wordList.size());

    for (Map.Entry<String, Integer> item : wordList.entrySet()) {
      offsetList.add(wordMap.get(item.getValue()));
    }

    return offsetList;
  }
}
