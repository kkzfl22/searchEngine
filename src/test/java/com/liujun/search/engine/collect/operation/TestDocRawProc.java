package com.liujun.search.engine.collect.operation;

import com.liujun.search.common.number.NumberSeq;
import com.liujun.search.engine.collect.operation.DocRawProc;
import com.liujun.search.utilscode.element.constant.HtmlHrefFileEnum;
import com.liujun.search.utilscode.element.html.HtmlHrefUtils;
import org.junit.After;
import org.junit.Assert;
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
public class TestDocRawProc {

  /** 测试html网页的保存功能 */
  @Test
  public void test01SaveHtmlRaw() {

    long seqId = 100000;

    String sinaHtml = HtmlHrefUtils.INSTANCE.getHtmlContext(HtmlHrefFileEnum.SINA);
    // 将数据保存到文件中
    DocRawProc.INSTANCE.open();
    DocRawProc.INSTANCE.putHtml(seqId, sinaHtml);
    DocRawProc.INSTANCE.close();
    String getData = DocRawProc.INSTANCE.getHtml(seqId);

    Assert.assertEquals(sinaHtml, getData);

    long seqId163 = 100001;
    String wy163Html = HtmlHrefUtils.INSTANCE.getHtmlContext(HtmlHrefFileEnum.WY163);
    // 将数据保存到文件中
    DocRawProc.INSTANCE.open();
    DocRawProc.INSTANCE.putHtml(seqId163, wy163Html);
    DocRawProc.INSTANCE.close();
    String getWy163Data = DocRawProc.INSTANCE.getHtml(seqId163);

    Assert.assertEquals(wy163Html, getWy163Data);
  }

  /** 测试html网页的保存功能 */
  @Test
  public void test02SmallData() {

    long seqId = 100010;
    long smaillId = 100011;
    long wy163Id = 100012;

    String sinaHtml = HtmlHrefUtils.INSTANCE.getHtmlContext(HtmlHrefFileEnum.SINA);

    String smallHtml = HtmlHrefUtils.INSTANCE.getHtmlContext(HtmlHrefFileEnum.SMALL);
    String wy163Html = HtmlHrefUtils.INSTANCE.getHtmlContext(HtmlHrefFileEnum.WY163);
    // 将数据保存到文件中
    DocRawProc.INSTANCE.open();
    DocRawProc.INSTANCE.putHtml(seqId, sinaHtml);
    DocRawProc.INSTANCE.putHtml(smaillId, smallHtml);
    DocRawProc.INSTANCE.putHtml(wy163Id, wy163Html);

    DocRawProc.INSTANCE.close();
    String getSmailData = DocRawProc.INSTANCE.getHtml(smaillId);
    Assert.assertEquals(smallHtml, getSmailData);

    String getwy163Data = DocRawProc.INSTANCE.getHtml(wy163Id);
    Assert.assertEquals(wy163Html, getwy163Data);
  }

  /** 执行最后的清理操作 */
  @After
  public void after() {
    DocRawProc.INSTANCE.clean();
  }
}
