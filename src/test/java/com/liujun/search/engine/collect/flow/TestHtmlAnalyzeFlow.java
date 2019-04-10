package com.liujun.search.engine.collect.flow;

import com.liujun.search.common.io.LocalIOUtils;
import com.liujun.search.element.download.HttpsClientManager;
import com.liujun.search.engine.collect.constant.WebEntryEnum;
import com.liujun.search.engine.collect.operation.DocIdproc;
import com.liujun.search.engine.collect.operation.docraw.DocRawWriteProc;
import com.liujun.search.engine.collect.operation.filequeue.FileQueue;
import com.liujun.search.engine.collect.operation.filequeue.FileQueueManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.*;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/18
 */
public class TestHtmlAnalyzeFlow {

  private static CloseableHttpClient Client;

  private static final int MAX_SIZE = 100;

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
    this.testDownloadAnalyzeComm(WebEntryEnum.SOHO, 1000);
  }

  /** 测试网页分析 */
  @Test
  public void testHtmlAnalyzeQQ() {
    this.testDownloadAnalyzeComm(WebEntryEnum.QQ, MAX_SIZE);
  }

  private void testDownloadAnalyzeComm(WebEntryEnum entry, int maxSize) {
    // 进行首个根地址的放入操作
    FileQueueManager.INSTANCE.getFileQueue(entry).firstWriteEntry(entry);
    // 找开文件通道操作
    FileQueueManager.INSTANCE.getFileQueue(entry).openFileQueue();

    DocIdproc.INSTANCE.openFile();
    DocRawWriteProc.INSTANCE.openFile();

    FileQueue queue = FileQueueManager.INSTANCE.getFileQueue(entry);

    String urlAddress = null;

    int index = 0;

    while (index < maxSize) {

      urlAddress = queue.get();

      // 清理前后空白符
      urlAddress = urlAddress.trim();

      // 如果为空，跳过当前的处理
      if (StringUtils.isEmpty(urlAddress)) {
        continue;
      }

      // 进行网页的收集操作
      HtmlAnalyzeFLow.INSTANCE.downloadAndAnalyzeHtml(entry, urlAddress, Client);

      index++;
    }

    FileQueueManager.INSTANCE.getFileQueue(entry).writeOffset();
  }

  /** 测试网页分析 */
  @Test
  public void testHtmlAnalyze163() {
    this.testDownloadAnalyzeComm(WebEntryEnum.WY163, MAX_SIZE);
  }

  /** 测试网页分析 */
  @Test
  public void testHtmlAnalyzeSina() {
    this.testDownloadAnalyzeComm(WebEntryEnum.SINA, MAX_SIZE);
  }

  @AfterClass
  public static void clean() {
    LocalIOUtils.close(Client);
  }
}
