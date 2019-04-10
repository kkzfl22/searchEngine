package com.liujun.search.common.io;

import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/09
 */
public class ByteUtils {

  /**
   * 将byte集合转换为一个完整的byte数组
   *
   * @param dataList byte数组
   * @return 拼命
   */
  public static byte[] BytesConvert(List<byte[]> dataList) {
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

    return outDataBytes;
  }

}
