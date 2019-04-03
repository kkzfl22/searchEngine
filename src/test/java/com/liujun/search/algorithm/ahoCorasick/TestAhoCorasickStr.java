package com.liujun.search.algorithm.ahoCorasick;

import com.liujun.search.algorithm.ahoCorasick.constatnt.AcHtmlTagEnum;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 测试ac自动机进行多模式串字符操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/08
 */
public class TestAhoCorasickStr {

  /** 进行失败指针的构建 */
  @Test
  public void test01add() {
    AhoCorasickChar ahoCorasick = new AhoCorasickChar();
    ahoCorasick.insert("abcde");
    ahoCorasick.insert("fghc");
    ahoCorasick.insert("stdc");
    ahoCorasick.insert("sd");
    ahoCorasick.builderFailurePointer();

    Map<String, Integer> value = ahoCorasick.matchMult("你好啊，fghc看国人");
    System.out.println(value);
    Assert.assertThat(value, Matchers.hasKey("fghc"));
  }

  @Test
  public void testhref() {
    AhoCorasickChar ahoCorasick = new AhoCorasickChar();
    ahoCorasick.insert(AcHtmlTagEnum.HREF_TAG_START.getAckey());

    ahoCorasick.builderFailurePointer();

    Map<String, Integer> value =
        ahoCorasick.matchMult("<a href=\"www.baidu.com\">百度</a><a href='www.sina.com'>新浪</a>");
    System.out.println(value);
    Assert.assertThat(value, Matchers.hasKey("href=\""));
    Assert.assertThat(value, Matchers.hasKey("href='"));

    ahoCorasick.matchOne("<a href=\"www.baidu.com\">百度</a><a href='www.sina.com'>新浪</a>");
  }

  @Test
  public void testhrefIndex() {
    AhoCorasickChar ahoCorasick = new AhoCorasickChar();
    ahoCorasick.insert(AcHtmlTagEnum.HREF_TAG_START.getAckey());

    ahoCorasick.builderFailurePointer();

    char[] mainChars =
        "<a href=\"www.baidu.com\">百度</a><a href='www.sina.com'>新浪</a>".toCharArray();

    int index = ahoCorasick.matcherIndex(mainChars, 0);

    Assert.assertEquals(3, index);

    index = index + AcHtmlTagEnum.HREF_TAG_START.getAckey().get(0).length();

    int indexOut = ahoCorasick.matcherIndex(mainChars, index);

    Assert.assertEquals(33, indexOut);
  }
}
