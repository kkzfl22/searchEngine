package com.liujun.search.algorithm.bloomFilter;

import com.liujun.search.common.properties.SysPropertiesUtils;
import com.liujun.search.common.constant.PathCfg;
import com.liujun.search.common.constant.SysPropertyEnum;

/**
 * 网页链接过滤器
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/18
 */
public class HtmlUrlFilter extends BloomFilterSerializable {

  /** 基础路径 */
  private static final String BASEPATH =
      SysPropertiesUtils.getInstance().getValue(SysPropertyEnum.FILE_PROCESS_PATH);

  /** 待爬取网页链接文件 */
  private static final String BLOOM_FILTER_FILE = "htmlurl_bloom_filter.bin";

  /** 布隆过滤器文件 */
  private static final String PROCESS_BLOOM_FILTER =
      BASEPATH + PathCfg.COLLEC_PATH + BLOOM_FILTER_FILE;

  /** 默认的大小,使用10亿大小 */
  private static final int DEFAULT_BITES = 1000000000;

  /** 默认的布隆过滤器实例 */
  public static final HtmlUrlFilter INSTANCE = new HtmlUrlFilter(DEFAULT_BITES);

  private HtmlUrlFilter(int nbits) {
    super(nbits);
  }

  @Override
  protected String getOutPathFile() {
    return PROCESS_BLOOM_FILTER;
  }
}
