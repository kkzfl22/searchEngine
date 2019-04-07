package com.liujun.search.engine.collect.shutdown;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.operation.BloomFilter;
import com.liujun.search.engine.collect.operation.DocIdproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 保存布隆过滤器中的数据
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/06
 */
public class CloseDocIdFile implements FlowServiceInf {

  public static final CloseDocIdFile INSTANCE = new CloseDocIdFile();

  private Logger logger = LoggerFactory.getLogger(CloseDocIdFile.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    logger.info("shutdown flow close doc id start");

    // 保存布隆过滤器的数据
    DocIdproc.INSTANCE.close();

    logger.info("shutdown flow close doc id finish");

    return true;
  }
}
