package com.liujun.search.engine.analyze.operation.docraw.docrawReader.lineProcess;

import com.liujun.search.algorithm.boyerMoore.CommCharMatcherInstance;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.common.io.ByteUtils;
import com.liujun.search.engine.analyze.constant.DocrawReaderEnum;
import com.liujun.search.engine.analyze.pojo.RawDataLine;
import com.liujun.search.utilscode.io.constant.SymbolMsg;

import java.util.List;

/**
 * 数据转换，将数据转换为完整的网页信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/24
 */
public class BytesToEntityConvert implements FlowServiceInf {

  public static final BytesToEntityConvert INSTANCE = new BytesToEntityConvert();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    // 取出数据存储集合对象
    List<byte[]> dataList =
        context.getObject(DocrawReaderEnum.DOCRAW_PROC_CACHE_BUFFERLIST.getKey());

    // 进行对象的封装操作
    if (dataList != null && !dataList.isEmpty()) {
      byte[] covertData = ByteUtils.BytesConvert(dataList);

      // 进行转换操作
      RawDataLine data = this.getDataEntity(covertData);

      List<RawDataLine> dataLinesArrays =
          context.getObject(DocrawReaderEnum.DOCRAW_INOUTPUT_RESULT_LIST.getKey());

      dataLinesArrays.add(data);

      // 完成之后释放缓存集合
      dataList.clear();

      // 转换完成释放内存
      covertData = null;
    }

    return true;
  }

  /**
   * 获取数据内容信息
   *
   * @param dataVale 数据内容信息
   * @return 数据内容
   */
  public RawDataLine getDataEntity(byte[] dataVale) {

    int startIndex = 0;

    RawDataLine dataLine = new RawDataLine();

    // 查找得到网页的id
    int seqNumEndIndex =
        CommCharMatcherInstance.LINE_COLUMN_MATCHER.matcherIndex(dataVale, startIndex);

    // 得到网页的id
    String htmlId = new String(dataVale, startIndex, seqNumEndIndex);

    dataLine.setId(Long.parseLong(htmlId));
    // 得到长度
    int htmlLength =
        CommCharMatcherInstance.LINE_COLUMN_MATCHER.matcherIndex(dataVale, seqNumEndIndex + 1);

    if (htmlLength == -1) {
      throw new RuntimeException("curr exception:" + htmlLength);
    }

    String htmlLenChars = new String(dataVale, seqNumEndIndex + 1, htmlLength - seqNumEndIndex - 1);
    dataLine.setLength(Long.parseLong(htmlLenChars));
    htmlLength = htmlLength + 1;

    int contextLength = dataVale.length - htmlLength - SymbolMsg.LINE_OVER.length();
    // 返回最终的数据集
    String htmlContext = new String(dataVale, htmlLength, contextLength);
    dataLine.setHtmlContext(htmlContext);

    return dataLine;
  }
}
