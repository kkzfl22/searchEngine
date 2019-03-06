package com.liujun.search.engine.collect;

import com.liujun.search.engine.collect.FileManage;
import com.liujun.search.engine.collect.html.FileChunkMsg;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试文件管理器
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/05
 */
public class TestFileManage {

  /** 测试向文件管理器中插入一个数据 */
  @Test
  public void testPutData() {

    String msg = "这是数据内容!";
    long htmlId = 0;

    FileChunkMsg data = FileManage.INSTANCE.putData(htmlId, msg);

    Assert.assertNotNull(data);
  }

  /** 获取数据内容信息 */
  @Test
  public void testGetData() {
    int fileIndex = 0;
    int chunkIndex = 0;

    FileChunkMsg chunkmsg = new FileChunkMsg(0, 0, 0);

    String msg = FileManage.INSTANCE.getData(chunkmsg);

    Assert.assertNotNull(msg);
  }
}
