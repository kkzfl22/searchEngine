package com.liujun.search.engine.collect.operation.docraw;

import com.liujun.search.common.io.ByteBufferUtils;
import com.liujun.search.utilscode.io.constant.SymbolMsg;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.common.io.LocalIOUtils;
import com.liujun.search.engine.collect.constant.DocRawFindEnum;
import com.liujun.search.engine.collect.operation.docrawfind.DataConvert;
import com.liujun.search.engine.collect.operation.docrawfind.DataFindId;
import com.liujun.search.engine.collect.operation.docrawfind.FindDataEnd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/**
 * html原始网页的处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/04
 */
public class DocRawProc extends DocRawFileStreamManager {

  /** 缓冲区大小，32K */
  private static final int BUFFER_SIZE = 1024 * 32;

  /** 流程信息 */
  private static final FlowServiceInf[] FLOW = new FlowServiceInf[3];

  static {
    // 首先进行数据的id的查找
    FLOW[0] = DataFindId.INSTANCE;
    // 当查找到id后，再进行结束符的查找
    FLOW[1] = FindDataEnd.INSTANCE;
    // 最后进行数据的转换操作,即提取所需要的数据
    FLOW[2] = DataConvert.INSTANCE;
  }

  public static final DocRawProc INSTANCE = new DocRawProc();

  /** 日志信息 */
  private Logger logger = LoggerFactory.getLogger(DocRawProc.class);

  /** 线程局部变量 */
  private ThreadLocal<ByteBuffer> threadLocal = new ThreadLocal<>();

  public DocRawProc() {
    // 检查并放入缓冲区
    threadLocal.set(ByteBuffer.allocate(BUFFER_SIZE));

    // 进行首次的初始化操作
    this.initReadIndex();
  }

  /** 清理所有文件 */
  public void cleanAll() {
    // 执行清理操作
    super.close();

    // 进行文件的删除操作
    new File(super.getPathFile()).delete();

    // 查找文件下所有的文件
    String[] fileList = this.fileList();

    if (null != fileList && fileList.length > 1) {
      String pathInfo = super.getPath();

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

    // 将数据写入通道中
    ByteBufferUtils.wirteBuffOrChannel(buffer, channel, lineData);

    // 写入完成更新文件大小
    super.fileSizeAdd(lineData.length());
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

  /**
   * 获取html的内容信息
   *
   * @param id 网的id信息
   * @return 获取的网页内容信息
   */
  public String getHtml(long id) {

    ByteBuffer readBuffer = ByteBuffer.allocate(2048);
    FlowServiceContext context = new FlowServiceContext();

    context.put(DocRawFindEnum.INPUT_FIND_ID.getKey(), id);

    // 扫描文件夹，得到文件列表
    String[] dirList = this.fileList();

    String dataContext = null;
    for (String dataFile : dirList) {
      // 进行文件的查找遍历操作
      dataContext = this.fileMatcher(readBuffer, context, dataFile);

      // 当查找到文件后，则退出文件遍历
      if (null != dataContext) {
        return dataContext;
      }
    }

    return null;
  }

  /**
   * 进行文件中在字符匹配操作
   *
   * @param readBuffer 读取的字符缓冲区
   * @param context 搜索处理的上下文信息
   * @param fileName 文件名
   * @return 匹配的字符串信息 不为空，则为匹配的内容，为空，则说明当前文件中未找到
   */
  public String fileMatcher(ByteBuffer readBuffer, FlowServiceContext context, String fileName) {

    FileInputStream input = null;
    FileChannel channel = null;

    try {

      String loopFile = super.getPath() + File.separator + fileName;

      input = new FileInputStream(loopFile);
      channel = input.getChannel();

      int readSize = 0;
      int startPos = readBuffer.position();

      while ((readSize = channel.read(readBuffer)) > 0) {

        readBuffer.flip();
        byte[] bufferBytes = new byte[readSize];
        readBuffer.get(bufferBytes, startPos, readSize);

        context.put(DocRawFindEnum.INPUT_SRC_CHARS.getKey(), bufferBytes);

        for (FlowServiceInf flowitem : FLOW) {
          boolean continueFlag = flowitem.runFlow(context);

          if (!continueFlag) {
            break;
          }
        }

        context.remove(DocRawFindEnum.INPUT_SRC_CHARS.getKey());

        // 在每次操作完成后，都需要进行清理buffer对象
        readBuffer.clear();

        // 检查当前是否已经结束
        if (null != context.getObject(DocRawFindEnum.OUT_FIND_END_FLAG.getKey())) {
          return context.getObject(DocRawFindEnum.OUT_FIND_DATA_CONTEXT.getKey());
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("DocRawProc getHtml FileNotFoundException ", e);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("DocRawProc getHtml IOException ", e);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("DocRawProc getHtml Exception ", e);
    } finally {
      LocalIOUtils.close(channel);
      LocalIOUtils.close(input);
    }

    return null;
  }

  /**
   * 获取文件列表，并且需要按名称默认排序
   *
   * @return 文件信息
   */
  private String[] fileList() {
    File dataFile = new File(super.getPath());

    String[] list = dataFile.list();

    if (list.length > 1) {
      Arrays.sort(list);
    }

    return list;
  }
}
