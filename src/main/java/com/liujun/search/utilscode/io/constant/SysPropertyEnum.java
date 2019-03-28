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
  FILE_MAX_SIZE("file.max.size");

  private String key;

  SysPropertyEnum(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
