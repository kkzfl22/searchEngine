package com.liujun.search.engine.collect.flow;

import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.FileQueue;
import com.liujun.search.engine.collect.constant.WebEntryEnum;

import java.util.List;

/**
 * 网页分析流程
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/18
 */
public class HtmlAnalyzeFLow {

  /** 网页分析流程 */
  public static final HtmlAnalyzeFLow INSTANCE = new HtmlAnalyzeFLow();

  public static final FlowServiceInf[] FLOW = new FlowServiceInf[4];

  static {
    // 2，下载网页信息
    FLOW[0] = DownLoadHtml.INSTANCE;
    // 3,进行网页分析，抽取出链接地址信息
    FLOW[1] = HtmlContextAnalyze.INSTANCE;
  }

  /**
   * 网页分析流程
   *
   * @param readNum 读取的网页下载数量
   * @param entry 网页搜索入口信息
   * @return true 运行成功
   */
  public boolean htmlAnalyze(int readNum, WebEntryEnum entry) {

    return true;
  }

  /**
   * 获取网页的链接信息
   *
   * @param readNum
   * @return
   */
  public List<String> getTopUrl(int readNum, WebEntryEnum entry) {
    FileQueue filQueue = FileQueue.GetQueue(entry.getFlag());

    return filQueue.readData(0, readNum);
  }
}
