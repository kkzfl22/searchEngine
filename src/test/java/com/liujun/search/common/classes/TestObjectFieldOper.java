package com.liujun.search.common.classes;

import com.liujun.search.engine.collect.operation.docraw.DocRawWriteProc;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/28
 */
public class TestObjectFieldOper {

  @Test
  public void testSetValue() {
    int value = DocRawWriteProc.INSTANCE.getMaxFileSize();
    Assert.assertEquals(1048576, value);

    ObjectFieldOper.INSTANCE.setFieldValue(DocRawWriteProc.INSTANCE, "maxFileSize", 21);

    int value2 = DocRawWriteProc.INSTANCE.getMaxFileSize();
    Assert.assertEquals(21, value2);
  }
}
