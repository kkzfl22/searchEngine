package com.liujun.search.algorithm.bloomFilter;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

/**
 * 布隆过滤器
 *
 * <p>用来实现网页判重的功能
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/04
 */
public class BloomFilter {

  /** bitmap对象信息 */
  protected BitMap bloomFilter;

  public BloomFilter(int nbits) {
    bloomFilter = new BitMap(nbits);
  }

  /**
   * 将数据放入到布隆过滤器中
   *
   * @param data
   */
  public void putData(String data) {
    int mumur3Code = Hashing.murmur3_32().hashString(data, Charsets.UTF_8).hashCode();
    int mumurCode2 =
        Hashing.murmur3_32(Integer.MAX_VALUE / 2).hashString(data, Charsets.UTF_8).hashCode();
    int mumur128Code =
        Hashing.murmur3_128(Integer.MAX_VALUE).hashString(data, Charsets.UTF_8).hashCode();

    this.bloomFilter.set(mumur3Code);
    this.bloomFilter.set(mumurCode2);
    this.bloomFilter.set(mumur128Code);
  }

  /**
   * 判断是否存在
   *
   * @param data 数据信息
   * @return true 当前存在 false 当前不存在
   */
  public boolean exists(String data) {
    int mumur3Code = Hashing.murmur3_32().hashString(data, Charsets.UTF_8).hashCode();
    int mumurCode2 =
        Hashing.murmur3_32(Integer.MAX_VALUE / 2).hashString(data, Charsets.UTF_8).hashCode();
    int mumur128Code =
        Hashing.murmur3_128(Integer.MAX_VALUE).hashString(data, Charsets.UTF_8).hashCode();

    boolean mumur3Flag = this.bloomFilter.get(mumur3Code);
    boolean crc32Flag = this.bloomFilter.get(mumurCode2);
    boolean adlerFlag = this.bloomFilter.get(mumur128Code);

    if (mumur3Flag && crc32Flag && adlerFlag) {
      return true;
    }
    return false;
  }
}
