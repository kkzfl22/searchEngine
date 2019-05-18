package com.liujun.search.engine.analyze.operation.docraw.docrawReader;

import com.liujun.search.algorithm.boyerMoore.CommCharMatcherInstance;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.DocrawReaderEnum;
import com.liujun.search.engine.analyze.operation.docraw.docrawReader.lineProcess.LineDataProcFlow;
import com.liujun.search.common.constant.SymbolMsg;

import java.util.List;

/**
 * 进行将缓存的上一次的数据进行读取操作
 *
 * <p>使用单模式串匹配算法，检查当前是否存在行结束符\r\n\r\n
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/08
 */
public class LineLastMatcher implements FlowServiceInf {

  /** 实例 */
  public static final LineLastMatcher INSTANCE = new LineLastMatcher();

  /** 最大的buffer中的匹配次数，当达到这个次数时，说时网页中存在问题 */
  private static final int READ_MAX_NUM = 50;

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    byte[] matchBuffer = context.getObject(DocrawReaderEnum.DOCRAW_PROC_CACHE_NEXTBUFFER.getKey());

    // 仅当存在内容时才进行读取
    if (null != matchBuffer) {
      // 检查上一次的数读取数所是否读取完成，如果未读取完成，则需要优先读取，已经完成，则跳过
      this.readBufferToList(context);

      context.remove(DocrawReaderEnum.DOCRAW_PROC_CACHE_NEXTBUFFER.getKey());
    }
    return true;
  }

  private void readBufferToList(FlowServiceContext context) {

    List<byte[]> bufferList =
        context.getObject(DocrawReaderEnum.DOCRAW_PROC_CACHE_BUFFERLIST.getKey());

    int readNum = 0;

    byte[] matchBuffer = context.getObject(DocrawReaderEnum.DOCRAW_PROC_CACHE_NEXTBUFFER.getKey());
    int position = 0;

    // 进行多轮的字符匹配操作
    while (readNum < READ_MAX_NUM) {
      // 进行结束符的查找
      int matchIndex = CommCharMatcherInstance.LINE_END_MATCHER.matcherIndex(matchBuffer, position);

      // 如果当前未找到结束符
      if (matchIndex == -1) {
        // 从缓存中读取指定位置的数据
        byte[] buffers = this.readerBufferDataNotFinish(matchBuffer.length, position, matchBuffer);
        bufferList.add(buffers);

        return;

      }
      // 如果当前找到结束符，则将结束符前的数据加入到缓存结果集中
      else {
        // 当找到结束符后，加入到缓冲字符的集合中
        byte[] endBufferData = this.readerBufferEndAppend(matchIndex, position, matchBuffer);
        bufferList.add(endBufferData);
        // 位置信息
        position = matchIndex + SymbolMsg.LINE_OVER.length();

        // 然后进行对象的转化
        LineDataProcFlow.INSTANCE.parseToListAndCheck(context);
      }

      readNum++;
    }

    throw new RuntimeException("readBufferToList exception ,is loop");
  }

  /**
   * 将数据中间部分的data从集合中读出
   *
   * <p>仅限在未找到结束符时进行读取操作
   *
   * @param dataLength 匹配索引
   * @param buffer 字符
   */
  public byte[] readerBufferDataNotFinish(int dataLength, int lastPostion, byte[] buffer) {

    int readLength = dataLength - lastPostion;

    if (readLength > 0) {
      byte[] dataValue = new byte[readLength];
      System.arraycopy(buffer, lastPostion, dataValue, 0, readLength);

      return dataValue;
    }

    return new byte[0];
  }

  /**
   * 读取并且添加数据到集合中，去
   *
   * @param matchIndex 匹配索引
   * @param buffer 字符
   */
  public byte[] readerBufferEndAppend(int matchIndex, int position, byte[] buffer) {
    // 进行匹配上结束部分数据的读取操作
    int readLength = matchIndex - position + SymbolMsg.LINE_OVER.length();

    if (readLength > 0) {
      byte[] outdata = new byte[readLength];

      System.arraycopy(buffer, position, outdata, 0, readLength);

      return outdata;
    }

    return new byte[0];
  }
}
