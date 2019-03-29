package com.liujun.search.engine.collect.thread;

import com.liujun.search.engine.collect.constant.WebEntryEnum;
import com.liujun.search.engine.collect.flow.HtmlAnalyzeFLow;

/**
 * 网页收集线程
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/29
 */
public class HtmCollectThread implements Runnable {

  private WebEntryEnum entry;

  public HtmCollectThread(WebEntryEnum entry) {
    this.entry = entry;
  }

  @Override
  public void run() {
    // 进行网页的收集操作
    HtmlAnalyzeFLow.INSTANCE.downloadAndAnalyzeHtml(entry);
  }
}
