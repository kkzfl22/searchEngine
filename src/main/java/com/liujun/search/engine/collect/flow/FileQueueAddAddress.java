package com.liujun.search.engine.collect.flow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.element.download.DownLoad;
import com.liujun.search.engine.collect.constant.CollectFlowKeyEnum;
import com.liujun.search.engine.collect.constant.WebEntryEnum;
import com.liujun.search.engine.collect.operation.filequeue.FileQueue;
import com.liujun.search.engine.collect.operation.filequeue.FileQueueManager;
import com.liujun.search.engine.collect.operation.html.HtmlHrefAnalyze;

import java.util.Set;

/**
 * 将网页中的链接存入文件队列中
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/17
 */
public class FileQueueAddAddress implements FlowServiceInf {

  /** 实例对象 */
  public static final FileQueueAddAddress INSTANCE = new FileQueueAddAddress();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    WebEntryEnum entry = context.getObject(CollectFlowKeyEnum.WEB_ENTRY.getKey());

    FileQueue fileQueue = FileQueueManager.INSTANCE.getFileQueue(entry);

    // 进行网页链接的集合操作
    Set<String> hrefValue = context.getObject(CollectFlowKeyEnum.FLOW_CONTEXT_HREF_LIST.getKey());

    // 将数据入入到队列中
    fileQueue.put(hrefValue);

    return true;
  }
}
