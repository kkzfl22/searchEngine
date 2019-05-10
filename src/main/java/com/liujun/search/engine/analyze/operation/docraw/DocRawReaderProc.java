package com.liujun.search.engine.analyze.operation.docraw;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.common.io.LocalIOUtils;
import com.liujun.search.engine.analyze.constant.DocrawReaderEnum;
import com.liujun.search.engine.analyze.operation.docraw.docrawReader.*;
import com.liujun.search.engine.analyze.pojo.RawDataLine;
import com.liujun.search.engine.collect.operation.docraw.DocRawFileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.List;

/**
 * docraw用来进行原始网页的读取操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/04
 */
public class DocRawReaderProc extends DocRawFileManager {

  /** 日志信息 */
  private Logger logger = LoggerFactory.getLogger(DocRawReaderProc.class);

  /** 流程信息 */
  private static final List<FlowServiceInf> FLOW = new ArrayList<>();

  private static FlowServiceContext FlowCon = new FlowServiceContext();

  /** 用来标识当前所有文件是否读取完成 */
  private boolean finish = false;

  static {
    // 进行读取操作
    FLOW.add(ReaderInit.INSTANCE);
    // 进行文件切换
    FLOW.add(ReaderSwitchCheck.INSTANCE);
    // 进行上一次数据余下的读取操作
    FLOW.add(LineLastMatcher.INSTANCE);
    // 行结束符查找
    FLOW.add(LineEndMatcher.INSTANCE);
    // 结束符检查
    FLOW.add(FinishCheck.INSTANCE);

    // 进行流程的初始化操作
    InitFlow();
  }

  public static final DocRawReaderProc INSTANCE = new DocRawReaderProc();

  /** 进行流程的初始化操作 */
  public static void InitFlow() {
    // 扫描文件夹，得到文件列表
    String[] dirList = FileList();

    FlowCon.put(DocrawReaderEnum.DOCRAW_INPUT_FILE_LIST.getKey(), dirList);
    FlowCon.put(DocrawReaderEnum.DOCRAW_INPUT_BASE_PATH.getKey(), GetPath());
    FlowCon.put(DocrawReaderEnum.DOCRAW_INPUT_FILE_INDEX.getKey(), 0);
    FlowCon.put(DocrawReaderEnum.DOCRAW_INPUT_FILE_POSITION.getKey(), 0L);
    // 分配缓冲区
    FlowCon.put(
        DocrawReaderEnum.DOCRAW_INPUT_READER_BUFFER.getKey(),
        ByteBuffer.allocateDirect(BUFFER_SIZE));
  }

  /**
   * 检查当前读取是否已经结束
   *
   * @return true 已经结束 false 未结束
   */
  public boolean checkFinish() {
    return finish;
  }

  /**
   * 获取html的内容信息,根据网页的id
   *
   * @param limit 每次读取的条数
   * @return 获取的网页内容信息
   */
  public List<RawDataLine> reader(int limit) {

    FlowCon.remove(DocrawReaderEnum.DOCRAW_INOUTPUT_RESULT_LIST.getKey());
    // 用于封装返回对象
    FlowCon.put(DocrawReaderEnum.DOCRAW_INOUTPUT_RESULT_LIST.getKey(), new ArrayList<>());
    // 输入的限制
    FlowCon.put(DocrawReaderEnum.DOCRAW_INPUT_PAGELIMIT.getKey(), limit);

    Boolean returnFlag = false;

    // 标识当前读取完成
    Boolean outFinish = false;

    try {
      // 如果当前未完成，则继续读取
      while (!finish) {
        for (FlowServiceInf flowService : FLOW) {
          if (!flowService.runFlow(FlowCon)) {
            break;
          }
          // 检查是否存在退出标识
          returnFlag = FlowCon.getObject(DocrawReaderEnum.DOCRAW_OUTPUT_RETURN_FLAG.getKey());

          if (null != returnFlag && returnFlag) {
            this.clean(FlowCon);

            outFinish = FlowCon.getObject(DocrawReaderEnum.DOCRAW_OUTPUT_FINISH_FLAG.getKey());

            // 如果文件已经读取完成，则将标识当前已经读取完成
            if (null != outFinish && outFinish) {
              finish = true;
            }

            return FlowCon.getObject(DocrawReaderEnum.DOCRAW_INOUTPUT_RESULT_LIST.getKey());
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("docraw reader exception", e);
    }

    List<RawDataLine> result =
        FlowCon.getObject(DocrawReaderEnum.DOCRAW_INOUTPUT_RESULT_LIST.getKey());

    return result;
  }

  /**
   * 进行流程的清理操作
   *
   * @param context 流程上下文对象
   */
  private void clean(FlowServiceContext context) {
    context.remove(DocrawReaderEnum.DOCRAW_OUTPUT_RETURN_FLAG.getKey());
  }

  /** 进行关闭操读取操作 */
  private void closeReader() {
    InputStream input = FlowCon.getObject(DocrawReaderEnum.DOCRAW_PROC_INPUT_STREAM.getKey());
    Channel channel = FlowCon.getObject(DocrawReaderEnum.DOCRAW_PROC_INPUT_CHANNEL.getKey());
    LocalIOUtils.close(channel);
    LocalIOUtils.close(input);
  }

  public void cleanAll() {
    this.closeReader();
    FlowCon.cleanParam();
  }

  public void setFinish(boolean runFlag) {
    this.finish = runFlag;
  }
}
