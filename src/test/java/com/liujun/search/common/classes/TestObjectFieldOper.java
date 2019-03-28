package com.liujun.search.common.classes;

import com.liujun.search.engine.collect.operation.docraw.DocRawFileStreamManager;
import com.liujun.search.engine.collect.operation.docraw.DocRawProc;
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
    int value = DocRawProc.INSTANCE.getMaxFileSize();
    Assert.assertEquals(1048576, value);

    ObjectFieldOper.INSTANCE.setFieldValue(DocRawProc.INSTANCE, "maxFileSize", 21);

    int value2 = DocRawProc.INSTANCE.getMaxFileSize();
    Assert.assertEquals(21, value2);
  }
}
