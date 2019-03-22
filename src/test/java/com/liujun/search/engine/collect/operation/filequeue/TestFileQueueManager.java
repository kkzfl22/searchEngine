package com.liujun.search.engine.collect.operation.filequeue;

import com.liujun.search.engine.collect.constant.WebEntryEnum;
import org.junit.Assert;
import org.junit.Test;

/**
 * 文件管理队列的实现，即实现第个入口一个对象的操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/22
 */
public class TestFileQueueManager {

  @Test
  public void testGetInstance() {
    FileQueue sohoQueue = FileQueueManager.INSTANCE.getFileQueue(WebEntryEnum.SOHO);
    Assert.assertNotNull(sohoQueue);

    FileQueue sohoQueueComp2 = FileQueueManager.INSTANCE.getFileQueue(WebEntryEnum.SOHO);
    // 检查获取的对象是否为同一个
    Assert.assertEquals(sohoQueueComp2, sohoQueue);
  }
}
