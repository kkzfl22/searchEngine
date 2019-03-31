package com.liujun.search.algorithm.manager;

import com.liujun.search.algorithm.manager.constant.BMHtmlTagContextEnum;
import com.liujun.search.algorithm.manager.constant.BMHtmlTagEnum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 初始化模式串
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/31
 */
public class InitParrern {

  public static final InitParrern INSTANCE = new InitParrern();

  /**
   * 获取初始化模式串字符
   *
   * @return
   */
  public Set<String> getInitParrertChars() {

    Set<String> parrernChars = new HashSet<>();

    // 添加网页标签处理器
    this.addHtmlTag(parrernChars);

    // 添加网页标签内容处理
    this.addHtmlTagContext(parrernChars);

    return parrernChars;
  }

  /**
   * 添加模式模式串的网页标签字符串
   *
   * @param parrernChars 网页串集合
   */
  private void addHtmlTag(Set<String> parrernChars) {

    for (BMHtmlTagEnum tal : BMHtmlTagEnum.values()) {
      parrernChars.add(tal.getBegin());
      parrernChars.add(tal.getEnd());
    }
  }

  /**
   * 添加网页标签内容匹配器
   *
   * @param parrernChars
   */
  private void addHtmlTagContext(Set<String> parrernChars) {
    for (BMHtmlTagContextEnum context : BMHtmlTagContextEnum.values()) {
      parrernChars.add(context.getPattern());
    }
  }
}
