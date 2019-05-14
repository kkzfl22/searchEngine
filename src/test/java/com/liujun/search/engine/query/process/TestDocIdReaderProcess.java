package com.liujun.search.engine.query.process;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试网页id与网页文件之间的查询
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/13
 */
public class TestDocIdReaderProcess {

  /** 测试网页链接与网页文本的加载 */
  @Test
  public void testgetDocMap() {
    Map<Long, String> docMap = new HashMap<>();
    DocIdReaderProcess.INSTANCE.loadDocMap(docMap);

    Assert.assertNotNull(docMap);
    Assert.assertNotEquals(0, docMap.size());
  }
}
