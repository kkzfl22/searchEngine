package com.liujun.search.utilscode.io.constant;

/**
 * 进行路径的枚举
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/27
 */
public enum PathEnum {
  /** 输出的io路径信息 */
  FILE_OUTPUT_IO("file/outout/io"),

  /** 网页文件分析的路径 */
  FILE_ANALYZE_HTML_PATH("html/analyze"),
  ;

  /** 路径信息 */
  private String path;

  PathEnum(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }
}
