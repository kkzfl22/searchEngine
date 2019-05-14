package com.liujun.search.element.download.charsetFlow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.element.constant.HttpCharsetFlowEnum;
import org.apache.http.entity.ContentType;

import java.nio.charset.StandardCharsets;

/**
 * 检查内容是否为UTF8，如果是，则直接转换
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/30
 */
public class ContextTypeUtf8Parse implements FlowServiceInf {

  /** 实例 */
  public static final ContextTypeUtf8Parse INSTANCE = new ContextTypeUtf8Parse();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    ContentType type = context.getObject(HttpCharsetFlowEnum.CHARSET_INPUT_CONTEXTYPE.getKey());

    if (null != type
        && type.getCharset() != null
        && StandardCharsets.UTF_8.equals(type.getCharset())) {
      byte[] dataArrays = context.getObject(HttpCharsetFlowEnum.CHARSET_INPUT_INPUTARRAYS.getKey());
      // 针对当前文本的网页编码结束，返回对应的解码数据
      String outDataValue = new String(dataArrays, StandardCharsets.UTF_8);
      context.put(HttpCharsetFlowEnum.CHARSET_OUTPU_HTMLCONTEXT.getKey(), outDataValue);

      return false;
    }

    return true;
  }
}
