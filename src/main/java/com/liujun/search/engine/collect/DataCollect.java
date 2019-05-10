package com.liujun.search.engine.collect;

import com.liujun.search.common.constant.SysProperty;
import com.liujun.search.common.properties.SysPropertiesUtils;

/**
 * 数据采集操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/01
 */
public class DataCollect {

  public static final DataCollect INSTANCE = new DataCollect();

  public static void main(String[] args) {

    // 加载配制文件
    String value = System.getProperty(SysProperty.CONFIG_PATH.getKey());
    SysPropertiesUtils.getInstance().reloadProc(value);

    // 执行收集操作
    DataCollectFlow.INSTANCE.runFlow();
  }
}
