package com.liujun.search.engine.collect.constant;

/**
 * 搜索入口的网站信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/19
 */
public enum WebEntryEnum {
  SOHO("http://www.sohu.com/", "sohu"),

  QQ("https://www.qq.com/", "qq"),

  WY163("https://www.163.com/", "163"),

  SINA("https://www.sina.com.cn/", "sina"),

  CSDN("https://www.csdn.net/", "csdn"),

  CARHOME("https://www.autohome.com.cn", "autohome"),

  ZOL("http://www.zol.com.cn/", "zol"),

  ITEYE("https://www.iteye.com/", "iteye");

  /** 网站地址信息 */
  private String urlAddress;

  /** 标识信息 */
  private String flag;

  WebEntryEnum(String urlAddress, String flag) {
    this.urlAddress = urlAddress;
    this.flag = flag;
  }

  public String getUrlAddress() {
    return urlAddress;
  }

  public String getFlag() {
    return flag;
  }
}
