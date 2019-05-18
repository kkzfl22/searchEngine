package com.liujun.search.engine.query.process;

import com.liujun.search.common.io.CommonIOUtils;
import com.liujun.search.engine.index.outputDescIndex.DescIndexFileName;
import com.liujun.search.engine.query.pojo.WordOffsetBusi;
import com.liujun.search.common.constant.SymbolMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 进行倒排索引文件的读取操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/14
 */
public class DescIndexReaderProcess {

  /** 字符缓冲区的大小,默认一个8K大小的缓冲区 */
  private static final int BUFFER_SIZE = 1024 * 8;

  /** 字符缓冲区 */
  private ByteBuffer BUFFER = ByteBuffer.allocate(BUFFER_SIZE);

  public static final DescIndexReaderProcess INSTANCE = new DescIndexReaderProcess();

  private Logger logger = LoggerFactory.getLogger(DescIndexReaderProcess.class);

  /**
   * 进行索引文件提取网页列表
   *
   * @param fileIndex 文件索引号
   * @param wordList 需要提取的同一文件的id号
   * @return 提取的网页列表
   */
  public Map<Integer, List<Long>> getWordList(int fileIndex, List<WordOffsetBusi> wordList) {

    String namePath = DescIndexFileName.INSTANCE.getDescIndexFileName(fileIndex);

    FileInputStream inputStream = null;
    FileChannel channel = null;

    Map<Integer, List<Long>> result = new HashMap<>(wordList.size());

    try {
      inputStream = new FileInputStream(namePath);
      channel = inputStream.getChannel();

      byte[] readData;
      for (WordOffsetBusi offset : wordList) {
        // 进行数据读取操作
        readData = this.readData(BUFFER, offset, channel);
        // 进行转换存储
        result.put(offset.getWordId(), this.parseWords(readData, offset.getWordId()));
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("DescIndexReaderProcess getWordList FileNotFoundException:", e);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("DescIndexReaderProcess getWordList IOException:", e);
    } finally {
      CommonIOUtils.close(channel);
      CommonIOUtils.close(inputStream);
    }

    return result;
  }

  /**
   * 进行文件的数据读取操作
   *
   * @param offsetBusi 偏移数据信息
   * @param channel 文件通道
   * @return 读取的byte对象
   */
  private byte[] readData(ByteBuffer buffer, WordOffsetBusi offsetBusi, FileChannel channel)
      throws IOException {
    byte[] outData = new byte[offsetBusi.getLength()];

    int sumnum =
        offsetBusi.getLength() % buffer.limit() == 0
            ? offsetBusi.getLength() / buffer.limit()
            : offsetBusi.getLength() / buffer.limit() + 1;

    int index = 0;
    // 1,计算buffer读取的长度
    int byteLength = offsetBusi.getLength();

    if (byteLength < buffer.limit()) {
      buffer.limit(byteLength);
    }

    // 2,计算byte中索引的下标位置
    int bytePos = 0;

    int offsetIndex = 0;

    while (index < sumnum) {
      int readLength = channel.read(buffer, offsetBusi.getOffset() + offsetIndex);

      if (readLength == -1) {
        throw new RuntimeException("read error:" + readLength);
      }

      offsetIndex += readLength;
      buffer.flip();

      // 进行一个缓冲区的读取
      if (byteLength > readLength) {
        buffer.get(outData, bytePos, readLength);
        bytePos += readLength;
        byteLength -= readLength;
      } else {
        buffer.get(outData, bytePos, byteLength);
      }

      buffer.clear();

      index++;
    }

    return outData;
  }

  /**
   * 进行字符转换操作
   *
   * @param readbytes 字符信息
   * @param wordId 单词的id
   * @return 集合信息
   */
  private List<Long> parseWords(byte[] readbytes, int wordId) {
    int wordsLength = String.valueOf(wordId).getBytes().length + SymbolMsg.DATA_COLUMN.length();

    // 仅提取文本中的内容
    String data = new String(readbytes, wordsLength, readbytes.length - wordsLength);

    String[] wordsArrays = data.split(SymbolMsg.COMMA);

    List<Long> result = null;

    if (null != wordsArrays && wordsArrays.length > 0) {
      result = new ArrayList<>(wordsArrays.length);

      for (String wordDocids : wordsArrays) {
        result.add(Long.parseLong(wordDocids));
      }

      return result;
    } else {
      result = new ArrayList<>(1);
    }

    return result;
  }
}
