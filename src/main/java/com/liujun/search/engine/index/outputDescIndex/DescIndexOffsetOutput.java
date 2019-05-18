package com.liujun.search.engine.index.outputDescIndex;

import com.liujun.search.common.io.CommonIOUtils;
import com.liujun.search.common.constant.SymbolMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 倒排索引文件偏移量的输出
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/11
 */
public class DescIndexOffsetOutput {

  public static final DescIndexOffsetOutput INSTANCE = new DescIndexOffsetOutput();

  /** 文件输出 */
  private FileOutputStream outputStream;

  /** 缓冲区输出 */
  private BufferedOutputStream bufferedOutputStream;

  private Logger logger = LoggerFactory.getLogger(DescIndexOffsetOutput.class);

  public void open() {
    String offsetname = DescIndexFileName.INSTANCE.getDescIndexOffsetFileName();

    try {
      outputStream = new FileOutputStream(offsetname);
      bufferedOutputStream = new BufferedOutputStream(outputStream);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("DescIndexOffsetOutput open FileNotFoundException", e);
    }
  }

  public void write(int wordid, long offset, int datalength) throws IOException {
    byte[] outDataBytes = this.getOutData(wordid, offset, datalength);

    // 进行文件的输出操作
    this.bufferedOutputStream.write(outDataBytes);
  }

  public void close() {
    CommonIOUtils.close(bufferedOutputStream);
    CommonIOUtils.close(outputStream);
  }

  private byte[] getOutData(int wordid, long offsetSet, int dataLength) {
    StringBuilder outData = new StringBuilder();

    outData.append(wordid);
    outData.append(SymbolMsg.DATA_COLUMN);
    outData.append(offsetSet);
    outData.append(SymbolMsg.DATA_COLUMN);
    outData.append(dataLength);
    outData.append(SymbolMsg.LINE);

    String outDataValue = outData.toString();

    return outDataValue.getBytes(StandardCharsets.UTF_8);
  }
}
