package com.liujun.search.common.utils;

/**
 * 字符编码转换方法
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/24
 */
public class ByteCode {

  /**
   * 将字符转换为byte的特殊方法
   *
   * <p>按char字符的长度，来转换为byte的长度，中文切分将出现问题
   *
   * @param patterChar 字符串，
   * @return byte信息
   */
  public static byte[] GetBytes(String patterChar) {
    char[] cs = patterChar.toCharArray();
    // 定义一个长度与需要转换的char数组相同的byte数组
    byte[] bs2 = new byte[cs.length];
    // 循环将char数组的每一个元素转换为byte并存在上面定义的byte数组中
    for (int i = 0; i < cs.length; i++) {
      // 将每一个char转换成byte
      byte b = (byte) cs[i];
      // 保存到数组中
      bs2[i] = b;
    }

    return bs2;
  }
}
