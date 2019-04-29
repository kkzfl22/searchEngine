package com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.tempIndexFile;

import com.liujun.search.common.properties.SysPropertiesUtils;
import com.liujun.search.utilscode.io.constant.SysPropertyEnum;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试临时索引文件的操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/25
 */
public class TestTempIndexFile {

  /** 测试写入临时索引文件 */
  @Test
  public void writeTempIndexFile() {
    String keys = "中国人民银行法";
    int keyIndex = 2413;
    int index = 206078;

    TempIndexFile.INSTANCE.writeData(keyIndex, index);

    Assert.assertNotEquals(0, TempIndexFile.INSTANCE.getCurrFileSize());
  }

  /** 用于测试当文件到达指定大小后，切换的操作 */
  @Test
  public void writeSwitch() {
    int size = 20;

    SysPropertiesUtils.getInstance()
        .setValue(SysPropertyEnum.ANALYZE_MAX_FILE, String.valueOf(size));

    int keyIndex = 123456789;

    TempIndexFile.INSTANCE.writeData(keyIndex, 12041);
    TempIndexFile.INSTANCE.writeData(keyIndex, 12041);
    // 数据刷入磁盘操作
    TempIndexFile.INSTANCE.flush();

    // 何时索引文件是否已经切换为1
    Assert.assertEquals(1, TempIndexFile.INSTANCE.getFileIndex());
  }

  @AfterClass
  public static void AfterClean() {
    TempIndexFile.INSTANCE.clean();
  }
}
