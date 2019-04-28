package com.liujun.search.common.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * bytebuffer的操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/26
 */
public class ByteBufferUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(ByteBufferUtils.class);

  /**
   * 将数据写入至文件中
   *
   * @param buffer 缓冲区信息
   * @param channel 通道信息
   * @param data 字符串数据内容
   * @return 写入的字节数
   */
  public static int wirteChannel(ByteBuffer buffer, FileChannel channel, String data) {

    if (buffer.position() != 0) {
      throw new RuntimeException("wirte  buffer  error, buffer postion:" + buffer.position());
    }

    int writeBytes = 0;

    // 2,将当前数据转码为byte数据
    byte[] dataByte = data.getBytes(StandardCharsets.UTF_8);

    // 将数据写入文件
    writeBytes = writeToChannel(buffer, channel, dataByte);

    dataByte = null;

    return writeBytes;
  }

  /**
   * 将数据写入文件操作
   *
   * @param buffer 数据缓冲区
   * @param channel 文件通道
   * @param dataByte 数据内容
   * @return 写入字节的长度
   * @throws IOException
   */
  public static int writeToChannel(ByteBuffer buffer, FileChannel channel, byte[] dataByte) {
    int wirteBytes = 0;

    // 循环将数据写入缓冲区看
    int startPos = 0;

    // 计算数据长度信息
    int byteSpaceLength = CountLenth(buffer, dataByte.length, startPos);

    // 针对单次写入来说，需确保完整性
    Lock lock = new ReentrantLock();

    try {
      lock.lock();

      // 检查待写入的数据长度是否大于0
      while (byteSpaceLength > 0) {
        // 数据拷贝操作
        buffer.put(dataByte, startPos, byteSpaceLength);
        buffer.flip();
        // 将缓冲区中的数据写入文件中
        int length = channel.write(buffer);
        wirteBytes += length;
        // 进行压缩操作
        buffer.compact();
        // 重新计算开始度度
        startPos += length;

        // 再进行一次计算
        byteSpaceLength = CountLenth(buffer, dataByte.length, startPos);
      }
    } catch (IOException e) {
      e.printStackTrace();
      LOGGER.error("write data error:", e);
      throw new RuntimeException("write buffer to file ioExceptoin", e);
    } finally {
      lock.unlock();
    }

    if (buffer.position() != 0) {
      throw new RuntimeException("wirte data error position:" + buffer.position());
    }

    dataByte = null;

    return wirteBytes;
  }

  /**
   * 计算数据写入长度
   *
   * @param buffer buffer的数据
   * @param dataTotalLength 数据的长度
   * @param startPos 开始的位置
   * @return 计算写入的长度
   */
  private static int CountLenth(ByteBuffer buffer, int dataTotalLength, int startPos) {
    int byteSpaceLength = 0;
    // 数据长度
    int bufferSpaceLength = buffer.limit() - buffer.position();

    // 进行中间的写入长度计算
    if (dataTotalLength - startPos > bufferSpaceLength) {
      byteSpaceLength = bufferSpaceLength;
    }
    // 进行最后一次长度的计算
    else {
      byteSpaceLength = dataTotalLength - startPos;
    }

    return byteSpaceLength;
  }
}
