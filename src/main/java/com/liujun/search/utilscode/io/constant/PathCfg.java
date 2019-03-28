package com.liujun.search.utilscode.io.constant;

import com.liujun.search.common.properties.SysPropertiesUtils;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/07
 */
public class PathCfg {

  /** 基础路径 */
  public static final String BASEPATH =
      SysPropertiesUtils.getInstance().getValue(SysPropertyEnum.FILE_PROCESS_PATH);

  /** 数据收集的目录 */
  public static final String COLLEC_PATH = "collect/";
}
