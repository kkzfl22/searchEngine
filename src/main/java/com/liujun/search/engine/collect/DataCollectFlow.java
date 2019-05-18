package com.liujun.search.engine.collect;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.start.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/28
 */
public class DataCollectFlow {

  /** 任务信息 */
  private static final List<FlowServiceInf> FLOW = new ArrayList<>();

  /** 日志数据 */
  private  Logger logger = LoggerFactory.getLogger(DataCollectFlow.class);

  static {
    // 读取最后的文件序列
    FLOW.add(ReadLastHtmlSeq.INSTANCE);
    // 加过布隆过滤器
    FLOW.add(LoaderBloomFilter.INSTANCE);
    // 找开doc_id文件
    FLOW.add(OpenDocIdFile.INSTANCE);
    // 找开doc_raw文件
    FLOW.add(OpenDocRawFile.INSTANCE);
    // 加载停止流程处理器
    FLOW.add(ShutdownCallBack.INSTANCE);
    // 启动线程任务收集器
    FLOW.add(StartDataCollectThread.INSTANCE);
  }

  public static final DataCollectFlow INSTANCE = new DataCollectFlow();

  public void runFlow() {

    FlowServiceContext contextFlow = new FlowServiceContext();
    try {
      for (FlowServiceInf flowItem : FLOW) {
        flowItem.runFlow(contextFlow);
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("DataCollect Exception", e);
    }
  }
}
