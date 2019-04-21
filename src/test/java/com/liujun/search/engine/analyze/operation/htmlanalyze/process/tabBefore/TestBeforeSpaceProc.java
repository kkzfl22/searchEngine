package com.liujun.search.engine.analyze.operation.htmlanalyze.process.tabBefore;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * 测试字符的空格替换操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/19
 */
public class TestBeforeSpaceProc {

  @Test
  public void testSpaceArrayMove() {
    String context = "< a href='baidu.com'>baidu</ a>";
    char[] conArray = context.toCharArray();

    BeforeSpaceProc.INSTANCE.moveContext(conArray, 1, 1);

    System.out.println(Arrays.toString(conArray));
    System.out.println(new String(conArray));
  }

  /** 进行前后标签的空格处理 */
  @Test
  public void testSpaceArrayCon() {
    String context = "< a href='baidu.com'>baidu</ a>";
    char[] conArray = context.toCharArray();

    int moveNum = BeforeSpaceProc.INSTANCE.tagSpaceMove(conArray);

    System.out.println(new String(conArray, 0, conArray.length - moveNum));
    Assert.assertEquals(2, moveNum);
  }

  /** 进行略复杂的处理 */
  @Test
  public void testSpaceArrayCon3() {
    String context =
        "< a href=\" \" target=\"_blank\">小电科技</ a>\n"
            + "< a href=\"https://tousu.sina.com.cn/complaint/view/17347320179/\" target=\"_blank\">"
            + "租用小电充电宝已经及时归还却被收费99元。"
            + "2月14日晚6点30分左右在南京市碑亭巷俺村活鱼使用支付宝租用小电充电宝，"
            + "并于当晚7点25分左右归还充电宝，正确插入卡槽，充电宝指示灯亮起。 "
            + "2月19日早上7点30分左右收到支付宝扣款99元消息。</ a>\n"
            + "< a href=\"https://tousu.sina.com.cn/complaint/view/17347320024/\" "
            + "target=\"_blank\">京东客服</ a>"
            + "< a href=\"https://tousu.sina.com.cn/complaint/view/17347320024/\" "
            + "target=\"_blank\">就算在质保内也不能质保！"
            + "京东销售方当时购买时并没有给发票及并没告知需要保留外包装！"
            + "本来不给发票就有逃税嫌疑！要求京东履行质保义务。</ a>";
    char[] conArray = context.toCharArray();

    int moveNum = BeforeSpaceProc.INSTANCE.tagSpaceMove(conArray);

    System.out.println(new String(conArray, 0, conArray.length - moveNum));
    Assert.assertEquals(8, moveNum);
  }
}
