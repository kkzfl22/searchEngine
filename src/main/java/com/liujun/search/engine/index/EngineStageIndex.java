package com.liujun.search.engine.index;

import com.liujun.search.engine.EngineStageInf;
import com.liujun.search.engine.index.filesort.TempIndexSort;
import com.liujun.search.engine.index.mergeIndex.MergeSortIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 搜索引擎的索引阶段处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/18
 */
public class EngineStageIndex implements EngineStageInf {

  public static final EngineStageIndex INSTANCE = new EngineStageIndex();

  private Logger logger = LoggerFactory.getLogger(EngineStageIndex.class);

  @Override
  public void stageProcess() {

    logger.info("search engine index start ");

    // 1，进行临时索引的排序
    TempIndexSort.INSTANCE.tempIndexSort();
    logger.info("search engine desc temp index finish ");
    // 2,进行索引的合并输出
    MergeSortIndex.INSTANCE.mergeIndex();

    logger.info("search engine index finish ");
  }
}
