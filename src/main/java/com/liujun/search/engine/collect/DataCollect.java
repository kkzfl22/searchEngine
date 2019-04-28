package com.liujun.search.engine.collect;

import com.liujun.search.common.constant.SysProperty;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.common.properties.SysPropertiesUtils;
import com.liujun.search.engine.collect.constant.WebEntryEnum;
import com.liujun.search.engine.collect.start.*;
import com.liujun.search.engine.collect.thread.CollectThreadPool;
import com.liujun.search.engine.collect.thread.HtmCollectThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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
    SysPropertiesUtils.getInstance().loadProc(value);

    // 执行收集操作
    DataCollectFlow.INSTANCE.runFlow();
  }
}
