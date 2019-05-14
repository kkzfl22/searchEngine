package com.liujun.search.engine.index.filesort;

import com.liujun.search.engine.index.pojo.TempIndexData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 进行测试临时索引文件的输出操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/09
 */
public class TestFileIndexOutput {

  /** 进行排序完成的临时索引文件输出 */
  @Test
  public void testoutSortFileIndex() {
    List<TempIndexData> list = new ArrayList<>();

    TempIndexData tempIndex = new TempIndexData();
    tempIndex.setTempId(100);
    tempIndex.setDocSeqId(1084l);

    list.add(tempIndex);
    list.add(tempIndex);
    list.add(tempIndex);

    FileIndexOutput.INSTANCE.outSortFileIndex(1, list);
  }
}
