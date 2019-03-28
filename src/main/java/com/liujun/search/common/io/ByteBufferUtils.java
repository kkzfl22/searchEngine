package com.liujun.search.common.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * bytebuffer的操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/26
 */
public class ByteBufferUtils {

  /**
   * 将数据写入到缓冲区中，
   *
   * <p>当缓冲区满了写入文件中
   *
   * <p>遍历结束了，数据都需要写入至文件中
   *
   * @param buffer 缓冲区信息
   * @param channel 通道信息
   * @param data 字符串数据内容
   */
  public static void wirteBuffOrChannel(ByteBuffer buffer, FileChannel channel, String data) {

    try {
      // 检查buffer是否还存在空间，如果不存在空间需要先写入磁盘
      if (buffer.limit() - buffer.position() == 0) {
        buffer.flip();
        channel.write(buffer);
        buffer.compact();
      }

      // 2,将当前数据转码为byte数据
      byte[] dataByte = data.getBytes(StandardCharsets.UTF_8);

      // 写入将数据写入文件
      writeByteOrChannel(buffer, channel, dataByte);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 将数据写入文件操作
   *
   * @param buffer
   * @param channel
   * @param dataByte
   * @throws IOException
   */
  public static void writeByteOrChannel(ByteBuffer buffer, FileChannel channel, byte[] dataByte)
      throws IOException {
    // 循环将数据写入缓冲区看
    int startPos = 0;

    // 计算数据长度信息
    int byteSpaceLength = CountLenth(buffer, dataByte.length, startPos);

    // 检查待写入的数据长度是否大于0
    while (byteSpaceLength > 0) {

      // 数据拷贝操作
      buffer.put(dataByte, startPos, byteSpaceLength);
      buffer.flip();
      // 将缓冲区中的数据写入文件中
      int length = channel.write(buffer);
      // 进行压缩操作
      buffer.compact();
      // 重新计算开始度度
      startPos += length;

      // 再进行一次计算
      byteSpaceLength = CountLenth(buffer, dataByte.length, startPos);
    }
  }

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
