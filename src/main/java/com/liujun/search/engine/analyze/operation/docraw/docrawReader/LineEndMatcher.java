package com.liujun.search.engine.analyze.operation.docraw.docrawReader;

import com.liujun.search.algorithm.boyerMoore.CommCharMatcherInstance;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.DocrawReaderEnum;
import com.liujun.search.engine.analyze.operation.docraw.docrawReader.lineProcess.LineDataProcFlow;
import com.liujun.search.common.constant.SymbolMsg;

import java.nio.ByteBuffer;
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

  /** 实例 */
  public static final LineEndMatcher INSTANCE = new LineEndMatcher();

  /** 最大的buffer中的匹配次数，当达到这个次数时，说时网页中存在问题 */
  private static final int READ_MAX_NUM = 200;

  /** 缓存长度 */
  public static final int MATCH_CACHE_SIZE = 3;

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    ByteBuffer buffer = context.getObject(DocrawReaderEnum.DOCRAW_INPUT_READER_BUFFER.getKey());

    // 仅当存在内容时才进行读取
    if (buffer.limit() > 0) {
      // 获取数据集合大小
      byte[] matchBuffer = new byte[buffer.limit()];
      buffer.get(matchBuffer);

      // 进行数据转换成集合操作
      return this.readBufferToList(matchBuffer, buffer, context);
    }
    return true;
  }

  private boolean readBufferToList(
      byte[] matchBuffer, ByteBuffer buffer, FlowServiceContext context) {

    List<byte[]> bufferList =
        context.getObject(DocrawReaderEnum.DOCRAW_PROC_CACHE_BUFFERLIST.getKey());

    int readNum = 0;

    int position = 0;

    // 进行多轮的字符匹配操作
    while (readNum < READ_MAX_NUM) {
      // 进行结束符的查找
      int matchIndex = CommCharMatcherInstance.LINE_END_MATCHER.matcherIndex(matchBuffer, position);

      // 如果当前未找到结束符
      if (matchIndex == -1) {
        byte[] buffers = this.readerBufferDataNotFinish(matchBuffer.length, position, buffer);

        if (buffers != null && buffers.length > 0) {
          bufferList.add(buffers);
        }

        buffer.compact();

        return false;
      }
      // 如果当前找到结束符，则将结束符前的数据加入到缓存结果集中
      else {
        // 当找到结束符后，加入到缓冲字符的集合中
        byte[] endBufferData = this.readerBufferEndAppend(matchIndex, position, buffer);
        bufferList.add(endBufferData);

        // 然后进行对象的转化
        boolean runFlag = LineDataProcFlow.INSTANCE.parseToListAndCheck(context);

        // 如果当前已经到达了阈值，则返回，但需要记录下当前的
        if (runFlag) {
          // 将buffer中空余的数据记录到上下文中，下次需要优先读取
          byte[] buferNext = this.readerBufferNext(matchIndex, buffer);

          if (buferNext.length > 0) {
            context.put(DocrawReaderEnum.DOCRAW_PROC_CACHE_NEXTBUFFER.getKey(), buferNext);
          }

          buffer.compact();

          return true;
        }

        // 位置信息
        position = matchIndex + SymbolMsg.LINE_OVER.length();
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
   * <p>每次读取时，只读取开始到倒数第三个字符
   *
   * @param dataLength 匹配索引
   * @param buffer 字符
   */
  private byte[] readerBufferDataNotFinish(int dataLength, int positon, ByteBuffer buffer) {
    if (buffer.position() <= MATCH_CACHE_SIZE) {
      return new byte[0];
    }

    int readLength = dataLength - positon - MATCH_CACHE_SIZE;

    if (readLength > 0) {
      byte[] outdata = new byte[readLength];
      buffer.position(positon);
      buffer.get(outdata, 0, readLength);

      // 进行压缩操作，将最后三个字符留在buffer中
      int pos = dataLength - MATCH_CACHE_SIZE;
      buffer.position(pos);

      return outdata;
    }

    return new byte[0];
  }

  /**
   * 读取并且添加数据到集合中，去
   *
   * @param matchIndex 匹配索引
   * @param buffer 字符
   */
  private byte[] readerBufferEndAppend(int matchIndex, int position, ByteBuffer buffer) {
    // 进行匹配上结束部分数据的读取操作
    int readLength = matchIndex + SymbolMsg.LINE_OVER.length() - position;

    if (readLength < 0) {
      throw new RuntimeException("readerBufferEndAppend end error length:" + readLength);
    }

    byte[] outdata = new byte[readLength];
    buffer.position(position);
    buffer.get(outdata, 0, readLength);

    return outdata;
  }

  /**
   * 读取buffer中下半部分的数据
   *
   * @param matchIndex 匹配索引
   * @param buffer 字符
   */
  private byte[] readerBufferNext(int matchIndex, ByteBuffer buffer) {

    int matIndex = matchIndex + SymbolMsg.LINE_OVER.length();

    // 进行匹配上结束部分数据的读取操作
    int readLength = buffer.limit() - matIndex;

    if (readLength > 0) {
      byte[] outdata = new byte[readLength];
      buffer.position(matIndex);
      buffer.get(outdata, 0, readLength);

      return outdata;
    }
    return new byte[0];
  }
}
