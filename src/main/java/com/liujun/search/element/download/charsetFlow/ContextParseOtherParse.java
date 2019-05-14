package com.liujun.search.element.download.charsetFlow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.element.constant.HttpCharsetFlowEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 检查内容是否为其他编码，设置了其他编码，需要进行两次转码操作，则直接转换
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/30
 */
public class ContextParseOtherParse implements FlowServiceInf {

  /** 实例 */
  public static final ContextParseOtherParse INSTANCE = new ContextParseOtherParse();

  /** 日志信息 */
  public Logger logger = LoggerFactory.getLogger(ContextParseOtherParse.class);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    String charSetStr = context.getObject(HttpCharsetFlowEnum.CHARSET_PROC_GETCHARSET.getKey());

    Charset charset = Charset.forName(charSetStr);

    if (null == charset) {
      logger.error("charset {} error", charSetStr);
      throw new RuntimeException("charset error:" + charSetStr);
    }

    byte[] dataArrays = context.getObject(HttpCharsetFlowEnum.CHARSET_INPUT_INPUTARRAYS.getKey());
    // 使用原来的编码进行编译成字符
    String outDataValue = new String(dataArrays, charset);

    // 再统一转换到UTF-8的编码中
    outDataValue = new String(outDataValue.getBytes(StandardCharsets.UTF_8));

    context.put(HttpCharsetFlowEnum.CHARSET_OUTPU_HTMLCONTEXT.getKey(), outDataValue);

    return true;
  }
}
