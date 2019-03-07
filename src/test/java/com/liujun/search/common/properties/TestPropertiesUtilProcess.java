package com.liujun.search.common.properties;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * 测试属性文件操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/07
 */
// Junit测试顺序：@FixMethodOrder
// ** MethodSorters.DEFAULT **（默认）
// 默认顺序由方法名hashcode值来决定，如果hash值大小一致，则按名字的字典顺序确定。
// ** MethodSorters.NAME_ASCENDING （推荐） **
//   按方法名称的进行排序，由于是按字符的字典顺序，所以以这种方式指定执行顺序会始终保持一致；
// ** MethodSorters.JVM **
//    按JVM返回的方法名的顺序执行，此种方式下测试方法的执行顺序是不可预测的，即每次运行的顺序可能
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPropertiesUtilProcess {

  /** 测试属性文件的保存操作 */
  @Test
  public void test01PropertieSet() {
    PropertiesUtilProcess.INSTANCE.setValue("key1", "value1");
    PropertiesUtilProcess.INSTANCE.setValue("key2", "value2");

    // 进行属性文件的保存操作
    PropertiesUtilProcess.INSTANCE.saveProperties();
  }

  /** 测试属性文件的存储操作 */
  @Test
  public void test02PropertiesGet() {
    PropertiesUtilProcess.INSTANCE.loadProperties();
    String value = PropertiesUtilProcess.INSTANCE.getValue("key1");
    Assert.assertEquals("value1", value);

    String value2 = PropertiesUtilProcess.INSTANCE.getValue("key2");
    Assert.assertEquals(value2, value2);
  }
}
