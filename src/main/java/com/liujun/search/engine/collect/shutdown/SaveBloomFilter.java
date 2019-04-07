package com.liujun.search.engine.collect.shutdown;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.operation.BloomFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 保存布隆过滤器中的数据
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/06
 */
public class SaveBloomFilter implements FlowServiceInf {

  public static final SaveBloomFilter INSTANCE = new SaveBloomFilter();

  private Logger logger = LoggerFactory.getLogger(SaveBloomFilter.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("shutdown flow save bloom filter start");

    // 保存布隆过滤器的数据
    BloomFilter.INSTANCE.save();

    logger.info("shutdown flow save bloom filter finish");

    return true;
  }
}
