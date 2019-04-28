package com.liujun.search.engine.analyze.operation;

import org.junit.Test;

/**
 * 测试网页的分析流程
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/27
 */
public class TestAnalyzeFlow {

  /** 测试网页的分析流程 */
  @Test
  public void testAnalyze() {
    AnalyzeFlow.INSTANCE.analyze();
  }
}
