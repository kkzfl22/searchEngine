package com.liujun.search.engine.collect;

import com.liujun.search.engine.EngineStageInf;

/**
 * 数据搜索阶段的处理工作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/18
 */
public class EngineStageCollect implements EngineStageInf {

  public static final EngineStageCollect INSTANCE = new EngineStageCollect();

  @Override
  public void stageProcess() {
    // 执行收集操作
    DataCollectFlow.INSTANCE.runFlow();
  }
}
