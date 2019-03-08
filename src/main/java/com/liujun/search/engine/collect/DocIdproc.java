package com.liujun.search.engine.collect;

import com.liujun.search.common.constant.PathCfg;
import com.liujun.search.common.constant.SymbolMsg;
import com.liujun.search.common.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

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
  private void openFile() {
    String pathPath = PathCfg.COLLEC_PATH + PathCfg.COLLEC_PATH + DOC_ID_FILE;

    try {
      write = new FileWriter(pathPath);
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
  private void close() {
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
    StringBuilder outMsg = new StringBuilder();

    outMsg.append(url);
    outMsg.append(SymbolMsg.DATA_COLUMN);
    outMsg.append(id);

    try {
      // 进行写数据操作
      bufferWriter.write(outMsg.toString());
      // 换行操作
      bufferWriter.newLine();
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("DocIdproc putDoc IOException", e);
    }

    return true;
  }

  /**
   * 通过一组id来获取查询的数据
   *
   * @param ids id信息
   * @return 结果数据
   */
  public List<String> getDoc(List<Long> ids) {
    return null;
  }
}
