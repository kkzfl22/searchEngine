package com.liujun.search.engine.analyze.operation.htmlanalyze.analyzeOneHtml;

import com.liujun.search.engine.analyze.operation.htmlanalyze.AnalyzeService;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.WordsKeyWriteTmpIndexFlow;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.tempIndexFile.TempIndexFile;
import com.liujun.search.engine.analyze.pojo.RawDataLine;
import org.junit.Test;

/**
 * 进行网页的单页面分词流程
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/10
 */
public class AnalyzeOneHtmlFlow {

  @Test
  public void testAnalyzeOneHtml() {
    RawDataLine rawData = AnalyzeHtmlFileReader.INSTANCE.readFile("analyzeseq3.html");

    if (null != rawData) {
      AnalyzeService.INSTANCE.analyzeHtml(rawData);

      TempIndexFile.INSTANCE.flush();
    }
  }
}
