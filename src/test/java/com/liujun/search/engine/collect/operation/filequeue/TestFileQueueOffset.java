package com.liujun.search.engine.collect.operation.filequeue;

import com.liujun.search.engine.collect.constant.WebEntryEnum;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * 进行文件队列偏移的操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/21
 */
public class TestFileQueueOffset {

  /** 测试的入口信息 */
  private WebEntryEnum entry = WebEntryEnum.SOHO;

  /** 测试偏移量信息 */
  @Test
  public void readOffset() {
    FileQueueOffset offset = new FileQueueOffset(entry.getFlag());
    // 首先执行清理
    offset.clean();
    // 执行读取偏移操作
    offset.readOffset();
    Assert.assertEquals(0, offset.getReadOffset());
    Assert.assertEquals(0, offset.getWriteOffset());
  }

  @Test
  public void writeOffset() {
    FileQueueOffset offset = new FileQueueOffset(entry.getFlag());
    offset.setReadOffset(10);
    offset.setWriteOffset(20);
    offset.writeOffset();
    offset.readOffset();
    Assert.assertEquals(10, offset.getReadOffset());
    Assert.assertEquals(20, offset.getWriteOffset());
  }

  /** 执行清理操作 */
  @After
  public void clean() {
    FileQueueOffset offset = new FileQueueOffset(entry.getFlag());
    offset.clean();
  }
}
