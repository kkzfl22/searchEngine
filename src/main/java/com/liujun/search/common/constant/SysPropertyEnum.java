package com.liujun.search.common.constant;

/**
 * 属性文件的枚举对象
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/01
 */
public enum SysPropertyEnum {

  /**
   * 搜索引擎的处理阶段阶段
   *
   * <p>1，数据的收集
   *
   * <p>2, 数据分析
   *
   * <p>3, 索引阶段
   *
   * <p>4, 最后的查询操作,此阶段需要方法调用
   */
  SEARCH_ENGINE_RUN_STAGE("search.engine.run.stage"),

  /** 文件操作的基础路径 */
  FILE_PROCESS_PATH("file.process.path"),

  /** 用于限制存储网页的单文件大小 */
  FILE_MAX_SIZE("file.max.size"),

  /** 网页连接的最大连接 */
  HTTP_POOL_MAXTOTAL("http.pool.maxtotal"),

  /** 设置设置每个路由上的默认连接个数 */
  HTTP_MAX_DEFAULT_PERROUTE("http.max.default.perroute"),

  /** 网页响应的socket超时 */
  HTTP_SOCKETTIMEOUT("http.sockettimout"),

  /** 网页连接响应超时 */
  HTTP_CONN_TIMEOUT("http.conn.timeout"),

  /** 网页请求的超时设置 */
  HTTP_CONN_REQ_TIMEOUT("http.conn.req.timeout"),

  /** 网页分词分析结果，限制最大单文件大小 */
  ANALYZE_MAX_FILE("analyze.max.file"),

  /** 分析阶段,读取缓存的数据条数 */
  ANZLYZE_CACHE_READ_NUM("analyze.cache.read.num"),

  /** 倒排索引阶段，单文件的最大大小 */
  DESC_INDEX_MAXFILE_SIZE("desc.index.max.file"),
  ;

  private String key;

  SysPropertyEnum(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
