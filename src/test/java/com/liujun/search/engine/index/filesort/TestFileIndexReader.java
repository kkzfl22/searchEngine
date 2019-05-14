package com.liujun.search.engine.index.filesort;

import com.liujun.search.engine.index.pojo.TempIndexData;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 临时索引文件读取操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/09
 */
public class TestFileIndexReader {

  @Test
  public void testFileIndex() {

    File[] list = FileIndexReader.INSTANCE.getIndexFiles();

    for (int i = 0; i < 5; i++) {

      long startTime = System.currentTimeMillis();

      for (File item : list) {
        // List<TempIndexData> tempIndexList = null;
        FileIndexReader.INSTANCE.getFileIndexList(item);
        // Assert.assertNotNull(tempIndexList);
      }

      long endTime = System.currentTimeMillis();
      System.out.println("时间差:" + (endTime - startTime));
    }
  }

  @Test
  public void testTreeMap() {
    TreeMap<Long, Integer> BUFFERSIZE = new TreeMap<>();

    BUFFERSIZE.put(1L * 1024 * 512, 40000);
    BUFFERSIZE.put(1L * 1024 * 128, 10000);
    BUFFERSIZE.put(1L * 1024 * 256, 20000);

    // 经过测试算，每1.1M的文件大约是8万行，预约空间，以防止数组扩容带来的开销
    // 一共预约128个档，进行文件操作
    for (int i = 0; i < 128; i++) {
      BUFFERSIZE.put(1L * 1024 * 1024 * i, 80000 * i);
    }

    Map.Entry<Long, Integer> mapEntry = BUFFERSIZE.ceilingEntry(0L);
    System.out.println(mapEntry);
    Assert.assertNotEquals(mapEntry, null);
    mapEntry = BUFFERSIZE.ceilingEntry(1L * 1024 * 1024 * 21l);
    System.out.println(mapEntry);
    Assert.assertNotEquals(mapEntry, null);
  }
}
