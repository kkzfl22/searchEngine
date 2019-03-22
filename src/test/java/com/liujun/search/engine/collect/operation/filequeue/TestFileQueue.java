package com.liujun.search.engine.collect.operation.filequeue;

import com.liujun.search.engine.collect.constant.WebEntryEnum;
import com.liujun.search.engine.collect.operation.filequeue.FileQueue;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件队列的单元测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/03
 */
// Junit测试顺序：@FixMethodOrder
// ** MethodSorters.DEFAULT **（默认）
// 默认顺序由方法名hashcode值来决定，如果hash值大小一致，则按名字的字典顺序确定。
// ** MethodSorters.NAME_ASCENDING （推荐） **
//   按方法名称的进行排序，由于是按字符的字典顺序，所以以这种方式指定执行顺序会始终保持一致；
// ** MethodSorters.JVM **
//    按JVM返回的方法名的顺序执行，此种方式下测试方法的执行顺序是不可预测的，即每次运行的顺序可能
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestFileQueue {

  private static final WebEntryEnum FLAG = WebEntryEnum.SOHO;

  private FileQueue instance;

  /** 仅操作一次 */
  @Before
  public void testClean() {
    instance = FileQueue.GetQueue(FLAG);
    instance.clean();
    instance.openFileQueue();
  }

  /** 从队列中获取一个数据 */
  @Test
  public void test02QueueGet() {
    String lineData = FLAG.getUrlAddress();

    String getData = instance.get();

    Assert.assertEquals(lineData, getData);
  }

  @Test
  public void test03QueuePutList() {
    List<String> list = new ArrayList<>();

    String line1 = "http://www.sohu.com/";
    list.add(line1);
    String line2 = "http://www.sohu.com2/";
    list.add(line2);
    String line3 = "http://www.sohu.com3/";
    list.add(line3);
    String line4 = "http://www.sohu.com4/";
    list.add(line4);

    boolean result = instance.put(list);
    Assert.assertEquals(true, result);

    Assert.assertEquals(FLAG.getUrlAddress(), instance.get());
    Assert.assertEquals(line1, instance.get());
    Assert.assertEquals(line2, instance.get());
    Assert.assertEquals(line3, instance.get());
    Assert.assertEquals(line4, instance.get());
  }

  /** 测试队列的功能，超过一个buffer大小的情况时 */
  @Test
  public void test06FileQueue() {

    int maxFileQueue = 1000;
    List<String> result = new ArrayList<>(maxFileQueue);
    for (int i = 0; i < maxFileQueue; i++) {
      result.add("http://www.sohu.com/" + i);
    }

    // 将数据放入
    instance.put(result);

    List<String> getList = new ArrayList<>(maxFileQueue);

    String getBuffList = null;

    while ((getBuffList = instance.get()) != null) {
      getList.add(getBuffList);
    }

    // result.add(0, FLAG.getUrlAddress());

    Assert.assertEquals(result, getList);
  }

  /** 进行关闭后再次读取操作 */
  @Test
  public void test07FileQueue() {

    // 先执行下清理操作,再开户操作
    instance.clean();
    instance.openFileQueue();

    // 1,写入100
    int maxFileQueue = 500;
    List<String> result = new ArrayList<>(maxFileQueue);
    for (int i = 0; i < maxFileQueue; i++) {
      result.add("http://www.sohu.com/" + i);
    }

    // 将数据放入
    instance.put(result);

    List<String> compResult = new ArrayList<>(maxFileQueue);

    // 首次需要从0开始读取
    String data = instance.get();

    Assert.assertNotNull(data);

    // 读取10
    compResult.add(data);

    // 保存offset
    instance.writeOffset();
    // 再次读取offset
    instance.readOffset();

    // 再进行读取操作
    String getBuffList = null;

    while ((getBuffList = instance.get()) != null) {
      compResult.add(getBuffList);
    }

    // 再保存一次
    instance.writeOffset();

    Assert.assertEquals(result, compResult);
  }

  /** 从队列的指定位置，获取指定的行数 */
  @Test
  public void test08FileQueueReadNum() {

    // 先写入数据;
    int maxFileQueue = 100;
    List<String> result = new ArrayList<>(maxFileQueue);
    for (int i = 0; i < maxFileQueue; i++) {
      result.add("http://www.sohu.com/" + i);
    }

    instance.put(result);

    List<String> list = instance.readData(88, 10);
    Assert.assertEquals(10, list.size());
  }

  /** 测试数据 */
  @Test
  public void putBigData() {
    StringBuilder bigUrl = new StringBuilder();

    for (int i = 0; i < 8192; i++) {
      bigUrl.append(i);
    }

    instance.put(bigUrl.toString());
  }

  /** 测试数据 */
  @Test
  public void putBigDataList() {
    List<String> data = new ArrayList<>();

    StringBuilder bigUrl = new StringBuilder();

    int index = 0;
    while (bigUrl.length() < 4090) {
      bigUrl.append(index);
    }
    data.add(bigUrl.toString());

    data.add("9876543211357");
    data.add("1");
    data.add("2");
    data.add("3");
    data.add("4");
    data.add("5");

    StringBuilder bigUrl2 = new StringBuilder();
    for (int i = 4090; i < 4090 * 2; i++) {
      bigUrl2.append(i);
    }
    data.add(bigUrl2.toString());

    // 放入大集合数据
    instance.put(data);

    List<String> getList = new ArrayList<>(8);

    String getBuffList = null;

    while ((getBuffList = instance.get()) != null) {
      getList.add(getBuffList);
    }

    Assert.assertEquals(data.size(), getList.size());
  }

  /** 测试数据 */
  @Test
  public void putBigDataList2() {
    List<String> data = new ArrayList<>();

    StringBuilder bigUrl = new StringBuilder();

    int index = 0;
    while (bigUrl.length() < 4090) {
      bigUrl.append(index);
    }
    data.add(bigUrl.toString());

    index = 0;
    while (bigUrl.length() < 4100) {
      bigUrl.append(index);
    }
    data.add(bigUrl.toString());

    StringBuilder bigUrl2 = new StringBuilder();
    for (int i = 8192; i < 8192 * 2; i++) {
      bigUrl2.append(i);
    }
    data.add(bigUrl2.toString());

    // 放入大集合数据
    instance.put(data);
  }

  /** 执行清理操作 */
  @After
  public void clean() {
    instance.clean();
  }
}
