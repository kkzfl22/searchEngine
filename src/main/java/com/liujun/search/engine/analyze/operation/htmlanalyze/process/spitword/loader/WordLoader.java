package com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.loader;

import com.liujun.search.common.io.LocalIOUtils;
import com.liujun.search.engine.analyze.constant.SpitWordFileEnum;
import com.liujun.search.utilscode.io.constant.SymbolMsg;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 分词信息的加载
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/20
 */
public class WordLoader {

  /** 关键词的记录 */
  private static final Map<String, Integer> KEYWORD = new HashMap<>(27000);

  /** 基础路径 */
  private static final String BASE_PATH = "./participle/";

  /** 分词以及编号对应信息 */
  private static final String KEY_WORD_FILE = "key_word.txt";

  private static final Logger LOGGER = LoggerFactory.getLogger(WordLoader.class);

  public static final WordLoader INSTANCE = new WordLoader();

  static {
    // 进行数据检查与加载操作
    INSTANCE.checkAndLoader();
  }

  /** 检查文件并加载分词信息 */
  private void checkAndLoader() {
    String filePath = BASE_PATH;
    String readPath = WordLoader.class.getClassLoader().getResource(filePath).getPath();

    File currFile = new File(readPath, KEY_WORD_FILE);

    if (!currFile.exists()) {
      AtomicInteger intValue = new AtomicInteger(0);
      for (SpitWordFileEnum spitWord : SpitWordFileEnum.values()) {
        INSTANCE.loadFile(spitWord, intValue);
      }

      // 进行数据的输出操作
      INSTANCE.writeKeys(currFile);
      LOGGER.info("word loader finish , word numer : {} ", KEYWORD.size());
    } else {
      INSTANCE.loaderKeys(currFile);
    }
  }

  /**
   * 加载分词文件
   *
   * @param fileEnum
   */
  private void loadFile(SpitWordFileEnum fileEnum, AtomicInteger num) {

    FileReader reader = null;
    BufferedReader bufferReader = null;

    try {
      String filePath = BASE_PATH + fileEnum.getFile();
      String readPath = WordLoader.class.getClassLoader().getResource(filePath).getPath();

      reader = new FileReader(readPath);
      bufferReader = new BufferedReader(reader);

      String line = null;

      while ((line = bufferReader.readLine()) != null) {
        // 进行行数据处理
        this.lineProcess(line, num);
      }

    } catch (IOException e) {
      e.printStackTrace();
      LOGGER.error("Wordloader loader IOException", e);
      throw new RuntimeException(e);
    } finally {
      LocalIOUtils.close(bufferReader);
      LocalIOUtils.close(reader);
    }
  }

  /**
   * 进行词库的读取操作
   *
   * @param file
   */
  private void loaderKeys(File file) {
    FileReader reader = null;
    BufferedReader bufferReader = null;

    try {
      reader = new FileReader(file);
      bufferReader = new BufferedReader(reader);

      String dataLine = null;
      while ((dataLine = bufferReader.readLine()) != null) {
        // 进行数据读取操作
        this.lineKeys(dataLine);
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
      LOGGER.error("Wordloader loader loaderKeys FileNotFoundException", e);
    } catch (IOException e) {
      e.printStackTrace();
      LOGGER.error("Wordloader loader loaderKeys IOException", e);
    } finally {
      LocalIOUtils.close(bufferReader);
      LocalIOUtils.close(reader);
    }
  }

  /**
   * 进行行数据处理
   *
   * @param line 行信息
   */
  private void lineKeys(String line) {
    String[] data = line.split(SymbolMsg.DATA_COLUMN);

    KEYWORD.put(data[0], Integer.parseUnsignedInt(data[1]));
  }

  private Map<Integer, String> parseKeyMap(Map<String, Integer> map) {
    Map<Integer, String> result = new HashMap<>(map.size());

    for (Map.Entry<String, Integer> itemEntry : map.entrySet()) {
      result.put(itemEntry.getValue(), itemEntry.getKey());
    }

    return result;
  }

  /**
   * 进行将分词以及序列写入文件中
   *
   * @param outFile
   */
  private void writeKeys(File outFile) {
    FileWriter writer = null;
    BufferedWriter bufferWirte = null;

    try {
      writer = new FileWriter(outFile);
      bufferWirte = new BufferedWriter(writer);

      int size = KEYWORD.size();

      Map<Integer, String> parseMap = this.parseKeyMap(KEYWORD);

      String data = null;
      String line = null;

      for (int i = 0; i < size; i++) {
        data = parseMap.get(i);
        line = this.getOutData(data, i);
        bufferWirte.write(line);
      }

      parseMap.clear();
      parseMap = null;

    } catch (IOException e) {
      e.printStackTrace();
      LOGGER.error("wordloader IOException :", e);
    } finally {
      LocalIOUtils.close(bufferWirte);
      LocalIOUtils.close(writer);
    }
  }

  /**
   * 进行数据的输出格式排列
   *
   * @param key 单词信息
   * @param indexNum 索引编号
   * @return 数据信息
   */
  private String getOutData(String key, int indexNum) {
    StringBuilder outData = new StringBuilder();
    outData.append(key);
    outData.append(SymbolMsg.DATA_COLUMN);
    outData.append(indexNum);
    outData.append(SymbolMsg.LINE);
    return outData.toString();
  }

  /**
   * 进行行数据的处理操作
   *
   * @param line 行数据
   */
  private void lineProcess(String line, AtomicInteger index) {

    // 空直接跳过
    if (StringUtils.isEmpty(line)) {
      return;
    }

    String[] lineArray = line.split(SymbolMsg.DATA_COLUMN);
    String dataValue = lineArray[0].trim();

    if (!KEYWORD.containsKey(dataValue)) {
      // 添加词组中,并将编号进行添加
      KEYWORD.put(dataValue, index.getAndIncrement());
    }
  }

  public Map<String, Integer> getKeywordMap() {
    return KEYWORD;
  }
}
