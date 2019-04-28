package com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.getSpitWord;

import com.liujun.search.algorithm.ahoCorasick.pojo.MatcherBusi;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.SpitWordFlowEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 进行分词的操作
 *
 * <p>进行网页的分词流程
 *
 * <p>1,使用分词表进行分词操作,如果分词成功，返回，未成功分词继续
 *
 * <p>2,使用过滤的词进行匹配，匹配上，返回索引位置，未匹配上则继续
 *
 * <p>3,当前面两种都示匹配时，则对当前的字符做跳过一位的处理,即索引位置向前进1
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/24
 */
public class SpitWorkProcess {

  private static final List<FlowServiceInf> FLOW = new ArrayList<>();

  static {
    // 1,使用分词表进行分词操作
    FLOW.add(SpitWordTable.INSTANCE);
    // 2,进行无意义词的过滤
    FLOW.add(FilterWordTable.INSTANCE);
    // 3,当分词表以及无意义词都无法匹配，则需要进行跳过一个字符的处理操作
    FLOW.add(JumpCharset.INSTANCE);
  }

  public static final SpitWorkProcess INSTANCE = new SpitWorkProcess();

  /** 进行流程运行的上下文对象 */
  private FlowServiceContext context = new FlowServiceContext();

  private Logger logger = LoggerFactory.getLogger(SpitWorkProcess.class);

  public MatcherBusi spitWord(char[] contextArrays, int pos) {

    MatcherBusi result = null;

    if (pos >= contextArrays.length) {
      result = new MatcherBusi();
      result.setMatcherIndex(-1);
      return result;
    }

    context.put(SpitWordFlowEnum.SPITWORD_INPUT_CONTEXT_ARRAYS.getKey(), contextArrays);
    context.put(SpitWordFlowEnum.SPITWORD_INPUT_POSITION.getKey(), pos);

    try {
      for (FlowServiceInf flowItem : FLOW) {
        if (!flowItem.runFlow(context)) {
          break;
        }
      }

      result = context.getObject(SpitWordFlowEnum.SPITWORD_OUTPUT_WORDBUSI.getKey());

    } catch (Exception e) {
      e.printStackTrace();
      logger.error("SpitWorkProcess spitWord exception:", e);
    } finally {
      context.cleanParam();
    }

    return result;
  }
}
