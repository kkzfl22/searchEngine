package com.liujun.search.engine.collect.operation;

import com.liujun.search.algorithm.boyerMoore.use.CharMatcherBMBadChars;
import com.liujun.search.common.constant.PathCfg;
import com.liujun.search.common.constant.SymbolMsg;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.common.io.IOUtils;
import com.liujun.search.engine.collect.constant.DocRawFindEnum;
import com.liujun.search.engine.collect.operation.docrawfind.DataConvert;
import com.liujun.search.engine.collect.operation.docrawfind.DataFindId;
import com.liujun.search.engine.collect.operation.docrawfind.FindDataEnd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * html原始网页的处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/04
 */
public class DocRawProc {

  /** 网页id与网页内容html的对应关系 */
  private static final String DOC_ID_FILE = "doc_raw.bin";

  /** url与id对应的关系的文件存储路径 */
  private static final String DOC_FILEPATH = PathCfg.BASEPATH + PathCfg.COLLEC_PATH + DOC_ID_FILE;

  /** 缓冲区大小，32K */
  private static final int BUFFER_SIZE = 1024 * 32;

  /** 流程信息 */
  private static final FlowServiceInf[] FLOW = new FlowServiceInf[3];

  static {
    FLOW[0] = DataFindId.INSTANCE;
    FLOW[1] = FindDataEnd.INSTANCE;
    FLOW[2] = DataConvert.INSTANCE;
  }

  public static final DocRawProc INSTANCE = new DocRawProc();

  /** 日志信息 */
  private Logger logger = LoggerFactory.getLogger(DocRawProc.class);

  /** 读取数据的对象 */
  private Writer write = null;

  /** 数据缓存对象 */
  private BufferedWriter bufferWrite = null;

  /** 缓冲区大小 */
  private ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);

  /** 打开写入流对象 */
  public void open() {
    try {
      write = new FileWriter(DOC_FILEPATH, true);
      bufferWrite = new BufferedWriter(write, BUFFER_SIZE);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("DocRawProc open FileNotFoundException", e);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("DocRawProc open IOException", e);
    }
  }

  /** 数据关闭对象 */
  public void close() {
    IOUtils.close(bufferWrite);
    IOUtils.close(write);
  }

  public void clean() {
    // 执行清理操作
    new File(DOC_FILEPATH).delete();
  }

  /**
   * 插入html数据的信息
   *
   * @param htmlCode html的内容信息
   */
  public void putHtml(long id, String htmlCode) {
    // 获取写入的数据信息
    String lineData = this.getLineData(id, htmlCode);
    try {
      bufferWrite.write(lineData);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("DocRawProc putHtml IOException", e);
    }
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

    FileInputStream input = null;
    FileChannel channel = null;

    try {
      input = new FileInputStream(DOC_FILEPATH);
      channel = input.getChannel();

      FlowServiceContext context = new FlowServiceContext();
      context.put(DocRawFindEnum.INPUT_FIND_ID.getKey(), id);

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
      IOUtils.close(channel);
      IOUtils.close(input);
    }

    return null;
  }
}
