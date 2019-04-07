package com.liujun.search.engine.collect.flow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.element.download.HttpsHtmlDownloadImpl;
import com.liujun.search.engine.collect.constant.CollectAnalyzeFlowKeyEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
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
    String urlAddress = context.getObject(CollectAnalyzeFlowKeyEnum.FLOW_DOWNLOAD_ADDRESS.getKey());
    CloseableHttpClient client =
        context.getObject(CollectAnalyzeFlowKeyEnum.FLOW_INPUT_HTTP_CONN.getKey());

    // 进行下载文件的操作
    String htmlContext = HttpsHtmlDownloadImpl.INSTNACE.downloadHtml(urlAddress, client);

    long endtime = System.currentTimeMillis();

    if (StringUtils.isNotEmpty(htmlContext)) {

      logger.info(
          "collect download html finish ,use time : {} , html length: {} ",
          (endtime - start),
          htmlContext.length());

      // 将内容为字符数组
      context.put(CollectAnalyzeFlowKeyEnum.FLOW_DOWNLOAD_CONTEXT.getKey(), htmlContext);
      context.put(
          CollectAnalyzeFlowKeyEnum.FLOW_DOWNLOAD_CONTEXT_ARRAY.getKey(),
          htmlContext.toCharArray());

      return true;
    }

    logger.info(
        "collect download html finish ,use time : {} , html length: {} ", (endtime - start), 0);

    return false;
  }
}
