package com.liujun.search.engine.analyze.operation;

import com.liujun.search.common.constant.SysPropertyEnum;
import com.liujun.search.common.properties.SysPropertiesUtils;
import com.liujun.search.engine.analyze.operation.docraw.DocRawReaderProc;
import com.liujun.search.engine.analyze.operation.htmlanalyze.AnalyzeService;
import com.liujun.search.engine.analyze.pojo.RawDataLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 进行网页分词分析流程
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/11
 */
public class AnalyzeFlow {

  /** 缓存读取的条数 */
  private static final int READ_LIMIT =
      SysPropertiesUtils.getInstance()
          .getIntegerValueOrDef(SysPropertyEnum.ANZLYZE_CACHE_READ_NUM, 128);

  public static final AnalyzeFlow INSTANCE = new AnalyzeFlow();

  private Logger logger = LoggerFactory.getLogger(AnalyzeFlow.class);

  public void analyze() {
    logger.info("search engine analyze start");

    // 1,检查当前docraw文件是否已经读取完成
    while (!DocRawReaderProc.INSTANCE.checkFinish()) {
      // 2,进行docraw文件的读取操作
      List<RawDataLine> rawdataList = DocRawReaderProc.INSTANCE.reader(READ_LIMIT);

      logger.info("search engine analyze reader filesize {}", rawdataList.size());
      // 2,进行分析处理流程
      AnalyzeService.INSTANCE.analyzeFlow(rawdataList);
    }

    logger.info("search engine analyze finish");
  }
}
