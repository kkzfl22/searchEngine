package com.liujun.search.engine.collect.flow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.CollectFlowKeyEnum;
import com.liujun.search.engine.collect.constant.WebEntryEnum;
import com.liujun.search.engine.collect.operation.filequeue.FileQueue;
import com.liujun.search.engine.collect.operation.filequeue.FileQueueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * 网页分析流程
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/18
 */
public class HtmlAnalyzeFLow {

  /** 日志 */
  private Logger logger = LoggerFactory.getLogger(HtmlAnalyzeFLow.class);

  /** 网页分析流程 */
  public static final HtmlAnalyzeFLow INSTANCE = new HtmlAnalyzeFLow();

  public static final FlowServiceInf[] FLOW = new FlowServiceInf[2];

  static {
    // 2，下载网页信息
    FLOW[0] = DownLoadHtml.INSTANCE;
    // 3,进行网页分析，抽取出链接地址信息
    FLOW[1] = HtmlContextAnalyze.INSTANCE;
    // 4,将网页链接加入文件队列中
    FLOW[2] = FileQueueAddAddress.INSTANCE;
  }

  /**
   * 网页下载分析流程,每调用一次进行一个网页链接的下载操作
   *
   * @param entry 网页搜索入口信息
   * @return 返回下载的网页链接地址信息
   */
  public Set<String> downloadAndAnalyzeHtml(WebEntryEnum entry) {

    FlowServiceContext context = new FlowServiceContext();

    try {
      // 1,进行初始化操作
      init(entry);

      // 1,从文件队列中获取地址信息
      FileQueue queue = FileQueueManager.INSTANCE.getFileQueue(entry);
      String urlAddress = null;

      // 放入入口信息
      context.put(CollectFlowKeyEnum.WEB_ENTRY.getKey(), entry);

      while ((urlAddress = queue.get()) != null) {

        // 放入地址信息
        context.put(CollectFlowKeyEnum.FLOW_DOWNLOAD_ADDRESS.getKey(), urlAddress);

        // 进行任务流程执行
        for (FlowServiceInf flowService : FLOW) {
          flowService.runFlow(context);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      logger.error("HtmlAnalyzeFLow downloadAndAnalyzeHtml Exception", e);
    }

    return context.getObject(CollectFlowKeyEnum.FLOW_CONTEXT_HREF_LIST.getKey());
  }

  /**
   * 进行初始化操作
   *
   * @param entry
   */
  private void init(WebEntryEnum entry) {
    // 进行首个根地址的放入操作
    FileQueueManager.INSTANCE.getFileQueue(entry).firstWriteEntry(entry);
  }
}
