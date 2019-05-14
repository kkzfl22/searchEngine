package com.liujun.search.element.download.charsetFlow;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.element.constant.HttpCharsetFlowEnum;
import org.apache.http.entity.ContentType;

import java.util.ArrayList;
import java.util.List;

/**
 * 进行网页的编码流程处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/30
 */
public class HtmlCharsetFlow {

  private static final List<FlowServiceInf> FLOWLIST = new ArrayList<>();

  static {
    // 优先进行已经配制了编码的转码
    FLOWLIST.add(ContextTypeUtf8Parse.INSTANCE);
    FLOWLIST.add(ContextTypeOtherParse.INSTANCE);

    // 读取编码
    FLOWLIST.add(HtmlContextTypeGet.INSTANCE);
    // 进行utf8转码
    FLOWLIST.add(ContextParseUtf8Parse.INSTANCE);
    FLOWLIST.add(ContextParseOtherParse.INSTANCE);
  }

  public static final HtmlCharsetFlow INSTANCE = new HtmlCharsetFlow();

  /**
   * 进行网页的解码，并统一输出为UTF-8
   *
   * @param htmlCode
   * @return
   */
  public String htmlCharsetValue(byte[] htmlCode, ContentType type) throws Exception {
    FlowServiceContext context = new FlowServiceContext();

    context.put(HttpCharsetFlowEnum.CHARSET_INPUT_INPUTARRAYS.getKey(), htmlCode);
    context.put(HttpCharsetFlowEnum.CHARSET_INPUT_CONTEXTYPE.getKey(), type);

    for (FlowServiceInf item : FLOWLIST) {
      if (!item.runFlow(context)) {
        break;
      }
    }

    return context.getObject(HttpCharsetFlowEnum.CHARSET_OUTPU_HTMLCONTEXT.getKey());
  }
}
