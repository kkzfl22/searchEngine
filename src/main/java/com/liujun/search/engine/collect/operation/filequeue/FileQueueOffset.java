package com.liujun.search.engine.collect.operation.filequeue;

import com.liujun.search.common.io.CommonIOUtils;
import com.liujun.search.common.constant.SymbolMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 偏移文件操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/21
 */
public class FileQueueOffset {

  /** 文件队列的偏移前缀信息 */
  private static final String LINKS_OFFSET_PREFIX_NAME = "links_";

  /** 文件标识 */
  private static final String LINKS_OFFSET_FLAG = "_offset";

  /** 默认数据标识 */
  private static final String DATA_DEF = "0,0";

  /** 日志 */
  private Logger log = LoggerFactory.getLogger(FileQueueOffset.class);

  /** 网站链接文件偏移信息 */
  private final String linkedOffsetFile;

  /** 当前读取的偏移量 */
  private long readOffset = 0;

  /** 当前写入的偏移量 */
  private long writeOffset = 0;

  public FileQueueOffset(String linksFile) {
    this.linkedOffsetFile =
        LINKS_OFFSET_PREFIX_NAME + linksFile + LINKS_OFFSET_FLAG + FileQueue.SUFFIX_NAME;

    // 文件夹的创建操作
    String mkPath = getLinkedOffsetPath();
    this.dirCheckAndMake(mkPath);

    // 文件创建操作，偏移量文件在操作前须确保文件已经存在
    String linkOffset = getLinksOffsetFile();
    this.fileCheckCreate(linkOffset);
  }

  public long getReadOffset() {
    return readOffset;
  }

  public void setReadOffset(long readOffset) {
    this.readOffset = readOffset;
  }

  public long getWriteOffset() {
    return writeOffset;
  }

  public void setWriteOffset(long writeOffset) {
    this.writeOffset = writeOffset;
  }

  /** 从本地文件中读取偏移量信息 */
  public void readOffset() {
    String linkOffset = this.getLinksOffsetFile();

    // 1,check file exists
    File offsetFile = new File(linkOffset);

    if (!offsetFile.exists()) {
      this.readOffset = 0;
      this.writeOffset = 0;
    } else {
      // 读取偏移量文件信息
      String value = this.readOffset(linkOffset);
      int spitIndex = value.indexOf(SymbolMsg.COMMA);

      // 重新设置当前的集合信息
      this.readOffset = Long.parseLong(value.substring(0, spitIndex));
      this.writeOffset = Long.parseLong(value.substring(spitIndex + 1));
    }
  }

  /**
   * 读取偏移量信息
   *
   * @param linkOffset 偏移量文件
   * @return 偏移量信息
   */
  private String readOffset(String linkOffset) {
    InputStream input = null;
    byte[] buffer = new byte[32];

    try {
      input = new FileInputStream(linkOffset);
      int size = input.read(buffer);

      String value = new String(buffer, 0, size);
      return value;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      log.error("FileQueue readAndSetOffset FileNotFoundException", e);
    } catch (IOException e) {
      e.printStackTrace();
      log.error("FileQueue readAndSetOffset IOException", e);
    } finally {
      CommonIOUtils.close(input);
    }

    return DATA_DEF;
  }

  /**
   * 读取文件偏移信息
   *
   * @return 结果信息
   */
  private byte[] getOffsetBytes() {
    StringBuilder outOffset = new StringBuilder();
    outOffset.append(this.readOffset);
    outOffset.append(SymbolMsg.COMMA);
    outOffset.append(this.writeOffset);

    return outOffset.toString().getBytes();
  }

  /** 将当前的写入与读取的偏移量写入至文件中 */
  public void writeOffset() {
    FileOutputStream outputStream = null;

    try {
      String linkOffset = this.getLinksOffsetFile();

      // 读取偏移信息
      byte[] outbyte = this.getOffsetBytes();

      // 每次执行数据替换操作
      outputStream = new FileOutputStream(linkOffset, false);
      outputStream.write(outbyte);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      log.error("FileQueue writeOffset FileNotFoundException", e);
    } catch (IOException e) {
      e.printStackTrace();
      log.error("FileQueue writeOffset IOException", e);
    } finally {
      CommonIOUtils.close(outputStream);
    }
  }

  /**
   * 文件检查创建
   *
   * @param linkOffset 文件偏移
   * @throws IOException
   */
  private void fileCheckCreate(String linkOffset) {
    File outFile = new File(linkOffset);
    if (!outFile.exists()) {
      try {
        outFile.createNewFile();

        // 进行首次文件的写入
        this.writeOffset();

      } catch (IOException e) {
        e.printStackTrace();
        log.error("FileQueueOffset fileCheckCreate IOException", e);
      }
    }
  }

  /**
   * 检查文件夹是否存在，不存在则创建操作
   *
   * @param path
   */
  private void dirCheckAndMake(String path) {
    File outFile = new File(path);
    if (!outFile.exists()) {
      outFile.mkdirs();
    }
  }

  /**
   * 获取偏移文件的路径信息
   *
   * @return
   */
  public String getLinkedOffsetPath() {
    return FileQueue.PROCESS_LINK_BASE_PATH
        + File.separator
        + FileQueue.LINKED_PATH
        + File.separator;
  }

  /**
   * 获取存储链接的偏移量文件路径信息
   *
   * @return
   */
  private String getLinksOffsetFile() {
    return FileQueue.PROCESS_LINK_BASE_PATH
        + File.separator
        + FileQueue.LINKED_PATH
        + File.separator
        + this.linkedOffsetFile;
  }

  /** 进行文件队列的清理操作,即是将文件中的偏移位置置为0 */
  public void clean() {
    this.readOffset = 0;
    this.writeOffset = 0;
    // 进行偏移量的保存操作
    this.writeOffset();
  }
}
