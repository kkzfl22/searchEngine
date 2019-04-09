package com.liujun.search.engine.analyze.operation.docraw.docrawReader;

import com.liujun.search.algorithm.boyerMoore.CommCharMatcherInstance;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.DocrawReaderEnum;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 进行行结束符的匹配操作
 *
 * <p>使用单模式串匹配算法，检查当前是否存在行结束符\r\n\r\n
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/08
 */
public class LineEndMatcher implements FlowServiceInf {

  /** 缓存长度 */
  private static final int MATCH_CACHE_SIZE = 3;

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    ByteBuffer buffer = context.getObject(DocrawReaderEnum.DOCRAW_INPUT_READER_BUFFER.getKey());

    byte[] matchBuffer = new byte[buffer.position()];
    buffer.get(matchBuffer);

    int matchIndex = CommCharMatcherInstance.LINE_END_MATCHER.matcherIndex(matchBuffer, 0);

    // 检查缓存buffer数组集合是否存在
    List<byte[]> bufferList =
        context.getObject(DocrawReaderEnum.DOCRAW_PROC_CACHE_BUFFERLIST.getKey());

    if (null == bufferList) {
      bufferList = new ArrayList<>();
      context.put(DocrawReaderEnum.DOCRAW_PROC_CACHE_BUFFERLIST.getKey(), bufferList);
    }

    // 如果当前未找到结束符
    if (matchIndex == -1) {
      bufferList.add(matchBuffer);
    }
    // 如果当前找到结束符，则将结束符前的数据加入到缓存结果集中
    else {
      int readLength = matchIndex - MATCH_CACHE_SIZE;
      byte[] outdata = new byte[readLength];
      buffer.get(outdata, MATCH_CACHE_SIZE, readLength);
      bufferList.add(outdata);
      buffer.position(matchIndex);
      // 进行压缩操作，准备进行下一次的读取
      buffer.compact();
    }

    return true;
  }
}
