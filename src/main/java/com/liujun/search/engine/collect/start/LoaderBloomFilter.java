package com.liujun.search.engine.collect.start;

import com.liujun.search.algorithm.bloomFilter.HtmlContextFilter;
import com.liujun.search.algorithm.bloomFilter.HtmlUrlFilter;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.algorithm.bloomFilter.BloomFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加载布隆过滤器中的数据
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/06
 */
public class LoaderBloomFilter implements FlowServiceInf {

  public static final LoaderBloomFilter INSTANCE = new LoaderBloomFilter();

  /** 日志 */
  private Logger logger = LoggerFactory.getLogger(LoaderBloomFilter.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("collect start loader bloom filter start..");

    // 加载布隆过滤器的数据
    HtmlContextFilter.INSTANCE.loader();
    HtmlUrlFilter.INSTANCE.loader();

    logger.info("collect start loader bloom filter finish..");

    return true;
  }
}
