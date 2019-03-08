package com.liujun.search.engine.collect;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于测试链接与与id的对应关系
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/05
 */
// Junit测试顺序：@FixMethodOrder
// ** MethodSorters.DEFAULT **（默认）
// 默认顺序由方法名hashcode值来决定，如果hash值大小一致，则按名字的字典顺序确定。
// ** MethodSorters.NAME_ASCENDING （推荐） **
//   按方法名称的进行排序，由于是按字符的字典顺序，所以以这种方式指定执行顺序会始终保持一致；
// ** MethodSorters.JVM **
//    按JVM返回的方法名的顺序执行，此种方式下测试方法的执行顺序是不可预测的，即每次运行的顺序可能
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDocIdProc {

  /** 测试数据放入操作 */
  @Test
  public void test01putDocId() {
    // 内容信息
    String url = "http://www.baidu.com";
    // id信息
    long id = 0;
    // 网页与对应的id的关系
    boolean rsp = DocIdproc.INSTANCE.putDoc(url, id);
    Assert.assertEquals(true, rsp);

    // 网页与对应的id的关系
    boolean rsp1 = DocIdproc.INSTANCE.putDoc("http://www.163.com", 1);
    Assert.assertEquals(true, rsp1);

    // 网页与对应的id的关系
    boolean rsp2 = DocIdproc.INSTANCE.putDoc("http://www.taobao.com", 2);
    Assert.assertEquals(true, rsp2);
  }

  /** 测试数据的获取操作 */
  @Test
  public void test02getDocId() {
    List<Long> docIds = new ArrayList<>();
    docIds.add(0l);
    docIds.add(1l);
    docIds.add(2l);

    List<String> values = DocIdproc.INSTANCE.getDoc(docIds);

    Assert.assertNotNull(values);
  }
}
