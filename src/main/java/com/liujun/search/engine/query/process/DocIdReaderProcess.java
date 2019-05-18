package com.liujun.search.engine.query.process;

import com.liujun.search.common.io.CommonIOUtils;
import com.liujun.search.engine.collect.operation.DocIdproc;
import com.liujun.search.engine.query.pojo.DocIdBusi;
import com.liujun.search.common.constant.PathCfg;
import com.liujun.search.common.constant.SymbolMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * 进行文件id与网页链接的处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/13
 */
public class DocIdReaderProcess {

  /** 网页单词与编号之间的关系 */
  private static final String DOC_FILEPATH =
      PathCfg.BASEPATH + PathCfg.COLLEC_PATH + DocIdproc.DOC_ID_FILE;

  /** 默认的map初始化大小 */
  private static final int INIT_MAP_SIZE = 65536;

  /** 实例对象 */
  public static final DocIdReaderProcess INSTANCE = new DocIdReaderProcess();

  private Logger logger = LoggerFactory.getLogger(DocIdReaderProcess.class);

  /**
   * 读取网页编码与网页链接的对应文件
   *
   * @param cacheMap 进行网页id与对应的url的存储map
   * @return 网页id与网页地址对应
   */
  public void loadDocMap(Map<Long, String> cacheMap) {

    FileReader reader = null;
    BufferedReader bufferedReader = null;

    try {
      reader = new FileReader(DOC_FILEPATH);
      bufferedReader = new BufferedReader(reader);

      String line = null;

      while ((line = bufferedReader.readLine()) != null) {

        // 进行数据转换操作
        DocIdBusi docIds = parseData(line);

        cacheMap.put(docIds.getDocId(), docIds.getHtmlUrl());
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("DocidReaderProcess loadDocMap FileNotFoundException", e);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("DocidReaderProcess loadDocMap IOException", e);
    } finally {
      CommonIOUtils.close(bufferedReader);
      CommonIOUtils.close(reader);
    }
  }

  /**
   * 进行数据转换操作
   *
   * @param line 行数据
   * @return 结果数据
   */
  private DocIdBusi parseData(String line) {
    String[] dataArrays = line.split(SymbolMsg.DATA_COLUMN);
    DocIdBusi result = new DocIdBusi();

    result.setDocId(Long.parseLong(dataArrays[0]));
    result.setHtmlUrl(dataArrays[1]);

    return result;
  }
}
