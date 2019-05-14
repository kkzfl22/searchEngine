package com.liujun.search.element.download.charsetFlow;

import com.liujun.search.algorithm.boyerMoore.use.CharMatcherBMBadChars;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.element.constant.HttpCharsetFlowEnum;
import com.liujun.search.element.download.HttpUtils;
import com.liujun.search.utilscode.io.constant.SysConfig;

/**
 * html4中的网页内容编码获取
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/30
 */
public class HtmlContextTypeGet implements FlowServiceInf {

  /** 最大查找NUM次数 */
  private static final int MAX_FIND_META_NUM = 100;

  /** 网页标签meat中的编码属性信息 */
  private static final CharMatcherBMBadChars CONTEXT_PROPERTIES_MATCHER =
      CharMatcherBMBadChars.getGoodSuffixInstance("http-equiv=\"content-type\"");

  /** 网页标签meat中的编码信息开始位置字符 */
  private static final String CHAR_HTML_START = "content=\"";
  /** 网页标签meat中的编码信息结束位置字符 */
  private static final String CHAR_HTML_END = "\"";

  /** 网页标签meat中的编码信息开始位置匹配 */
  private static final CharMatcherBMBadChars CHARSET_CONTEXT_START =
      CharMatcherBMBadChars.getGoodSuffixInstance(CHAR_HTML_START);

  /** 网页标签meat中的编码信息结束位置匹配 */
  private static final CharMatcherBMBadChars CHARSET_CONTEXT_END =
      CharMatcherBMBadChars.getGoodSuffixInstance(CHAR_HTML_END);

  // ********************************************************************************

  private static final String META_CHAR = "<meta";

  /** 查询网页标签META */
  private static final CharMatcherBMBadChars META_MATCHER_START =
      CharMatcherBMBadChars.getGoodSuffixInstance(META_CHAR);

  /** 查询网页标签META结束 */
  private static final CharMatcherBMBadChars META_MATCHER_END =
      CharMatcherBMBadChars.getGoodSuffixInstance(">");

  /** 网页标签meat中的编码信息开始位置字符 */
  private static final String CHARSET_START = "charset=";
  /** 网页标签meat中的编码信息结束位置字符 */
  private static final String CHARSET_END = "\"";

  /** 网页标签meat中的编码信息开始位置匹配 */
  private static final CharMatcherBMBadChars CHARSETHTML5_CONTEXT_START =
      CharMatcherBMBadChars.getGoodSuffixInstance(CHARSET_START);

  /** 网页标签meat中的编码信息结束位置匹配 */
  private static final CharMatcherBMBadChars CHARSETHTML5_CONTEXT_END =
      CharMatcherBMBadChars.getGoodSuffixInstance(CHARSET_END);

  public static final HtmlContextTypeGet INSTANCE = new HtmlContextTypeGet();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    byte[] dataArraysInput =
        context.getObject(HttpCharsetFlowEnum.CHARSET_INPUT_INPUTARRAYS.getKey());

    int bufferLength = SysConfig.SYS_DEFA_BUFFER_SIZE;

    if (bufferLength > dataArraysInput.length) {
      bufferLength = dataArraysInput.length;
    }

    // 定义char数据内容
    String dataValue = new String(dataArraysInput, 0, bufferLength);
    char[] dataArrays = dataValue.toCharArray();

    String charset = this.getHtml4Charset(dataArrays);

    if (null == charset) {
      charset = this.getHtml5Charset(dataArrays);
    }

    dataValue = null;
    dataArrays = null;

    if (null != charset) {

      // 网页编码信息
      context.put(HttpCharsetFlowEnum.CHARSET_PROC_GETCHARSET.getKey(), charset);
      return true;
    }
    // 获取不到网页编码信息，则跳过此网页
    return false;
  }

  /**
   * 获取html4中的网页编码
   *
   * @param dataArrays 网页内容
   * @return 网页信息
   */
  private String getHtml4Charset(char[] dataArrays) {
    String outDataValue = null;

    // 查找http-equiv属性
    int matchIndex = CONTEXT_PROPERTIES_MATCHER.matcherIndexIgnoreCase(dataArrays, 0);

    if (matchIndex != -1) {
      int startIndex = CHARSET_CONTEXT_START.matcherIndex(dataArrays, matchIndex);

      // 如果匹配到开始字符
      if (startIndex != -1) {

        startIndex = startIndex + CHAR_HTML_START.length();
        // 匹配结束字符
        int endIndex = CHARSET_CONTEXT_END.matcherIndex(dataArrays, startIndex);

        String outCharCon = new String(dataArrays, startIndex, endIndex - startIndex);

        String charsetCon = HttpUtils.ContextTypeCharset(outCharCon);

        if (charsetCon != null) {
          outDataValue = charsetCon;
        }
      }
    }

    return outDataValue;
  }

  /**
   * 获取html5中的网页编码
   *
   * @param dataArrays 网页内容
   * @return 编码信息
   */
  private String getHtml5Charset(char[] dataArrays) {
    int startPos = 0;
    String outDataValue = null;

    int num = 0;

    while (num < MAX_FIND_META_NUM) {
      // 查找<meta开始
      int matchIndex = META_MATCHER_START.matcherIndex(dataArrays, startPos);

      if (matchIndex == -1) {
        break;
      }

      matchIndex = matchIndex + META_CHAR.length();

      // 查找字符属性charset
      int chsetIndex = CHARSETHTML5_CONTEXT_START.matcherIndex(dataArrays, matchIndex);
      // 查找meat的结束
      int endIndex = META_MATCHER_END.matcherIndex(dataArrays, matchIndex);

      // 检查是否合要求
      if (chsetIndex != -1 && -1 != endIndex) {
        if (matchIndex <= chsetIndex && chsetIndex <= endIndex) {
          // 需要加1，因为还有一个引号占用
          chsetIndex = chsetIndex + CHARSET_START.length() + 1;

          int endPost = CHARSETHTML5_CONTEXT_END.matcherIndex(dataArrays, chsetIndex);
          outDataValue = new String(dataArrays, chsetIndex, endPost - chsetIndex);

          break;
        } else {
          startPos = matchIndex;
        }
      } else {
        break;
      }

      num++;
    }

    if (MAX_FIND_META_NUM == num) {
      throw new RuntimeException("start getHtml5Charset loop exception");
    }

    return outDataValue;
  }
}
