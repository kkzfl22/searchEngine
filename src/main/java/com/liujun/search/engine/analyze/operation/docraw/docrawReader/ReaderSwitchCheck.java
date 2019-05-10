package com.liujun.search.engine.analyze.operation.docraw.docrawReader;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.common.io.LocalIOUtils;
import com.liujun.search.engine.analyze.constant.DocrawReaderEnum;
import com.liujun.search.utilscode.io.constant.SymbolMsg;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 进行文件切换读取的检查
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/08
 */
public class ReaderSwitchCheck implements FlowServiceInf {

  public static final ReaderSwitchCheck INSTANCE = new ReaderSwitchCheck();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    FileChannel streamChannel =
        context.getObject(DocrawReaderEnum.DOCRAW_PROC_INPUT_CHANNEL.getKey());
    ByteBuffer buffer = context.getObject(DocrawReaderEnum.DOCRAW_INPUT_READER_BUFFER.getKey());

    // 文件位置
    long position = context.getObject(DocrawReaderEnum.DOCRAW_INPUT_FILE_POSITION.getKey());

    int readIndex = -1;

    if (streamChannel.isOpen()) {
      // 进行数据读取操作
      readIndex = streamChannel.read(buffer, position);
    }

    // 如果当前未读取到数据说明已经读取完成，切换到下一个文件读取
    if (readIndex <= 0) {
      FileInputStream inputStream =
          context.getObject(DocrawReaderEnum.DOCRAW_PROC_INPUT_STREAM.getKey());

      LocalIOUtils.close(streamChannel);
      LocalIOUtils.close(inputStream);

      boolean switchFlag = openNext(context);

      // 如果切换失败，则返回失败
      if (!switchFlag) {
        context.put(DocrawReaderEnum.DOCRAW_OUTPUT_FINISH_FLAG.getKey(), true);
        // 当读取完成后，则索引位置不加
        readIndex = 0;

        //所有完成后，需要进行关闭操作


      } else {
        position = 0;
        streamChannel = context.getObject(DocrawReaderEnum.DOCRAW_PROC_INPUT_CHANNEL.getKey());
        // 切换成功，进行首个buffer的读取操作
        readIndex = streamChannel.read(buffer, position);
      }
    }

    position = position + readIndex;

    // 更新位置
    context.put(DocrawReaderEnum.DOCRAW_INPUT_FILE_POSITION.getKey(), position);

    buffer.flip();
    // 记录下当前读取的buffer的大小
    context.put(DocrawReaderEnum.DOCRAW_PROC_READ_BUFFERSIZE.getKey(), readIndex);
    return true;
  }

  /**
   * 打开下一个文件
   *
   * @param context 处理的上下文对象信息
   * @return true 切换成功 false 结束切换失败
   * @throws IOException
   */
  private boolean openNext(FlowServiceContext context) throws IOException {
    // 1,进行指定文件的读取操作
    String[] fileList = context.getObject(DocrawReaderEnum.DOCRAW_INPUT_FILE_LIST.getKey());
    // 获取文件索引
    int index = context.getObject(DocrawReaderEnum.DOCRAW_INPUT_FILE_INDEX.getKey());

    // 当索引超过文件数时，则不再继续
    if (index + 1 >= fileList.length) {
      return false;
    }

    // 获取文件路径
    String basePath = context.getObject(DocrawReaderEnum.DOCRAW_INPUT_BASE_PATH.getKey());

    index = index + 1;
    // 重新将索引位置写入至流程中
    context.put(DocrawReaderEnum.DOCRAW_INPUT_FILE_INDEX.getKey(), index);

    String filePath = basePath + SymbolMsg.PATH + fileList[index];
    File checkFile = new File(filePath);
    FileInputStream input = new FileInputStream(checkFile);

    FileChannel channel = input.getChannel();

    context.remove(DocrawReaderEnum.DOCRAW_PROC_INPUT_STREAM.getKey());
    context.remove(DocrawReaderEnum.DOCRAW_PROC_INPUT_CHANNEL.getKey());

    context.put(DocrawReaderEnum.DOCRAW_PROC_INPUT_STREAM.getKey(), input);
    context.put(DocrawReaderEnum.DOCRAW_PROC_INPUT_CHANNEL.getKey(), channel);

    return true;
  }
}
