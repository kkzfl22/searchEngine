package com.liujun.search.engine.analyze.operation.docraw.docrawReader;

import com.liujun.search.algorithm.boyerMoore.CommCharMatcherInstance;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.DocrawReaderEnum;
import com.liujun.search.utilscode.io.constant.SymbolMsg;

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
  public static final int MATCH_CACHE_SIZE = 3;

  /** 实例 */
  public static final LineEndMatcher INSTANCE = new LineEndMatcher();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    ByteBuffer buffer = context.getObject(DocrawReaderEnum.DOCRAW_INPUT_READER_BUFFER.getKey());

    // 仅当存在内容时才进行读取
    if (buffer.limit() > 0) {
      // 获取数据集合大小
      byte[] matchBuffer = new byte[buffer.limit()];
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
        // 如果为首次匹配，则将将所有字符加入到缓存命令中
        if (bufferList.isEmpty()) {
          // 进行压缩操作，准备进行下一次的读取
          buffer.compact();
          bufferList.add(matchBuffer);
        } else {
          byte[] buffers = this.readerBufferData(matchBuffer.length, buffer, matchBuffer);
          bufferList.add(buffers);
          // 加入跨页的标识
          context.put(DocrawReaderEnum.DOCRAW_PROC_PAGE_END_APPEND.getKey(), true);
        }

        return false;
      }
      // 如果当前找到结束符，则将结束符前的数据加入到缓存结果集中
      else {
        // 如果当前存在跨页的标识，则在读取时需要去掉前3个字符
        Boolean endAppendFlag =
            context.getObject(DocrawReaderEnum.DOCRAW_PROC_PAGE_END_APPEND.getKey());
        if (null != endAppendFlag && endAppendFlag) {
          byte[] endBufferData = this.readerBufferEndAppend(matchIndex, buffer);
          bufferList.add(endBufferData);
          context.remove(DocrawReaderEnum.DOCRAW_PROC_PAGE_END_APPEND.getKey());
        }
        // 当不存在跨页时，则不需要
        else {
          byte[] endBufferData = this.readerBufferEnd(matchIndex, buffer);
          bufferList.add(endBufferData);
        }
      }
    }
    return true;
  }

  /**
   * 将数据中间部分的data从集合中读出
   *
   * @param dataLength 匹配索引
   * @param buffer 字符
   * @param matchBuffer 字符信息
   */
  private byte[] readerBufferData(int dataLength, ByteBuffer buffer, byte[] matchBuffer) {
    if (buffer.position() <= MATCH_CACHE_SIZE) {
      return new byte[0];
    }

    int readLength = dataLength - MATCH_CACHE_SIZE;
    byte[] outdata = new byte[readLength];
    buffer.position(MATCH_CACHE_SIZE);
    buffer.get(outdata, 0, readLength);

    // 进行压缩操作，准备进行下一次的读取,位置需要加上结束符的长度
    buffer.position(dataLength);
    buffer.compact();

    // 将最后三个字符放入到byteBuffer中
    buffer.put(matchBuffer[matchBuffer.length - 3]);
    buffer.put(matchBuffer[matchBuffer.length - 2]);
    buffer.put(matchBuffer[matchBuffer.length - 1]);

    return outdata;
  }

  /**
   * 读取并且添加数据到集合中，去
   *
   * @param matchIndex 匹配索引
   * @param buffer 字符
   */
  private byte[] readerBufferEndAppend(int matchIndex, ByteBuffer buffer) {
    int readLength = matchIndex - MATCH_CACHE_SIZE;
    byte[] outdata = new byte[readLength];
    buffer.position(MATCH_CACHE_SIZE);
    buffer.get(outdata, 0, readLength);

    // 进行压缩操作，准备进行下一次的读取,位置需要加上结束符的长度
    buffer.position(matchIndex + SymbolMsg.LINE_OVER.length());
    buffer.compact();

    return outdata;
  }

  /**
   * 读取并且添加数据到集合中
   *
   * @param matchIndex 匹配索引
   * @param buffer 字符
   */
  private byte[] readerBufferEnd(int matchIndex, ByteBuffer buffer) {
    int readLength = matchIndex;
    byte[] outdata = new byte[readLength];
    buffer.position(0);
    buffer.get(outdata, 0, readLength);
    // 进行压缩操作，准备进行下一次的读取,位置需要加上结束符的长度
    buffer.position(matchIndex + SymbolMsg.LINE_OVER.length());
    buffer.compact();

    return outdata;
  }
}
