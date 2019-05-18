package com.liujun.search.engine.index.filesort;

import com.liujun.search.common.io.CommonIOUtils;
import com.liujun.search.engine.index.pojo.TempIndexData;
import com.liujun.search.common.constant.PathCfg;
import com.liujun.search.common.constant.SymbolMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * 临时索引文件读取操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/09
 */
public class FileIndexReader {

  /** 临时索引文件目录 */
  private static final String TMP_INDEX_PATH =
      PathCfg.BASEPATH + PathCfg.ANALYZE_PATH + PathCfg.ANALYZE_PATH_TEMP_INDEX_PATH;

  private static final TreeMap<Long, Integer> BUFFERSIZE = new TreeMap<>();

  static {
    BUFFERSIZE.put(1L * 1024 * 512, 40000);
    BUFFERSIZE.put(1L * 1024 * 128, 10000);
    BUFFERSIZE.put(1L * 1024 * 256, 20000);

    // 经过测试算，每1.1M的文件大约是8万行，预约空间，以防止数组扩容带来的开销
    // 一共预约128个档，进行文件操作
    for (int i = 0; i < 128; i++) {
      BUFFERSIZE.put(1L * 1024 * 1024 * i, 80000 * i);
    }
  }

  public static final FileIndexReader INSTANCE = new FileIndexReader();

  private Logger logger = LoggerFactory.getLogger(FileIndexReader.class);

  /**
   * 获取临时索引文件信息
   *
   * @return
   */
  public File[] getIndexFiles() {

    File baseTmpIndex = new File(TMP_INDEX_PATH);

    if (baseTmpIndex.exists()) {
      return baseTmpIndex.listFiles();
    }

    return new File[0];
  }

  /**
   * 获取临时索引的
   *
   * @param filepath
   * @return
   */
  public List<TempIndexData> getFileIndexList(File filepath) {

    FileReader read = null;
    BufferedReader bufferReader = null;

    List<TempIndexData> list = null;

    try {
      // 通过计算得到初始值，以减少扩容带来的性能损失
      Map.Entry<Long, Integer> entry = BUFFERSIZE.ceilingEntry(filepath.length());

      if (entry == null) {
        list = new ArrayList<>(5000);
      } else {
        list = new ArrayList<>(entry.getValue());
      }

      read = new FileReader(filepath);
      bufferReader = new BufferedReader(read);

      String line = null;

      while ((line = bufferReader.readLine()) != null) {
        list.add(parseIndexData(line));
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("FileIndexReader getFileIndexList FileNotFoundException ",e);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("FileIndexReader IOException FileNotFoundException ",e);
    } finally {
      CommonIOUtils.close(bufferReader);
      CommonIOUtils.close(read);
    }

    return list;
  }

  /**
   * 将数据转换为java对象
   *
   * @param data
   * @return
   */
  private TempIndexData parseIndexData(String data) {

    TempIndexData temp = new TempIndexData();

    String[] dataValue = data.split(SymbolMsg.DATA_COLUMN);

    temp.setTempId(Integer.parseInt(dataValue[0]));
    temp.setDocSeqId(Long.parseLong(dataValue[1]));

    return temp;
  }
}
