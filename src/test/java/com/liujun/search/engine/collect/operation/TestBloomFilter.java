package com.liujun.search.engine.collect.operation;

import com.liujun.search.engine.collect.operation.BloomFilter;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * 测试布隆过滤器
 *
 * <p>1，检查数据是否在
 *
 * <p>2，将数据添加至布隆过滤器中
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/04
 */
// Junit测试顺序：@FixMethodOrder
// ** MethodSorters.DEFAULT **（默认）
// 默认顺序由方法名hashcode值来决定，如果hash值大小一致，则按名字的字典顺序确定。
// ** MethodSorters.NAME_ASCENDING （推荐） **
//   按方法名称的进行排序，由于是按字符的字典顺序，所以以这种方式指定执行顺序会始终保持一致；
// ** MethodSorters.JVM **
//    按JVM返回的方法名的顺序执行，此种方式下测试方法的执行顺序是不可预测的，即每次运行的顺序可能
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestBloomFilter {

  /** 测试布隆过滤器 */
  @Test
  public void test01BloomFilterPutAndExists() {
    String url = "http://www.baidu.com";
    BloomFilter.INSTANCE.putData(url);
    boolean exists = BloomFilter.INSTANCE.exists(url);
    Assert.assertEquals(true, exists);
  }

  /** 测试保存布隆过滤器的数据 */
  @Test
  public void test02FilterSave() {
    BloomFilter.INSTANCE.save();
    String url = "http://www.baidu.com2";
    BloomFilter.INSTANCE.putData(url);
    boolean exists = BloomFilter.INSTANCE.exists(url);
    Assert.assertEquals(true, exists);
  }

  /** 测试布隆过滤器在保存之后重新加载后是否还能正常 */
  @Test
  public void test03BloomSaveLoader() {
    String url = "http://www.baidu.com3";
    BloomFilter.INSTANCE.putData(url);
    boolean exists = BloomFilter.INSTANCE.exists(url);
    Assert.assertEquals(true, exists);

    BloomFilter.INSTANCE.save();
    BloomFilter.INSTANCE.loader();

    boolean exists2 = BloomFilter.INSTANCE.exists(url);
    Assert.assertEquals(true, exists2);
  }
}
