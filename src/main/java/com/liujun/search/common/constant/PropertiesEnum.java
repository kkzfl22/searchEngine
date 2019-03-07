package com.liujun.search.common.constant;

/**
 * 属性文件操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/07
 */
public enum PropertiesEnum {

  /** 文件索引号，用于记录文件索引编号 */
  FILE_INDEX_OUT("file.index.out"),

  /** 文件写入到的位置信息 */
  FILE_POSTION_OUT("file.postion.out"),
  ;

  private String key;

  private PropertiesEnum(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
