package com.liujun.search.engine.query.process;

import com.liujun.search.common.io.CommonIOUtils;
import com.liujun.search.engine.index.outputDescIndex.DescIndexFileName;
import com.liujun.search.engine.query.pojo.WordOffsetBusi;
import com.liujun.search.common.constant.SymbolMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

/**
 * 进行索引后的偏移文件读取操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/13
 */
public class WordOffsetReaderProcess {

  public static final WordOffsetReaderProcess INSTANCE = new WordOffsetReaderProcess();

  private Logger logger = LoggerFactory.getLogger(WordOffsetReaderProcess.class);

  public void loadWordOffsetMap(Map<Integer, WordOffsetBusi> cacheMap) {

    File[] files = this.getFiles();

    if (files.length > 0) {

      for (File item : files) {
        // 读取文件
        this.readWordOffset(item, cacheMap);
      }
    }
  }

  /**
   * 读取偏移文件
   *
   * @param fileInfo
   * @param wordMap
   */
  private void readWordOffset(File fileInfo, Map<Integer, WordOffsetBusi> wordMap) {
    FileReader reader = null;
    BufferedReader bufferedReader = null;

    try {
      // 获取文件索引号
      int index = DescIndexFileName.INSTANCE.getNameIndex(fileInfo.getName());

      reader = new FileReader(fileInfo);
      bufferedReader = new BufferedReader(reader);

      String line = null;
      WordOffsetBusi offsetBusi = null;

      while ((line = bufferedReader.readLine()) != null) {

        offsetBusi = readerLineOffset(line);
        offsetBusi.setFileIndex(index);

        wordMap.put(offsetBusi.getWordId(), offsetBusi);
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("WordOffsetReaderProcess readWordOffset IOException", e);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("WordOffsetReaderProcess readWordOffset IOException", e);
    } finally {
      CommonIOUtils.close(bufferedReader);
      CommonIOUtils.close(reader);
    }
  }

  /**
   * 转换索引的偏移的数据信息
   *
   * @param line 行数据
   * @return 单行偏移数据
   */
  private WordOffsetBusi readerLineOffset(String line) {
    String[] data = line.split(SymbolMsg.DATA_COLUMN);

    WordOffsetBusi offsetBusi = new WordOffsetBusi();

    offsetBusi.setWordId(Integer.parseInt(data[0]));
    offsetBusi.setOffset(Long.parseLong(data[1]));
    offsetBusi.setLength(Integer.parseInt(data[2]));

    return offsetBusi;
  }

  /**
   * 获取文件集合
   *
   * @return 文件列表
   */
  private File[] getFiles() {

    File descIndex = new File(DescIndexFileName.DESC_INDEX_PATH);

    if (descIndex.exists() && null != descIndex.listFiles()) {

      File[] files =
          descIndex.listFiles(
              (File dir, String name) -> {
                if (name.endsWith(DescIndexFileName.DESC_INDEX_FILENAME_OFFSET_SUFFIX)) {
                  return true;
                }
                return false;
              });

      return files;
    }

    return new File[0];
  }
}
