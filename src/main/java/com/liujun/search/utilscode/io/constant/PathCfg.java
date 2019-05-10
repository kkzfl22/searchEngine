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

  /** 数据分析的基础目录 */
  public static final String ANALYZE_PATH = "analyze/";

  /** 数据分析阶段,临时索引文件，可能有多个 */
  public static final String ANALYZE_PATH_TEMP_INDEX_PATH = "tempindex";

  /** 输出索引文件的目录 */
  public static final String INDEX_PATH = "index/";

  /** 临时索引文件排序 完成后的输出目录 */
  public static final String INDEX_TEMP_SORT_INDEX_PATH = "sortindex";
}
