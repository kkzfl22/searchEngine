package com.liujun.search.algorithm.bloomFilter;

import com.liujun.search.common.io.CommonIOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 布隆过滤器的序列化操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/18
 */
public abstract class BloomFilterSerializable extends BloomFilter implements Serializable {

  /** 日志对象 */
  private Logger logger = LoggerFactory.getLogger(BloomFilter.class);

  protected BloomFilterSerializable(int nbits) {
    super(nbits);
  }

  /**
   * 输出的文件
   *
   * @return
   */
  protected abstract String getOutPathFile();

  /** 进行数据保存操作 */
  public void save() {
    // 对数据进行序列化保存操作,加入缓冲区
    OutputStream fileObjOutput = null;
    BufferedOutputStream bufferedOutput = null;
    ObjectOutputStream oos = null;
    try {
      String outPath = getOutPathFile();

      fileObjOutput = new FileOutputStream(outPath);
      bufferedOutput = new BufferedOutputStream(fileObjOutput);
      oos = new ObjectOutputStream(bufferedOutput);
      oos.writeObject(bloomFilter);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("BloomFilterSerializable save IOException", e);
    } finally {
      CommonIOUtils.close(oos);
      CommonIOUtils.close(bufferedOutput);
      CommonIOUtils.close(fileObjOutput);
    }
  }

  /** 从本地文件中加载布隆过滤器的数据 */
  public void loader() {

    String outPath = getOutPathFile();

    File bloomFile = new File(outPath);

    // 加载需要检查文件是否存在，首次文件是不存在的
    if (!bloomFile.exists()) {
      return;
    }

    InputStream inputStream = null;
    BufferedInputStream bufferInput = null;
    ObjectInputStream objInputStream = null;

    try {
      // 加载数据
      inputStream = new FileInputStream(bloomFile);
      bufferInput = new BufferedInputStream(inputStream);
      objInputStream = new ObjectInputStream(bufferInput);

      BitMap bitMap = (BitMap) objInputStream.readObject();
      this.bloomFilter = bitMap;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("BloomFilterSerializable loader FileNotFoundException", e);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("BloomFilterSerializable loader IOException", e);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      logger.error("BloomFilterSerializable loader ClassNotFoundException", e);
    } finally {
      CommonIOUtils.close(objInputStream);
      CommonIOUtils.close(bufferInput);
      CommonIOUtils.close(inputStream);
    }
  }

  public void clean() {
    File delFile = new File(this.getOutPathFile());

    if (delFile.exists()) {
      delFile.delete();
    }
  }
}
