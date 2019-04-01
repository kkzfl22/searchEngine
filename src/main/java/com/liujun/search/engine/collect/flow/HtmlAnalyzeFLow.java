package com.liujun.search.engine.collect.flow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.common.number.NumberLoopSeq;
import com.liujun.search.engine.collect.constant.CollectFlowKeyEnum;
import com.liujun.search.engine.collect.constant.WebEntryEnum;
import com.liujun.search.engine.collect.operation.DocIdproc;
import com.liujun.search.engine.collect.operation.docraw.DocRawProc;
import com.liujun.search.engine.collect.operation.filequeue.FileQueue;
import com.liujun.search.engine.collect.operation.filequeue.FileQueueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  /** 流程操作 */
  private static final FlowServiceInf[] FLOW = new FlowServiceInf[7];

  static {
    // 1，下载网页操作
    FLOW[0] = DownLoadHtml.INSTANCE;
    // 2,进行网页判重操作
    FLOW[1] = HtmlBoomFilter.INSTANCE;
    // 3,分配网页的id操作
    FLOW[2] = HtmlNumberId.INSTANCE;
    // 4,将网页与网页id存储到doc_id.bin文件中
    FLOW[3] = HtmlDocIdToFile.INSTANCE;
    // 5，将网页存储到doc_raw.bin文件中
    FLOW[4] = HtmlDocrawToFile.INSTANCE;
    // 6,进行网页分析，抽取出链接地址信息
    FLOW[5] = HtmlContextAnalyze.INSTANCE;
    // 7,将网页链接加入文件队列中
    FLOW[6] = FileQueueAddAddress.INSTANCE;
  }

  /**
   * 网页下载分析流程,每调用一次进行一个网页链接的下载操作
   *
   * @param entry 网页搜索入口信息
   * @return 返回下载的网页链接地址信息
   */
  public void downloadAndAnalyzeHtml(WebEntryEnum entry) {

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

        logger.info("collect url start :" + urlAddress);

        // 放入地址信息
        context.put(CollectFlowKeyEnum.FLOW_DOWNLOAD_ADDRESS.getKey(), urlAddress);

        // 进行任务流程执行
        for (FlowServiceInf flowService : FLOW) {
          boolean runFlag = flowService.runFlow(context);

          // 如果返回false说明当前流程需要停止，进行下一个
          if (!runFlag) {
            break;
          }
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      logger.error("HtmlAnalyzeFLow downloadAndAnalyzeHtml Exception", e);
    }
  }

  /**
   * 进行初始化操作
   *
   * @param entry
   */
  private void init(WebEntryEnum entry) {
    // 进行首个根地址的放入操作
    FileQueueManager.INSTANCE.getFileQueue(entry).firstWriteEntry(entry);
    // 找开文件通道操作
    FileQueueManager.INSTANCE.getFileQueue(entry).openFileQueue();

    // 进行当前线程相关的初始化操作
    DocRawProc.INSTANCE.threadInit();

    //读取上一次的最后一个id
    long lastId  = DocIdproc.INSTANCE.getLastHrefId();
    //设置序列号的开始值为上一次的
    NumberLoopSeq.getNewInstance().start(lastId);
  }
}
