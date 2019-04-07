package com.liujun.search.utilscode.io.constant;

/**
 * 属性文件的枚举对象
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/01
 */
public enum SysPropertyEnum {

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
  ;

  private String key;

  SysPropertyEnum(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
