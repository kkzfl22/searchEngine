package com.liujun.search.engine.analyze.operation.htmlanalyze.process.htmlanalyze;

import com.liujun.search.engine.analyze.operation.htmlanalyze.AnalyzeService;
import com.liujun.search.engine.analyze.pojo.RawDataLine;
import com.liujun.search.utilscode.element.constant.HtmlHrefFileEnum;
import com.liujun.search.utilscode.element.html.HtmlReaderUtils;
import org.junit.Test;

import java.io.File;

/**
 * 进行网页分词的测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/30
 */
public class TestAnalyzeService {

  @Test
  public void testanalyzeHtml() {

    AnalyzeService INSTANCE = new AnalyzeService();

    RawDataLine rawDataLine = new RawDataLine();

    File files = HtmlReaderUtils.GetErrorFile("html_error_22.html");

    int index = 0;

    String value = HtmlReaderUtils.GetContext(files);

    if (value.length() > 0) {
      rawDataLine.setId(index);
      rawDataLine.setLength(value.length());
      rawDataLine.setHtmlContext(value);

      // 进行网页的分析操作
      INSTANCE.analyzeHtml(rawDataLine);
    }
    index++;
  }
}
