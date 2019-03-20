package com.liujun.search.engine.collect.operation.html;

import org.junit.Assert;
import org.junit.Test;

/**
 * 网页的链接处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/20
 */
public class TestHrefContentOperation {

  /** 链接中描的处理 */
  @Test
  public void anchorOperation() {
    String context = "http://auto.sohu.com/#utab_model";
    String value = HrefContentOperation.INSTANCE.anchor(context);
    String comp1 = "http://auto.sohu.com/";
    Assert.assertEquals(value, comp1);
  }

  /** 链接中描的处理 */
  @Test
  public void anchorOperation2() {
    String context = "https://www.sohu.com/a/302080106_430526";
    String value = HrefContentOperation.INSTANCE.anchor(context);

    Assert.assertEquals(value, context);
  }

  /** 链接中描的处理 */
  @Test
  public void contextOperation() {
    String context = "http://auto.sohu.com/#utab_model";
    String value = HrefContentOperation.INSTANCE.hrefContext(context);
    String comp1 = "http://auto.sohu.com/";
    Assert.assertEquals(value, comp1);
  }

  /** 链接中描的处理 */
  @Test
  public void contextOperation2() {
    String context = "https://www.sohu.com/a/302080106_430526";
    String value = HrefContentOperation.INSTANCE.hrefContext(context);

    Assert.assertEquals(value, context);
  }
}
