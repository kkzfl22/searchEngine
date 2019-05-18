package com.liujun.search.engine.collect.thread;

import com.liujun.search.common.io.CommonIOUtils;
import com.liujun.search.element.download.HttpsClientManager;
import com.liujun.search.engine.collect.constant.WebEntryEnum;
import com.liujun.search.engine.collect.flow.HtmlAnalyzeFLow;
import com.liujun.search.engine.collect.operation.docraw.DocRawWriteProc;
import com.liujun.search.engine.collect.operation.filequeue.FileQueue;
import com.liujun.search.engine.collect.operation.filequeue.FileQueueManager;
import com.liujun.search.common.constant.SymbolMsg;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 网页收集线程
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/29
 */
public class HtmCollectThread implements Runnable {

  /** 网页入口信息 */
  private WebEntryEnum entry;

  /** 当前是否运行的标识 */
  private AtomicBoolean runFlag = new AtomicBoolean(true);

  /** 当前任务是否运行结束 */
  private AtomicBoolean finishFlag = new AtomicBoolean(false);

  /** 网页连接对象 */
  private CloseableHttpClient client;

  public HtmCollectThread(WebEntryEnum entry) {
    this.entry = entry;
    // 添加公共的网页连接对象
    this.client = HttpsClientManager.getConnection();
  }

  @Override
  public void run() {
    // 设置线程的名称
    this.setThreadName();

    // 进行线程资源的初始化
    this.runThreadInit();

    // 将当前线程加入全局线程管理中
    RunCollectThreadManager.INTANACE.putThread(entry, this);

    // 1,从文件队列中获取地址信息
    FileQueue queue = FileQueueManager.INSTANCE.getFileQueue(entry);

    String urlAddress;

    while ((urlAddress = queue.get()) != null && runFlag.get()) {

      // 清理前后空白符
      urlAddress = urlAddress.trim();

      // 如果为空，跳过当前的处理
      if (StringUtils.isEmpty(urlAddress)) {
        continue;
      }

      // 进行网页的收集操作
      HtmlAnalyzeFLow.INSTANCE.downloadAndAnalyzeHtml(entry, urlAddress, client);
    }

    // 标识当前任务结束
    finishFlag.set(true);
  }

  public AtomicBoolean getRunFlag() {
    return runFlag;
  }

  /** 设置线程的名称 */
  private void setThreadName() {

    String oldName = Thread.currentThread().getName();

    StringBuilder outName = new StringBuilder();

    outName.append(oldName);
    outName.append(SymbolMsg.MINUS);
    outName.append(Thread.currentThread().getId());
    outName.append(SymbolMsg.MINUS);
    outName.append(entry.getFlag());

    Thread.currentThread().setName(outName.toString());
  }

  /** 进行线程运行资源的初始化 */
  private void runThreadInit() {

    // 进行首个根地址的放入操作
    FileQueueManager.INSTANCE.getFileQueue(entry).firstWriteEntry(entry);
    // 找开文件通道操作
    FileQueueManager.INSTANCE.getFileQueue(entry).openFileQueue();

    // 进行线程的资源初始化
    DocRawWriteProc.INSTANCE.threadInit();
  }

  /** 进特线程的资源靖理 */
  public void cleanResource() {
    // 关闭http的连接
    CommonIOUtils.close(client);
    // 清理线程资源
    DocRawWriteProc.INSTANCE.threadClean();
    // 保存当前变量指针的值
    FileQueueManager.INSTANCE.getFileQueue(entry).writeOffset();
    // 关闭当前文件通道
    FileQueueManager.INSTANCE.getFileQueue(entry).closeAll();
  }

  public AtomicBoolean getFinishFlag() {
    return finishFlag;
  }
}
