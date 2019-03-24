package com.liujun.search.engine.collect.operation.docrawfind;

import com.liujun.search.algorithm.boyerMoore.use.CharMatcherBMBadChars;
import com.liujun.search.common.constant.SymbolMsg;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.DocRawFindEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 进行数据行结束位置的查找
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/24
 */
public class FindDataEnd implements FlowServiceInf {

  /** 实例对象 */
  public static final FindDataEnd INSTANCE = new FindDataEnd();

  /** 行结束查找对象 */
  private static final CharMatcherBMBadChars LINE_OVER_MATCHER_FLAG =
      CharMatcherBMBadChars.getGoodSuffixInstance(SymbolMsg.LINE_OVER);

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    byte[] data = context.getObject(DocRawFindEnum.INPUT_SRC_CHARS.getKey());

    // 数据集合
    List<byte[]> dataBytes = context.getObject(DocRawFindEnum.PROC_COLLECT_OUT_DATA.getKey());

    int startIndex = 0;
    // 如果还未存储数据，说明首次被找到开始位置,则从匹配的数字位置向后查找
    if (null == dataBytes) {
      startIndex = context.getObject(DocRawFindEnum.PROC_FIND_ID_INDEX.getKey());
    }
    int endIndex = LINE_OVER_MATCHER_FLAG.matcherIndex(data, startIndex);

    // 如果当前结束标识被找到，则继续处理，
    if (endIndex != -1) {
      context.put(DocRawFindEnum.PROC_FIND_END_INDEX.getKey(), endIndex);

      // 获取集合数据
      List<byte[]> outList = this.getList(startIndex, endIndex, data, dataBytes);

      if (dataBytes == null) {
        context.put(DocRawFindEnum.PROC_COLLECT_OUT_DATA.getKey(), outList);
      }

      return true;
    }
    // 当前未找到，只需要记录下开始部分的数据
    else {
      int sumData = data.length;

      List<byte[]> outList = this.getList(startIndex, sumData, data, dataBytes);

      if (dataBytes == null) {
        context.put(DocRawFindEnum.PROC_COLLECT_OUT_DATA.getKey(), outList);
      }

      return false;
    }
  }

  /**
   * 获取集合对象
   *
   * @param startIndex 开始位置
   * @param endIndex 结束位置
   * @param currData 当前数据
   * @param dataBytes 存储数据的集合
   * @return 获取存储数据集合对象
   */
  private List<byte[]> getList(
      int startIndex, int endIndex, byte[] currData, List<byte[]> dataBytes) {

    List<byte[]> outDataList = dataBytes;

    int dataLength = endIndex - startIndex;
    byte[] outData = new byte[dataLength];
    System.arraycopy(currData, startIndex, outData, 0, dataLength);

    if (outDataList == null) {
      outDataList = new ArrayList<>();
    }
    outDataList.add(outData);
    return outDataList;
  }
}
