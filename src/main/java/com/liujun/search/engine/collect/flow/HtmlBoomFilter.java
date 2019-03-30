package com.liujun.search.engine.collect.flow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.element.download.DownLoad;
import com.liujun.search.engine.collect.constant.CollectFlowKeyEnum;
import com.liujun.search.engine.collect.operation.BloomFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 进行网页的判重操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/17
 */
public class HtmlBoomFilter implements FlowServiceInf {

  /** 实例对象 */
  public static final HtmlBoomFilter INSTANCE = new HtmlBoomFilter();

  private Logger logger = LoggerFactory.getLogger(HtmlBoomFilter.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("collect bloom filter start ");

    // 获取网页信息
    String htmlContext = context.getObject(CollectFlowKeyEnum.FLOW_DOWNLOAD_CONTEXT.getKey());

    boolean exists = BloomFilter.INSTANCE.exists(htmlContext);

    logger.info("collect bloom filter finish ,exists {} ", exists);

    // 如果当前已经存在，则退出
    if (exists) {
      return false;
    }

    // 将当前网页加入到布隆过滤器中
    BloomFilter.INSTANCE.putData(htmlContext);

    return true;
  }
}
