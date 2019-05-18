package com.liujun.search.engine.collect.flow;

import com.liujun.search.algorithm.bloomFilter.HtmlUrlFilter;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.CollectAnalyzeFlowKeyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 进行网页的URL的判重操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/17
 */
public class HtmlUrlBoomFilter implements FlowServiceInf {

  /** 实例对象 */
  public static final HtmlUrlBoomFilter INSTANCE = new HtmlUrlBoomFilter();

  private Logger logger = LoggerFactory.getLogger(HtmlUrlBoomFilter.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("collect url bloom filter start ");

    // 网页的地址
    String urlAddress = context.getObject(CollectAnalyzeFlowKeyEnum.FLOW_DOWNLOAD_ADDRESS.getKey());

    boolean exists = HtmlUrlFilter.INSTANCE.exists(urlAddress);

    logger.info("collect url bloom filter finish ,exists {} ", exists);

    // 如果当前已经存在，则退出
    if (exists) {
      return false;
    }

    // 将当前网页加入到布隆过滤器中
    HtmlUrlFilter.INSTANCE.putData(urlAddress);
    return true;
  }
}
