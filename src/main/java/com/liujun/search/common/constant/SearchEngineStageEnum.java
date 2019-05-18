package com.liujun.search.common.constant;

/**
 * 搜索引擎的阶段
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/18
 */
public enum SearchEngineStageEnum {

  /** 1,collect 数据的收集 采用http方式下载 */
  STAGE_COLLECT(1),

  /** 2,analyze 数据分析 */
  STAGE_ANALYZE(2),

  /** 3,index 索引阶段 */
  STAGE_INDEX(3),
  ;

  /** 阶段 */
  private int stage;

  SearchEngineStageEnum(int stage) {
    this.stage = stage;
  }

  public int getStage() {
    return stage;
  }
}
