package com.liujun.search.engine.analyze.operation.htmlanalyze.process;

import com.liujun.search.algorithm.ahoCorasick.pojo.MatcherBusi;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.WordFLowEnum;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.WordsKeyIndexFlow;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.WordsKeyWriteTmpIndexFlow;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.getSpitWord.SpitWorkProcess;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 进行每一个网页的分词流程
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/24
 */
public class SpitWordProcess {

  public static final SpitWordProcess INSTANCE = new SpitWordProcess();

  /** 集合 */
  private static final List<FlowServiceInf> FLOW = new ArrayList<>();

  /** 上下文集合对象 */
  private static final FlowServiceContext CONTEXT = new FlowServiceContext();

  static {
    // 1,找到分词的索引
    FLOW.add(WordsKeyIndexFlow.INSTANCE);
    // 2,将分词索引以及网页编写入临时索引文件中
    FLOW.add(WordsKeyWriteTmpIndexFlow.INSTANCE);
  }

  /**
   * 进行分词的处理
   *
   * @param data 数据停下
   * @param docId 网页的id
   */
  public void spitWord(String data, int docId) {
    if (StringUtils.isEmpty(data)) {
      return;
    }

    char[] charContext = data.toCharArray();

    int pos = 0;
    int wordLength = 0;

    int index = 0;

    while (index < charContext.length) {
      MatcherBusi matchBusi = SpitWorkProcess.INSTANCE.spitWord(charContext, pos);

      if (matchBusi.getMatcherIndex() != -1) {
        if (matchBusi.getMatcherKey() != null) {
          // 当匹配到字符后，则进行当前分词的流程处理
          wordIndexFLow(matchBusi.getMatcherKey(), docId, CONTEXT);

          wordLength = matchBusi.getMatcherKey().length();
        } else {
          wordLength = 0;
        }
        pos = matchBusi.getMatcherIndex() + wordLength;
      } else {
        break;
      }
      index++;
    }
  }

  /**
   * 分词数据的写入操作
   *
   * @param key 关键字
   * @param docId 数据id
   */
  private void wordIndexFLow(String key, int docId, FlowServiceContext context) {

    // 当前的分词
    context.put(WordFLowEnum.WORD_INPUT_WORD.getKey(), key);
    // 当前的网页id
    context.put(WordFLowEnum.WORKD_INPUT_DOCID.getKey(), docId);

    try {
      for (FlowServiceInf flowItem : FLOW) {
        if (!flowItem.runFlow(context)) {
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    context.cleanParam();
  }
}
