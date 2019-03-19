package com.liujun.search.engine.collect.flow;

import com.liujun.search.engine.collect.constant.WebEntryEnum;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/18
 */
public class TestHtmlAnalyzeFlow {

  /** 测试网页分析 */
  @Test
  public void testHtmlAnalyze() {
    int readNum = 100;

    // 2，执行下载
    boolean result = HtmlAnalyzeFLow.INSTANCE.htmlAnalyze(readNum, WebEntryEnum.SOHO);

    // 进行结果的验证
    Assert.assertEquals(result, true);

    // 读取100条记录
    List<String> readList = HtmlAnalyzeFLow.INSTANCE.getTopUrl(100, WebEntryEnum.SOHO);

    Assert.assertEquals(readNum, readList.size());
  }
}
