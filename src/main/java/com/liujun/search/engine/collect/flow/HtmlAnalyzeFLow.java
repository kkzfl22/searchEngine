package com.liujun.search.engine.collect.flow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.common.number.NumberLoopSeq;
import com.liujun.search.engine.collect.constant.CollectAnalyzeFlowKeyEnum;
import com.liujun.search.engine.collect.constant.WebEntryEnum;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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
  private static final List<FlowServiceInf> FLOW = new ArrayList<>(8);

  static {
    // 1，下载网页操作
    FLOW.add(DownLoadHtml.INSTANCE);
    //2, 检查当前是否为网页,如果不为网页，则跳过

    // 2,进行网页判重操作
    FLOW.add(HtmlBoomFilter.INSTANCE);
    // 3,分配网页的id操作
    FLOW.add(HtmlNumberId.INSTANCE);
    // 4,将网页与网页id存储到doc_id.bin文件中
    FLOW.add(HtmlDocIdToFile.INSTANCE);
    // 5，将网页存储到doc_raw.bin文件中
    FLOW.add(HtmlDocrawToFile.INSTANCE);
    // 6,进行网页分析，抽取出链接地址信息
    FLOW.add(HtmlContextAnalyze.INSTANCE);
    // 7,将网页链接加入文件队列中
    FLOW.add(FileQueueAddAddress.INSTANCE);
  }

  /**
   * 网页下载分析流程,每调用一次进行一个网页链接的下载操作
   *
   * @param entry 网页搜索入口信息
   * @param urlAddress 网页地址信息
   * @param httpclient 网页连接信息
   * @return 返回下载的网页链接地址信息
   */
  public void downloadAndAnalyzeHtml(
      WebEntryEnum entry, String urlAddress, CloseableHttpClient httpclient) {

    logger.info("collect url start :" + urlAddress);

    long startTime = System.currentTimeMillis();

    FlowServiceContext context = new FlowServiceContext();

    try {
      // 放入入口信息
      context.put(CollectAnalyzeFlowKeyEnum.WEB_ENTRY.getKey(), entry);
      // 放入网页 连接信息
      context.put(CollectAnalyzeFlowKeyEnum.FLOW_INPUT_HTTP_CONN.getKey(), httpclient);
      // 放入地址信息
      context.put(CollectAnalyzeFlowKeyEnum.FLOW_DOWNLOAD_ADDRESS.getKey(), urlAddress);

      // 进行任务流程执行
      for (FlowServiceInf flowService : FLOW) {
        boolean runFlag = flowService.runFlow(context);

        // 如果返回false说明当前流程需要停止，进行下一个
        if (!runFlag) {
          break;
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      logger.error("HtmlAnalyzeFLow downloadAndAnalyzeHtml Exception", e);
    } finally {
      // 进行清理操作
      context.cleanParam();
    }

    long endTime = System.currentTimeMillis();

    logger.info(
        "collect url finish :" + urlAddress + " ,use time ({}) millsecond", (endTime - startTime));
  }
}
