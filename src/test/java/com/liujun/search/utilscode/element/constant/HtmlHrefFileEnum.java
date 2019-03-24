package com.liujun.search.utilscode.element.constant;

/**
 * 网页测试链接的文件枚举
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/21
 */
public enum HtmlHrefFileEnum {
  WY163("163.html"),

  SINA("sina.html"),

  SOHO("soho.html"),

  SMALL("small.html"),
  ;

  /** 网页链接信息 */
  private String file;

  HtmlHrefFileEnum(String file) {
    this.file = file;
  }

  public String getFile() {
    return file;
  }
}
