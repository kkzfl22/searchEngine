package com.liujun.search.algorithm.ahoCorasick.pojo;

/**
 * 进行多字符匹配的信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/13
 */
public class MatcherBusi {

  /** 当前匹配的字符串信息 */
  private String matcherKey;

  /** 当前匹配的索引信息 -1 表示未匹配上，默认值 */
  private int matcherIndex = -1;

  public String getMatcherKey() {
    return matcherKey;
  }

  public void setMatcherKey(String matcherKey) {
    this.matcherKey = matcherKey;
  }

  public int getMatcherIndex() {
    return matcherIndex;
  }

  public void setMatcherIndex(int matcherIndex) {
    this.matcherIndex = matcherIndex;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("MatcherBusi{");
    sb.append("matcherKey='").append(matcherKey).append('\'');
    sb.append(", matcherIndex=").append(matcherIndex);
    sb.append('}');
    return sb.toString();
  }
}
