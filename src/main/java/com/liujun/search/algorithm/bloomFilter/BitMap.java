package com.liujun.search.algorithm.bloomFilter;

import java.io.Serializable;

/**
 * 位图的操作
 *
 * <p>java 中一个char字符占用16bit,也就是2个字节
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/18
 */
public class BitMap implements Serializable {

  /** 存储位数据的字符串 */
  private char[] bytes;

  /** 位图的大小 */
  private int nbits;

  /**
   * 初始化位图的大小
   *
   * @param nbits
   */
  public BitMap(int nbits) {
    this.nbits = nbits;
    this.bytes = new char[nbits / 16 + 1];
  }

  /**
   * 找到对应值的位数
   *
   * @param k 值
   */
  public void set(int k) {
    int useValue = k;

    // 如果当前小于0,则需要进行取绝对值操作
    if (useValue < 0) {
      useValue = Math.abs(useValue);
    }

    // 如果超过了范围，进行取模运算
    if (useValue > nbits) {
      useValue = useValue % nbits;
    }

    int byteIndex = useValue / 16;
    int bitIndex = useValue % 16;

    // 将指定的位，修改为被占用
    bytes[byteIndex] |= (1 << bitIndex);
  }

  /**
   * 获取某位是否被占用
   *
   * @param k
   * @return
   */
  public boolean get(int k) {

    int getValue = k;

    // 如果当前小于0,则需要进行取绝对值操作
    if (getValue < 0) {
      getValue = Math.abs(getValue);
    }

    // 如果超过了范围，则进行取模运算
    if (getValue > nbits) {
      getValue = getValue % nbits;
    }

    int byteIndex = getValue / 16;
    int bitIndex = getValue % 16;

    return (bytes[byteIndex] & (1 << bitIndex)) != 0;
  }
}
