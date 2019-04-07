package com.liujun.search.engine.collect.start;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.shutdown.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 晕行关闭流程的调用流程
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/06
 */
public class ShutdownCallBack implements FlowServiceInf {

  public static final ShutdownCallBack INSTANCE = new ShutdownCallBack();

  /** 日志信息 */
  private Logger logger = LoggerFactory.getLogger(ShutdownCallBack.class);

  private static final List<FlowServiceInf> SHUTDOWNFLOW = new ArrayList<>();

  static {
    // 1,停止任务队列
    SHUTDOWNFLOW.add(StopFileQueue.INSTANCE);
    // 2,检查所有任务都已经停止
    SHUTDOWNFLOW.add(TaskRunFinishCheck.INSTANCE);
    // 2 进行关闭资源操作
    SHUTDOWNFLOW.add(ThreadCloseResource.INSTANCE);
    // 3.保存布隆过滤器中的数据
    SHUTDOWNFLOW.add(SaveBloomFilter.INSTANCE);
    // 4,关闭doc_id文件
    SHUTDOWNFLOW.add(CloseDocIdFile.INSTANCE);
    // 5,关闭docraw文件
    SHUTDOWNFLOW.add(CloseDocrawFile.INSTANCE);
    // 最后等待5秒钟再关系系统
    SHUTDOWNFLOW.add(WaitCloseDelay.INSTANCE);
  }

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("collect start shutdown flow loader start..");

    // 接收关闭信号
    Runtime.getRuntime()
        .addShutdownHook(
            new Thread(
                () -> {
                  FlowServiceContext contextShutdown = new FlowServiceContext();
                  try {
                    for (FlowServiceInf flowItem : SHUTDOWNFLOW) {
                      flowItem.runFlow(contextShutdown);
                    }
                  } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("ShutdownCallBack Exception", e);
                  }
                }));

    logger.info("collect start shutdown flow loader finish ");

    return true;
  }
}
