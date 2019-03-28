package com.liujun.search.engine.collect.operation.docrawfind;

import com.liujun.search.algorithm.boyerMoore.use.CharMatcherBMBadChars;
import com.liujun.search.utilscode.io.constant.SymbolMsg;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.DocRawFindEnum;

import java.util.List;

/**
 * 数据转换，将数据转换为完整的网页信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/24
 */
public class DataConvert implements FlowServiceInf {

  /** 列分隔符对象 */
  private CharMatcherBMBadChars LINE_COLUMN_MATCHER_FLAG =
      CharMatcherBMBadChars.getGoodSuffixInstance(SymbolMsg.DATA_COLUMN);

  public static final DataConvert INSTANCE = new DataConvert();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    // 取出数据存储集合对象
    List<byte[]> dataList = context.getObject(DocRawFindEnum.PROC_COLLECT_OUT_DATA.getKey());

    byte[] outDataBytes = null;
    if (dataList.size() > 1) {
      int arrayLength = 0;

      // 统计得到整个网页的字符大小
      for (byte[] datas : dataList) {
        arrayLength += datas.length;
      }

      outDataBytes = new byte[arrayLength];

      int startPostion = 0;

      byte[] currOut = null;
      // 将所有数据拼命到一个字符数组中
      for (int i = 0; i < dataList.size(); i++) {
        currOut = dataList.get(i);
        System.arraycopy(currOut, 0, outDataBytes, startPostion, currOut.length);
        startPostion += currOut.length;
      }
    } else {
      outDataBytes = dataList.get(0);
    }

    String outdataContext = getDataContext(outDataBytes);
    context.put(DocRawFindEnum.OUT_FIND_DATA_CONTEXT.getKey(), outdataContext);
    context.put(DocRawFindEnum.OUT_FIND_END_FLAG.getKey(), true);

    return false;
  }

  /**
   * 获取数据内容信息
   *
   * @param dataVale 数据内容信息
   * @return 数据内容
   */
  public String getDataContext(byte[] dataVale) {

    int startIndex = 0;

    // 查找得到网页的id
    startIndex = LINE_COLUMN_MATCHER_FLAG.matcherIndex(dataVale, startIndex);
    // 得到长度
    startIndex = LINE_COLUMN_MATCHER_FLAG.matcherIndex(dataVale, startIndex + 1);
    startIndex = startIndex + 1;
    // 返回最终的数据集
    return new String(dataVale, startIndex, dataVale.length - startIndex);
  }
}
