package com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.loader;

import com.liujun.search.common.io.CommonIOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.HashSet;

/**
 * 加载过滤的字
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/22
 */
public class FilterWordLoader {

  /** 关键词的记录 */
  private static final HashSet<String> FILTER_WORD = new HashSet<>(1900);

  /** 过滤的分词 */
  private static final String STOP_WORDS_FILE = "/participle/stopwords.txt";

  private static final Logger LOGGER = LoggerFactory.getLogger(WordLoader.class);

  static {
    // 进行过滤的文件的加载操作
    LoadFile();
  }

  public static final FilterWordLoader INSTANCE = new FilterWordLoader();

  /**
   * 加载分词文件
   *
   * @param
   */
  private static void LoadFile() {

    FileReader reader = null;
    BufferedReader bufferReader = null;
    InputStream input = null;
    InputStreamReader inpuReader = null;
    try {

      URL url = WordLoader.class.getClassLoader().getResource(STOP_WORDS_FILE);

      // 普通运行时的加载
      if (null != url) {
        String readPath = url.getPath();
        reader = new FileReader(readPath);
        bufferReader = new BufferedReader(reader);
      } else {
        // 在jar运行时，将使用此方式进行加载
        input = GetResource(STOP_WORDS_FILE);
        if (null == input) {
          throw new RuntimeException("file not exists,path:" + STOP_WORDS_FILE);
        }
        inpuReader = new InputStreamReader(input);
        bufferReader = new BufferedReader(inpuReader);
      }

      String line = null;

      while ((line = bufferReader.readLine()) != null) {
        // 进行行数据处理
        lineProcess(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
      LOGGER.error("Wordloader loader IOException", e);
      throw new RuntimeException(e);
    } finally {
      CommonIOUtils.close(inpuReader);
      CommonIOUtils.close(input);
      CommonIOUtils.close(bufferReader);
      CommonIOUtils.close(reader);
    }
  }

  /**
   * 获取系统资源
   *
   * @param path 路径信息
   * @return 文件流
   */
  public static InputStream GetResource(String path) {
    return FilterWordLoader.class.getResourceAsStream(path);
  }

  /**
   * 进行行数据的处理
   *
   * @param line
   */
  private static void lineProcess(String line) {

    if (StringUtils.isNotEmpty(line)) {
      String dataline = line.trim();
      // 将数据加到在过滤分词中
      FILTER_WORD.add(dataline);
    }
  }

  public HashSet<String> getFilterSet() {
    return FILTER_WORD;
  }
}
