package com.liujun.search.engine.collect.operation.docraw;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.common.io.LocalIOUtils;
import com.liujun.search.engine.collect.constant.DocRawFindEnum;
import com.liujun.search.engine.collect.operation.docraw.docrawFind.DataConvert;
import com.liujun.search.engine.collect.operation.docraw.docrawFind.DataFindId;
import com.liujun.search.engine.collect.operation.docraw.docrawFind.FindDataEnd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * docraw用来进行原始网页的查找操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/04
 */
public class DocRawFindProc extends DocRawFileStreamManager {

  public static final DocRawFindProc INSTANCE = new DocRawFindProc();

  /** 日志信息 */
  private Logger logger = LoggerFactory.getLogger(DocRawFindProc.class);

  /** 流程信息 */
  private static final List<FlowServiceInf> FLOW = new ArrayList<>();

  static {
    // 首先进行数据的id的查找
    FLOW.add(DataFindId.INSTANCE);
    // 当查找到id后，再进行结束符的查找
    FLOW.add(FindDataEnd.INSTANCE);
    // 最后进行数据的转换操作,即提取所需要的数据
    FLOW.add(DataConvert.INSTANCE);
  }

  /**
   * 获取html的内容信息,根据网页的id
   *
   * @param id 网的id信息
   * @return 获取的网页内容信息
   */
  public String findContextById(long id) {

    ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);
    FlowServiceContext context = new FlowServiceContext();

    context.put(DocRawFindEnum.INPUT_FIND_ID.getKey(), id);

    // 扫描文件夹，得到文件列表
    String[] dirList = this.FileList();

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

      String loopFile = super.GetPath() + File.separator + fileName;

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
      logger.error("DocRawFindProc fileMatcher FileNotFoundException ", e);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("DocRawFindProc fileMatcher IOException ", e);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("DocRawFindProc fileMatcher Exception ", e);
    } finally {
      LocalIOUtils.close(channel);
      LocalIOUtils.close(input);
    }

    return null;
  }
}
