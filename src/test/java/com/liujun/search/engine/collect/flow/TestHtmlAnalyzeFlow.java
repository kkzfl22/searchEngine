package com.liujun.search.engine.collect.flow;

import com.liujun.search.common.io.LocalIOUtils;
import com.liujun.search.element.download.HttpsClientManager;
import com.liujun.search.engine.collect.constant.WebEntryEnum;
import com.liujun.search.engine.collect.operation.DocIdproc;
import com.liujun.search.engine.collect.operation.docraw.DocRawWriteProc;
import com.liujun.search.engine.collect.operation.filequeue.FileQueueManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.*;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/18
 */
public class TestHtmlAnalyzeFlow {

  private static CloseableHttpClient Client;

  @BeforeClass
  public static void init() {
    Client = HttpsClientManager.getConnection();
  }

  @Before
  public void getHttpClient() {
    // 进行线程的资源初始化
    DocRawWriteProc.INSTANCE.threadInit();
  }

  /** 测试网页分析 */
  @Test
  public void testHtmlAnalyze() {
    this.testDownloadAnalyzeComm(WebEntryEnum.SOHO);
  }

  /** 测试网页分析 */
  @Test
  public void testHtmlAnalyzeQQ() {
    this.testDownloadAnalyzeComm(WebEntryEnum.QQ);
  }

  private void testDownloadAnalyzeComm(WebEntryEnum entry) {
    // 进行首个根地址的放入操作
    FileQueueManager.INSTANCE.getFileQueue(entry).firstWriteEntry(entry);
    // 找开文件通道操作
    FileQueueManager.INSTANCE.getFileQueue(entry).openFileQueue();

    DocIdproc.INSTANCE.openFile();
    DocRawWriteProc.INSTANCE.openFile();

    String url = FileQueueManager.INSTANCE.getFileQueue(entry).get();
    // 2，执行下载
    HtmlAnalyzeFLow.INSTANCE.downloadAndAnalyzeHtml(entry, url, this.Client);

    FileQueueManager.INSTANCE.getFileQueue(entry).writeOffset();
  }

  /** 测试网页分析 */
  @Test
  public void testHtmlAnalyze163() {
    this.testDownloadAnalyzeComm(WebEntryEnum.WY163);
  }

  /** 测试网页分析 */
  @Test
  public void testHtmlAnalyzeSina() {
    this.testDownloadAnalyzeComm(WebEntryEnum.SINA);
  }

  @AfterClass
  public static void clean() {
    LocalIOUtils.close(Client);
  }
}
