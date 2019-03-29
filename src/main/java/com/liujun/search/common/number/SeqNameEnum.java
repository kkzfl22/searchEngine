package com.liujun.search.common.number;

/**
 * 序列号名称管理器
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/28
 */
public enum SeqNameEnum {

  /** 网页的id */
  SEQ_HTML_DOC_ID("seq_html_doc_id_number"),
  ;

  /** 序列号名称 */
  private String name;

  SeqNameEnum(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
