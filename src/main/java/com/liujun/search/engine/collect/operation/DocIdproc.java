package com.liujun.search.engine.collect.operation;

import com.liujun.search.algorithm.ahoCorasick.AhoCorasick;
import com.liujun.search.common.io.LocalIOUtils;
import com.liujun.search.utilscode.io.constant.PathCfg;
import com.liujun.search.utilscode.io.constant.SymbolMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 文件网页链接及其编号所对应的文件
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/05
 */
public class DocIdproc {

  /** 文件与id的对应关系 */
  private static final String DOC_ID_FILE = "doc_id.bin";

  /** url与id对应的关系的文件存储路径 */
  private static final String DOC_FILEPATH = PathCfg.BASEPATH + PathCfg.COLLEC_PATH + DOC_ID_FILE;

  /** 实例对象 */
  public static final DocIdproc INSTANCE = new DocIdproc();

  /** logger info */
  private Logger logger = LoggerFactory.getLogger(DocIdproc.class);

  /** 字符缓冲流 */
  private FileWriter write;

  /** 字符读取操作 */
  private BufferedWriter bufferWriter;

  public DocIdproc() {
    openFile();
  }

  /** 打开文件 */
  public void openFile() {

    try {
      write = new FileWriter(DOC_FILEPATH);
      bufferWriter = new BufferedWriter(write);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("DocIdproc openFile FileNotFoundException", e);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("DocIdproc openFile IOException", e);
    }
  }

  /** 关闭文件 */
  public void close() {
    LocalIOUtils.close(bufferWriter);
    LocalIOUtils.close(write);
  }

  /**
   * 存储网页的id及url信息
   *
   * @param url
   * @param id
   */
  public boolean putDoc(String url, long id) {

    String data = this.srcToFileData(url, id);
    try {
      // 进行写数据操作
      bufferWriter.write(data);
      // 换行操作
      bufferWriter.newLine();
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("DocIdproc putDoc IOException", e);
    }

    return true;
  }

  /** 将缓冲区中的数据写入磁盘中 */
  public void writeDisk() {
    try {
      bufferWriter.flush();
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("DocIdproc writeDisk IOException", e);
    }
  }

  /**
   * 通过一组id来获取查询的数据
   *
   * @param ids id信息
   * @return 结果数据
   */
  public Map<String, String> getDoc(List<String> ids) {
    // 进行参数的检查
    if (null == ids || ids.isEmpty()) {
      return null;
    }

    Map<String, String> findMap = new HashMap<>(ids.size());

    FileReader reader = null;
    BufferedReader bufferReader = null;

    try {
      // 构建多模式串匹配的ad自动机实例对象
      AhoCorasick ahoCorasick = AhoCorasick.GetAhoCorasickInstance(ids);

      reader = new FileReader(DOC_FILEPATH);
      bufferReader = new BufferedReader(reader);

      String lineData;
      String findValue;

      while ((lineData = bufferReader.readLine()) != null) {
        // 检查字符串是否存在多模式中的字符
        findValue = ahoCorasick.matchOne(lineData);

        if (null != findValue) {
          String[] dataVals = fileDataToSrc(lineData);
          // 数据行信息
          findMap.put(dataVals[1], dataVals[0]);

          // 如果当前的数据已经全部查询结束，则退出文件读取操作
          if (findMap.size() == ids.size()) {
            break;
          }
        }
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("DocIdproc getDoc FileNotFoundException", e);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("DocIdproc getDoc IOException", e);
    } finally {
      LocalIOUtils.close(bufferReader);
      LocalIOUtils.close(reader);
    }

    return findMap;
  }

  /**
   * 获取最后一个链接的id操作
   *
   * @return
   */
  public long getLastHrefId() {
    // 找到当前文件
    FileInputStream input = null;
    FileChannel channel = null;

    int lastReadSize = 512;

    // 查找最后一个链接,即查找最后一个换行符与另外一个换行符号之间的数据
    ByteBuffer buffer = ByteBuffer.allocate(lastReadSize);

    try {
      input = new FileInputStream(DOC_FILEPATH);
      channel = input.getChannel();

      // 读取文件的大小
      long channelSize = channel.size();

      // 读取最后一个buffer的大小
      long startPos = channelSize - lastReadSize;

      // 读取最后一个buffer的数据
      channel.read(buffer, startPos);
      // buffer.flip();

      // 提取出最后一行记录
      String lastData = this.getLastData(buffer);

      return getLastId(lastData);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("DocIdproc getLastHrefId FileNotFoundException:", e);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error(
          "DocIdproc getLastHrefId IOExceptio                                          n:", e);
    }

    return -1;
  }

  /**
   * 将原始数据转换为输出至文件行中的数据
   *
   * @param url url地址信息
   * @param id 文件的编号信息
   * @return 转换后的行信息
   */
  private String srcToFileData(String url, long id) {
    StringBuilder outMsg = new StringBuilder();
    outMsg.append(id);
    outMsg.append(SymbolMsg.DATA_COLUMN);
    outMsg.append(url);

    return outMsg.toString();
  }

  /**
   * 提取出文件中id信息
   *
   * @param lastData
   * @return
   */
  private long getLastId(String lastData) {
    String[] spitDataArrays = this.fileDataToSrc(lastData);

    return Long.parseLong(spitDataArrays[0]);
  }

  /**
   * 将文件中的数据行转换为数据行信息
   *
   * @param fileData 文件行信息
   * @return 数据信息 [0]url地址,[1]id信息
   */
  private String[] fileDataToSrc(String fileData) {
    return fileData.split(SymbolMsg.DATA_COLUMN);
  }

  /**
   * 先需要获取最后一行的数据
   *
   * @param buffer 缓冲区的信息
   * @return 数据信息
   */
  private String getLastData(ByteBuffer buffer) {
    // 1，查找结束的位置
    int endIndex = this.lastLineIndex(buffer, buffer.position() - 1);

    // 2,查找倒数第二个换行符
    int startIndex = this.lastLineIndex(buffer, endIndex - 1) + 1;

    // 获取数据
    int dataLength = endIndex - startIndex;
    byte[] lastLineBytes = new byte[dataLength];

    buffer.position(startIndex);
    buffer.get(lastLineBytes, 0, dataLength);

    return new String(lastLineBytes, StandardCharsets.UTF_8);
  }

  /**
   * 查找换行号的位置
   *
   * @param buffer
   * @param lastPostion
   * @return
   */
  private int lastLineIndex(ByteBuffer buffer, int lastPostion) {
    for (int i = lastPostion; i >= 0; i--) {
      if (buffer.get(i) == SymbolMsg.LINE_INT) {
        return i;
      }
    }

    throw new RuntimeException("data is error ,not find switch line flag");
  }
}
