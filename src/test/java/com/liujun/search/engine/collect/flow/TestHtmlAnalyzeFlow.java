package com.liujun.search.engine.collect.flow;

import com.liujun.search.engine.collect.FileQueue;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/18
 */
public class TestHtmlAnalyzeFlow {

  public void testHtmlAnalyze() {
    int readNum = 100;
    // 1,在有向图中放入一个顶点
    FileQueue.INSTANCE.put("http://www.sohu.com/");
    // 2，执行下载
    boolean result = HtmlAnalyzeFLow.INSTANCE.htmlAnalyze(readNum);

    // 进行结果的验证
    Assert.assertEquals(result, true);

    // 检查队列中的数字是否为读取到的指定数量
    List<String> downUrlList = new ArrayList<>();

    String data = null;

    while ((data = FileQueue.INSTANCE.get()) != null) {
      downUrlList.add(data);
    }
  }
}
