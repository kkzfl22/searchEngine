package com.liujun.search.engine.analyze.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/13
 */
public enum HtmlTagSectionEnum {

  /** 网页script标签段 */
  SECTION_SCRIPT("<script", "</script>"),

  /** 网页样式标签段 */
  SECTION_STYLE("<style", "</style>"),

  /** 选择框的标签段 */
  SECTION_OPTION("<option", "</option>");

  /** 段落标签 */
  private String sectionStart;

  /** 段落结束 */
  private String sectionEnd;

  HtmlTagSectionEnum(String sectionStart, String sectionEnd) {
    this.sectionStart = sectionStart;
    this.sectionEnd = sectionEnd;
  }

  public String getSectionStart() {
    return sectionStart;
  }

  public String getSectionEnd() {
    return sectionEnd;
  }

  /**
   * 获取网页开始集合
   *
   * @return 开始标签信合
   */
  public static List<String> GetScriptStartList() {

    List<String> getList = new ArrayList<>(values().length);

    for (HtmlTagSectionEnum tagSection : values()) {
      getList.add(tagSection.getSectionStart());
    }

    return getList;
  }

  /**
   * 获取网页开始集合
   *
   * @return 开始标签信合
   */
  public static List<String> GetScriptEndList() {

    List<String> getList = new ArrayList<>(values().length);

    for (HtmlTagSectionEnum tagSection : values()) {
      getList.add(tagSection.getSectionEnd());
    }

    return getList;
  }

  /**
   * 开始与结束标签配对检查
   *
   * @param start 开始标签
   * @param end 结束标签
   * @return true 表示配对成功，false 配对失败
   */
  public static HtmlTagSectionEnum getSectionTag(String start, String end) {

    for (HtmlTagSectionEnum sectionItem : values()) {
      if (sectionItem.getSectionStart().equals(start) && sectionItem.getSectionEnd().equals(end)) {
        return sectionItem;
      }
    }

    return null;
  }

  /**
   * 获取网页标签
   *
   * @param startTag 开始标签
   * @return html标签
   */
  public static HtmlTagSectionEnum GetTag(String startTag) {
    for (HtmlTagSectionEnum tagSection : values()) {
      if (tagSection.getSectionStart().equals(startTag)) {
        return tagSection;
      }
    }

    throw new RuntimeException("curr html tag " + startTag + " not exists enum");
  }
}
