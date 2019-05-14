package com.liujun.search.engine.index.outputDescIndex;

import com.liujun.search.common.classes.ObjectFieldOper;
import com.liujun.search.engine.index.pojo.TempIndexData;
import com.liujun.search.utilscode.io.constant.PathCfg;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 倒排索引文件输出的测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/11
 */
public class TestDescIndexOutput {

  /** 倒排索引文件排序完成目录 */
  private static final String TMP_INDEX_PATH =
      PathCfg.BASEPATH + PathCfg.INDEX_PATH + PathCfg.INDEX_OUTPUT_DESCINDEX_PATH;

  /** 测试倒排索引文件设置最大大小 */
  @Test
  public void testDescIndexSetMaxSize() {

    int maxFileSize = 10 * 1024 * 1024;

    ObjectFieldOper.INSTANCE.setFieldValue(DescIndexOutput.INSTANCE, "maxFileSize", maxFileSize);

    long sumLength = 0;

    for (int i = 0; i < 10000; i++) {

      List<TempIndexData> tempIndexList = new ArrayList<>();

      for (int j = 0; j < 100; j++) {
        TempIndexData tempIndex = new TempIndexData();
        tempIndex.setTempId(i);
        tempIndex.setDocSeqId(i + 10 + ThreadLocalRandom.current().nextInt());
        tempIndexList.add(tempIndex);
      }

      // 1,输出倒排索引
      int writeLength = DescIndexOutput.INSTANCE.writeIndex(tempIndexList);

      sumLength += writeLength;
    }

    int sumNum = (int) sumLength / maxFileSize;

    // 2,检查倒排索引文件是否存在
    File outFile = new File(TMP_INDEX_PATH);

    Assert.assertNotEquals(sumNum * 2, outFile.listFiles().length);
  }

  //@After
  public void afterClean() {
    DescIndexOffsetOutput.INSTANCE.close();
    DescIndexOutput.INSTANCE.close();


    File[] fileIndex = new File(TMP_INDEX_PATH).listFiles();

    for (File fileItem : fileIndex) {
      fileItem.delete();
    }
  }
}
