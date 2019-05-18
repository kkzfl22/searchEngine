package com.liujun.search.engine.analyze;

import com.liujun.search.engine.EngineStageInf;
import com.liujun.search.engine.analyze.operation.AnalyzeFlow;

/**
 * 搜索引擎的处理，进行采集的网页数据分析
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/18
 */
public class EngineStageAnalyze implements EngineStageInf {

  public static final EngineStageAnalyze INSTANCE = new EngineStageAnalyze();

  @Override
  public void stageProcess() {
    AnalyzeFlow.INSTANCE.analyze();
  }
}
