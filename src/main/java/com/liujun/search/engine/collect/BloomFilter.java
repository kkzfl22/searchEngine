package com.liujun.search.engine.collect;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.liujun.search.common.constant.SysPropertyEnum;
import com.liujun.search.common.io.IOUtils;
import com.liujun.search.common.properties.SysPropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 布隆过滤器
 *
 * <p>用来实现网页判重的功能
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/04
 */
public class BloomFilter implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  /** 基础路径 */
  private static final String BASEPATH =
      SysPropertiesUtils.getInstance().getValue(SysPropertyEnum.FILE_PROCESS_PATH);

  /** 数据收集的目录 */
  private static final String COLLEC_PATH = "collect/";

  /** 待爬取网页链接文件 */
  private static final String BLOOM_FILTER_FILE = "bloom_filter.bin";

  /** 布隆过滤器文件 */
  private static final String PROCESS_BLOOM_FILTER = BASEPATH + COLLEC_PATH + BLOOM_FILTER_FILE;

  /** 日志对象 */
  private Logger logger = LoggerFactory.getLogger(BloomFilter.class);

  /** bitmap对象信息 */
  private BitMap bloomFilter;

  /** 默认的大小,2亿 */
  private static final int DEFAULT_BITES = 200000000;

  /** 默认的布隆过滤器实例 */
  public static final BloomFilter INSTANCE = new BloomFilter(DEFAULT_BITES);

  public BloomFilter(int nbits) {
    bloomFilter = new BitMap(nbits);
  }

  /**
   * 位图的操作
   *
   * <p>java 中一个char字符占用16bit,也就是2个字节
   */
  private class BitMap implements Serializable {

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

  /**
   * 将数据放入到布隆过滤器中
   *
   * @param data
   */
  public void putData(String data) {
    int mumur3Code = Hashing.murmur3_32().hashString(data, Charsets.UTF_8).hashCode();
    int crc32Code = Hashing.crc32().hashString(data, Charsets.UTF_8).hashCode();
    int adlerCode = Hashing.adler32().hashString(data, Charsets.UTF_8).hashCode();

    this.bloomFilter.set(mumur3Code);
    this.bloomFilter.set(crc32Code);
    this.bloomFilter.set(adlerCode);
  }

  /**
   * 判断是否存在
   *
   * @param data 数据信息
   * @return true 当前存在 false 当前不存在
   */
  public boolean exists(String data) {
    int mumur3Code = Hashing.murmur3_32().hashString(data, Charsets.UTF_8).hashCode();
    int crc32Code = Hashing.crc32().hashString(data, Charsets.UTF_8).hashCode();
    int adlerCode = Hashing.adler32().hashString(data, Charsets.UTF_8).hashCode();

    boolean mumur3Flag = this.bloomFilter.get(mumur3Code);
    boolean crc32Flag = this.bloomFilter.get(crc32Code);
    boolean adlerFlag = this.bloomFilter.get(adlerCode);

    if (mumur3Flag && crc32Flag && adlerFlag) {
      return true;
    }
    return false;
  }

  /** 进行数据保存操作 */
  public void save() {

    // 对数据进行序列化保存操作,加入缓冲区
    OutputStream fileObjOutput = null;
    BufferedOutputStream bufferedOutput = null;
    ObjectOutputStream oos = null;
    try {
      fileObjOutput = new FileOutputStream(PROCESS_BLOOM_FILTER);
      bufferedOutput = new BufferedOutputStream(fileObjOutput);
      oos = new ObjectOutputStream(bufferedOutput);
      oos.writeObject(bloomFilter);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("BloomFilter save IOException", e);
    } finally {
      IOUtils.close(oos);
      IOUtils.close(bufferedOutput);
      IOUtils.close(fileObjOutput);
    }
  }

  /** 从本地文件中加载布隆过滤器的数据 */
  public void loader() {

    InputStream inputStream = null;
    BufferedInputStream bufferInput = null;
    ObjectInputStream objInputStream = null;

    try {
      // 加载数据
      inputStream = new FileInputStream(PROCESS_BLOOM_FILTER);
      bufferInput = new BufferedInputStream(inputStream);
      objInputStream = new ObjectInputStream(bufferInput);

      BitMap bitMap = (BitMap) objInputStream.readObject();
      this.bloomFilter = bitMap;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("BloomFilter loader FileNotFoundException", e);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("BloomFilter loader IOException", e);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      logger.error("BloomFilter loader ClassNotFoundException", e);
    } finally {
      IOUtils.close(objInputStream);
      IOUtils.close(bufferInput);
      IOUtils.close(inputStream);
    }
  }
}
