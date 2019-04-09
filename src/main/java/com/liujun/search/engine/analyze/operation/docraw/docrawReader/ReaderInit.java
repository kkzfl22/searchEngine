package com.liujun.search.engine.analyze.operation.docraw.docrawReader;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.analyze.constant.DocrawReaderEnum;
import com.liujun.search.utilscode.io.constant.SymbolMsg;

import java.io.FileInputStream;

/**
 * 进行文件的首次读取
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/08
 */
public class ReaderInit implements FlowServiceInf {

  public static final ReaderInit INSTANCE = new ReaderInit();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    FileInputStream input = context.getObject(DocrawReaderEnum.DOCRAW_PROC_INPUT_STREAM.getKey());

    if (null == input) {
      // 1,进行指定文件的读取操作
      String[] fileList = context.getObject(DocrawReaderEnum.DOCRAW_INPUT_FILE_LIST.getKey());
      // 获取文件索引
      int index = context.getObject(DocrawReaderEnum.DOCRAW_INPUT_FILE_INDEX.getKey());
      // 获取文件路径
      String basePath = context.getObject(DocrawReaderEnum.DOCRAW_INPUT_BASE_PATH.getKey());

      String filePath = basePath + SymbolMsg.PATH + fileList[index];
      // 文件位置
      long position = context.getObject(DocrawReaderEnum.DOCRAW_INPUT_FILE_POSITION.getKey());

      input = new FileInputStream(filePath);

      // 设置文件开始的位置
      input.skip(position);

      context.put(DocrawReaderEnum.DOCRAW_PROC_INPUT_STREAM.getKey(), input);
      context.put(DocrawReaderEnum.DOCRAW_PROC_INPUT_CHANNEL.getKey(), input.getChannel());
    }

    return true;
  }
}
