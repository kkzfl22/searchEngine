package com.liujun.search.engine.analyze.operation.htmlanalyze.analyzeOneHtml;

import com.liujun.search.common.io.CommonIOUtils;
import com.liujun.search.engine.analyze.pojo.RawDataLine;
import com.liujun.search.utilscode.io.code.PathUtils;
import com.liujun.search.utilscode.io.constant.PathEnum;

import java.io.*;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/10
 */
public class AnalyzeHtmlFileReader {

  private static final String BASE_HTML_WORD =
      PathUtils.GetClassPath(PathEnum.FILE_ANALYZE_HTML_WORD);

  private static final char CHAR_SPECIAL = '\uFEFF';

  public static final AnalyzeHtmlFileReader INSTANCE = new AnalyzeHtmlFileReader();

  public RawDataLine readFile(String fileName) {

    File read = new File(BASE_HTML_WORD, fileName);

    if (read.exists()) {

      RawDataLine dataLine = new RawDataLine();

      StringBuilder outContext = new StringBuilder();

      FileReader readFile = null;
      BufferedReader bufferedReader = null;
      try {
        readFile = new FileReader(read);
        bufferedReader = new BufferedReader(readFile);

        String line = null;
        int index = 0;
        while ((line = bufferedReader.readLine()) != null) {

          if (index == 0) {
            dataLine.setId(Long.parseLong(deleteSpecialValue(line.trim())));
          } else if (index == 1) {
            dataLine.setLength(Long.parseLong(line.trim()));
          } else {
            outContext.append(line);
          }

          index++;
        }
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        CommonIOUtils.close(bufferedReader);
        CommonIOUtils.close(readFile);
      }

      dataLine.setHtmlContext(outContext.toString());

      return dataLine;
    }

    return null;
  }

  private String deleteSpecialValue(String value) {
    char[] valueDataArray = value.toCharArray();

    if (valueDataArray[0] == CHAR_SPECIAL) {
      return new String(valueDataArray, 1, valueDataArray.length - 1);
    }

    return value;
  }
}
