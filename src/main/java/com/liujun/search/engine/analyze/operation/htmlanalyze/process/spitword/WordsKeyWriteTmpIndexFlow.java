package com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.WordFLowEnum;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.loader.KeyWordMap;
import com.liujun.search.engine.analyze.operation.htmlanalyze.process.spitword.tempIndexFile.TempIndexFile;

/**
 * 将分词写入临时索引文件
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/24
 */
public class WordsKeyWriteTmpIndexFlow implements FlowServiceInf {

  public static final WordsKeyWriteTmpIndexFlow INSTANCE = new WordsKeyWriteTmpIndexFlow();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    // 单词编号
    int keyIndex = context.getObject(WordFLowEnum.WORD_PROC_INDEX.getKey());

    // 网页编号
    int docId = context.getObject(WordFLowEnum.WORKD_INPUT_DOCID.getKey());

    // 将分词写入临时索引文件中
    TempIndexFile.INSTANCE.writeData(keyIndex, docId);

    return true;
  }
}
