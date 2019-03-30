package com.liujun.search.engine.collect.flow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.element.download.DownLoad;
import com.liujun.search.engine.collect.constant.CollectFlowKeyEnum;
import com.liujun.search.engine.collect.constant.WebEntryEnum;
import com.liujun.search.engine.collect.operation.filequeue.FileQueue;
import com.liujun.search.engine.collect.operation.filequeue.FileQueueManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 下载html网页
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/17
 */
public class DownLoadHtml implements FlowServiceInf {

  private Logger logger = LoggerFactory.getLogger(DownLoadHtml.class);

  /** 实例对象 */
  public static final DownLoadHtml INSTANCE = new DownLoadHtml();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("collect download html start ");

    long start = System.currentTimeMillis();
    String urlAddress = context.getObject(CollectFlowKeyEnum.FLOW_DOWNLOAD_ADDRESS.getKey());

    // 进行下载文件的操作
    String htmlContext = DownLoad.INSTANCE.downloadHtml(urlAddress);

    long endtime = System.currentTimeMillis();

    logger.info(
        "collect download html finish ,use time : {} , html length: {} ",
        (endtime - start),
        htmlContext.length());

    if (StringUtils.isNotEmpty(htmlContext)) {
      context.put(CollectFlowKeyEnum.FLOW_DOWNLOAD_CONTEXT.getKey(), htmlContext);

      return true;
    }

    return false;
  }
}
