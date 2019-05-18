package com.liujun.search.engine.collect.flow;

import com.liujun.search.algorithm.bloomFilter.HtmlContextFilter;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.CollectAnalyzeFlowKeyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 进行网页的内容的判重操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/17
 */
public class HtmlContextBoomFilter implements FlowServiceInf {

  /** 实例对象 */
  public static final HtmlContextBoomFilter INSTANCE = new HtmlContextBoomFilter();

  private Logger logger = LoggerFactory.getLogger(HtmlContextBoomFilter.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("collect context bloom filter start ");

    // 获取网页信息
    String htmlContext =
        context.getObject(CollectAnalyzeFlowKeyEnum.FLOW_DOWNLOAD_CONTEXT.getKey());

    boolean exists = HtmlContextFilter.INSTANCE.exists(htmlContext);

    logger.info("collect context bloom filter finish ,exists {} ", exists);

    // 如果当前已经存在，则退出
    if (exists) {
      return false;
    }

    // 将当前网页加入到布隆过滤器中
    HtmlContextFilter.INSTANCE.putData(htmlContext);

    return true;
  }
}
