package com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.loader;

import com.liujun.search.common.io.LocalIOUtils;
import com.liujun.search.engine.analyze.constant.SpitWordFileEnum;
import com.liujun.search.utilscode.io.constant.SymbolMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 分词信息的加载
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/20
 */
public class WordLoader {

  /** 关键字出现频度计数 */
  private static final Map<Character, Integer> CHARNUM = new HashMap<>(6500);

  /** 关键词的记录 */
  private static final HashSet<String> KEYWORD = new HashSet<>(27000);

  /** 基础路径 */
  private static final String BASE_PATH = "./participle/";

  private static final Logger LOGGER = LoggerFactory.getLogger(WordLoader.class);

  static {
    for (SpitWordFileEnum spitWord : SpitWordFileEnum.values()) {
      LoadFile(spitWord);
    }

    LOGGER.info("word loader finish , word numer : {} ", KEYWORD.size());
    LOGGER.info("word loader finish , charset num : {} ", CHARNUM.size());
  }

  public static final WordLoader INSTANCE = new WordLoader();

  /**
   * 加载分词文件
   *
   * @param fileEnum
   */
  public static void LoadFile(SpitWordFileEnum fileEnum) {

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
        lineProcess(line);
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

  private static void lineProcess(String line) {

    String[] lineArray = line.split(SymbolMsg.DATA_COLUMN);

    String dataValue = lineArray[0].trim();

    if (!KEYWORD.contains(dataValue)) {

      // 添加词组中
      KEYWORD.add(dataValue);
    }

    // 添加到单个字符中
    char[] keys = dataValue.toCharArray();

    Integer nums;
    for (char keyItem : keys) {
      nums = CHARNUM.get(keyItem);

      if (nums == null) {
        nums = 1;
      } else {
        nums = nums + 1;
      }
      CHARNUM.put(keyItem, nums);
    }
  }

  public HashSet<String> getKeyword() {
    return KEYWORD;
  }
}
