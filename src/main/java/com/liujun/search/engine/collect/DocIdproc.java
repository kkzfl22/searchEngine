package com.liujun.search.engine.collect;

import com.liujun.search.algorithm.ahoCorasick.AhoCorasick;
import com.liujun.search.common.constant.PathCfg;
import com.liujun.search.common.constant.SymbolMsg;
import com.liujun.search.common.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  public DocIdproc() {}

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
    IOUtils.close(bufferWriter);
    IOUtils.close(write);
  }

  /**
   * 存储网页的id及url信息
   *
   * @param url
   * @param id
   */
  public boolean putDoc(String url, long id) {

    try {
      // 进行写数据操作
      bufferWriter.write(this.srcToFileData(url, id));
      // 换行操作
      bufferWriter.newLine();
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("DocIdproc putDoc IOException", e);
    }

    return true;
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

    outMsg.append(url);
    outMsg.append(SymbolMsg.DATA_COLUMN);
    outMsg.append(id);

    return outMsg.toString();
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
      IOUtils.close(bufferReader);
      IOUtils.close(reader);
    }

    return findMap;
  }
}
