package com.liujun.search.engine;

import com.liujun.search.common.constant.SearchEngineStageEnum;
import com.liujun.search.common.constant.SysProperty;
import com.liujun.search.common.constant.SysPropertyEnum;
import com.liujun.search.common.properties.SysPropertiesUtils;
import com.liujun.search.engine.analyze.EngineStageAnalyze;
import com.liujun.search.engine.collect.EngineStageCollect;
import com.liujun.search.engine.index.EngineStageIndex;

import java.util.HashMap;
import java.util.Map;

/**
 * 搜索引擎入口
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/18
 */
public class SearchEngineMain {

  /** 运行的阶段信息 */
  private static final Map<Integer, EngineStageInf> RUN_STAGE_MAP = new HashMap<>();

  static {
    // 数据搜索阶段
    RUN_STAGE_MAP.put(SearchEngineStageEnum.STAGE_COLLECT.getStage(), EngineStageCollect.INSTANCE);
    // 数据分析阶段
    RUN_STAGE_MAP.put(SearchEngineStageEnum.STAGE_ANALYZE.getStage(), EngineStageAnalyze.INSTANCE);
    // 数据索引阶段
    RUN_STAGE_MAP.put(SearchEngineStageEnum.STAGE_INDEX.getStage(), EngineStageIndex.INSTANCE);
  }

  public static void main(String[] args) {
    // 加载信息
    Load();

    // 进行对应阶段的处理
    RunStage();
  }

  /** 进行加载操作 */
  private static void Load() {
    // 加载配制文件
    String value = System.getProperty(SysProperty.CONFIG_PATH.getKey());
    SysPropertiesUtils.getInstance().reloadProc(value);
  }

  private static void RunStage() {
    // 2,加载配制文件中的当前执行阶段
    int stage =
        SysPropertiesUtils.getInstance()
            .getIntegerValueOrDef(SysPropertyEnum.SEARCH_ENGINE_RUN_STAGE, 0);

    EngineStageInf engineStage = RUN_STAGE_MAP.get(stage);

    if (null != engineStage) {
      System.out.println("start start ");
      // 进行对应阶段的执行操作
      engineStage.stageProcess();

      System.out.println("start finish ");
    } else {
      throw new RuntimeException("config error, curr stage " + stage + " not exists");
    }
  }
}
