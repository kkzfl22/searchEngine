package com.liujun.search.element.download.charsetFlow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.element.constant.HttpCharsetFlowEnum;
import org.apache.http.entity.ContentType;

import java.nio.charset.StandardCharsets;

/**
 * 检查内容是否为其他编码，设置了编码，则直接转换
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/30
 */
public class ContextTypeOtherParse implements FlowServiceInf {

  /** 实例 */
  public static final ContextTypeOtherParse INSTANCE = new ContextTypeOtherParse();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    ContentType type = context.getObject(HttpCharsetFlowEnum.CHARSET_INPUT_CONTEXTYPE.getKey());

    if (null != type && type.getCharset() != null) {
      byte[] dataArrays = context.getObject(HttpCharsetFlowEnum.CHARSET_INPUT_INPUTARRAYS.getKey());
      // 使用原来的编码进行编译成字符
      String outDataValue = new String(dataArrays, type.getCharset());
      // 再统一转换到UTF-8的编码中
      outDataValue = new String(outDataValue.getBytes(StandardCharsets.UTF_8));

      context.put(HttpCharsetFlowEnum.CHARSET_OUTPU_HTMLCONTEXT.getKey(), outDataValue);

      return false;
    }

    return true;
  }
}
