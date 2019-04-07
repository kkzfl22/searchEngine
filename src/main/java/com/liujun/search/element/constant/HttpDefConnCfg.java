package com.liujun.search.element.constant;

/**
 * 网页池化连接参数默认配制信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/05
 */
public enum HttpDefConnCfg {

  /** 最大连接数 */
  POOL_MAX_TOTAL(50),

  /** 设置设置每个路由上的默认连接个数 */
  HTTP_MAX_DEFAULT_ROUTE(2),

  /** 网页socket超时设置 */
  HTTP_SOCKET_TIMEOUT(25000),

  /** 连接响应超时设置 */
  CONN_TIMEOUT(20000),

  /** 网页请求连接超时参数 */
  CONN_REQ_TIMEOUT(20000),
  ;

  private int cfg;

  HttpDefConnCfg(int cfg) {
    this.cfg = cfg;
  }

  public int getCfg() {
    return cfg;
  }
}
