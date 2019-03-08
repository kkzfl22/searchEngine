package com.liujun.search.engine.collect;

import com.liujun.search.engine.collect.html.FileChunkMsg;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * 测试文件管理器
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/05
 */
// Junit测试顺序：@FixMethodOrder
// ** MethodSorters.DEFAULT **（默认）
// 默认顺序由方法名hashcode值来决定，如果hash值大小一致，则按名字的字典顺序确定。
// ** MethodSorters.NAME_ASCENDING （推荐） **
//   按方法名称的进行排序，由于是按字符的字典顺序，所以以这种方式指定执行顺序会始终保持一致；
// ** MethodSorters.JVM **
//    按JVM返回的方法名的顺序执行，此种方式下测试方法的执行顺序是不可预测的，即每次运行的顺序可能
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestFileManage {

  /** 测试向文件管理器中插入一个数据 */
  @Test
  public void test01PutData() {

    String msg = "这是数据内容!";
    long htmlId = 0;

    FileChunkMsg data = FileManage.INSTANCE.putData(htmlId, msg);
    Assert.assertNotNull(data);

    // 获取数据
    String msgOut = FileManage.INSTANCE.getData(data);
    Assert.assertNotNull(msgOut);

    // 进行数据的保存测试
    FileManage.INSTANCE.save();
    FileManage.INSTANCE.load();

    Assert.assertEquals(FileManage.INSTANCE.getFileIndex(), data.getFileIndex());
  }

  /** 测试向文件管理器中插入一个数据 */
  @Test
  public void test02PutBigData() {

    StringBuilder outmsg = new StringBuilder();

    for (int i = 0; i < 1024; i++) {
      outmsg.append("12345678901234567890123456789012345678901234567890").append("\n");
      outmsg.append("12345678901234567890123456789012345678901234567890").append("\n");
    }

    long htmlId = 0;
    FileChunkMsg data = FileManage.INSTANCE.putData(htmlId, outmsg.toString());
    Assert.assertNotNull(data);

    // 获取数据
    String msgOut = FileManage.INSTANCE.getData(data);
    Assert.assertNotNull(msgOut);

    // 进行数据的保存测试
    FileManage.INSTANCE.save();
    FileManage.INSTANCE.load();

    Assert.assertEquals(FileManage.INSTANCE.getFileIndex(), data.getFileIndex());
  }
}
