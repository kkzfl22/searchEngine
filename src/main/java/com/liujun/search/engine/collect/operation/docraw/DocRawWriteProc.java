package com.liujun.search.engine.collect.operation.docraw;

import com.liujun.search.common.io.ByteBufferUtils;
import com.liujun.search.utilscode.io.constant.SymbolMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * docraw用来进行原始网页的写入磁盘操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/04
 */
public class DocRawWriteProc extends DocRawFileStreamManager {

  /** 缓冲区大小，32K */
  private static final int BUFFER_SIZE = 1024 * 32;

  public static final DocRawWriteProc INSTANCE = new DocRawWriteProc();

  /** 日志信息 */
  private Logger logger = LoggerFactory.getLogger(DocRawWriteProc.class);

  /** 线程局部变量 */
  private ThreadLocal<ByteBuffer> threadLocal = new ThreadLocal<>();

  public DocRawWriteProc() {
    // 进行首次的初始化操作
    this.initReadIndex();
  }

  /** 进行线程的初始化操作 */
  public void threadInit() {
    // 检查并放入缓冲区
    threadLocal.set(ByteBuffer.allocate(BUFFER_SIZE));
  }

  /** 进行线程本地资源的清理操作 */
  public void threadClean() {
    ByteBuffer buffer = threadLocal.get();
    buffer.clear();
    threadLocal.remove();
  }

  /** 清理所有文件 */
  public void cleanAll() {
    // 执行清理操作
    super.close();

    // 进行文件的删除操作
    new File(super.getPathFile()).delete();

    // 查找文件下所有的文件
    String[] fileList = FileList();

    if (null != fileList && fileList.length >= 1) {
      String pathInfo = GetPath();

      for (String file : fileList) {
        new File(pathInfo + File.separator + file).delete();
      }
    }
  }

  /**
   * 插入html数据的信息
   *
   * @param htmlCode html的内容信息
   */
  public void putHtml(long id, String htmlCode) {

    // 获取当前线程的缓冲区
    ByteBuffer buffer = threadLocal.get();

    // 1,检查当前文件大小,并切换文件
    super.fileSizeCheanAndSwitch();

    // 将数据转换为写入的数据格式
    String lineData = this.getLineData(id, htmlCode);

    // 获取文件通道
    FileChannel channel = super.getChannel();

    // 将数据写入文件通道中,统计需要以最终的写入大小为准
    int wirteBytes = ByteBufferUtils.wirteChannel(buffer, channel, lineData);

    // 写入完成更新文件大小
    super.fileSizeAdd(wirteBytes);
  }

  /**
   * 获取行数据的byte字符信息
   *
   * @return
   */
  public String getLineData(long id, String htmlContext) {
    StringBuilder outData = new StringBuilder();

    outData.append(id);
    outData.append(SymbolMsg.DATA_COLUMN);
    outData.append(htmlContext.length());
    outData.append(SymbolMsg.DATA_COLUMN);
    outData.append(htmlContext);
    outData.append(SymbolMsg.LINE_OVER);

    return outData.toString();
  }
}
