package com.liujun.search.engine.collect.operation;

import com.liujun.search.common.number.NumberSeq;
import com.liujun.search.engine.collect.operation.DocRawProc;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * 进行测试网的存取功能
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
public class TestHtmlRawProc {

  /** 获取id */
  private long seqId;

  /** 测试html网页的保存功能 */
  @Test
  public void test01SaveHtmlRaw() {

    seqId = NumberSeq.INSTANCE.NextSeqValue();

    String html = "";

    // 将数据保存到文件中
    DocRawProc.INSTANCE.putHtml(seqId, html);
  }

  /** 通过id去获取html的内容信息 */
  public void testGetHtmlRaw() {
    // 将数据保存到文件中
    DocRawProc.INSTANCE.getHtml(seqId);
  }
}
