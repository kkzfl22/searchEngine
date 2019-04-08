package com.liujun.search.engine.collect.operation.docraw;

import com.liujun.search.common.io.LocalIOUtils;
import com.liujun.search.common.properties.SysPropertiesUtils;
import com.liujun.search.utilscode.io.constant.SysPropertyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 进行网页存储的文件管理
 *
 * <p>目前的管理方式为，限制文件大小为1GB,超过之后则切换到下一个文件
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/25
 */
public class DocRawFileStreamManager extends DocRawFileManager {

  /** 日志信息 */
  private Logger logger = LoggerFactory.getLogger(DocRawFileStreamManager.class);

  /** 默认限制最大文件大小 */
  private static final int DEF_FILE_MAX_SIZE = 1 * 1024 * 1024 * 1024;

  /** 输入流对象 */
  private FileOutputStream outputStream = null;

  /** 文件通道对象 */
  private FileChannel channel;

  /** 独占锁 */
  private Lock lock = new ReentrantLock();

  /** 文件大小限制的临时变量 */
  private int maxFileSize;

  /** 进行文件的打开操作 */
  public void openFile() {
    String fileChar = super.getPathFile();

    try {
      outputStream = new FileOutputStream(fileChar, true);
      channel = outputStream.getChannel();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("DocRawFileStreamManager open FileNotFoundException ", e);
    }
  }

  /** 检查文件大小并切换文件 */
  public void fileSizeCheanAndSwitch() {
    // 检查文件是否超过了文件大小限制
    if (this.getCurrFileSize() > getMaxFileSize()) {
      int fileIndex = super.getFileIndex();
      boolean lockRsp = false;
      try {
        lock.lock();

        lockRsp = true;

        int fileIndex2 = super.getFileIndex();

        // 执行二次加锁的确认操作,有可能被其他线程已经更新掉了文件索引号
        if (fileIndex == fileIndex2) {
          // 切换文件
          super.switchNextFileIndex();
          // 1,先次原来的文件通道 关闭
          this.close();
          // 然后打开新的文件
          this.openFile();

          // 设置新文件大小为0
          super.setNewFileSizeZero();
        }
      } catch (Exception e) {
        e.printStackTrace();
        logger.error("DocRawFileStreamManager switchOpen Exception", e);
      } finally {
        if (lockRsp) {
          // 加锁成功后，必须释放锁
          lock.unlock();
        }
      }
    }
  }

  /**
   * 获取单文件大小的限制
   *
   * @return 大小的值，使用int类型表示
   */
  public int getMaxFileSize() {

    if (maxFileSize == 0) {

      int maxFileSizeDef = DEF_FILE_MAX_SIZE;
      Integer dataMaxSize =
          SysPropertiesUtils.getInstance().getIntValue(SysPropertyEnum.FILE_MAX_SIZE);

      if (null != dataMaxSize) {
        maxFileSizeDef = dataMaxSize;
      }

      maxFileSize = maxFileSizeDef;
    }
    return maxFileSize;
  }

  public FileChannel getChannel() {
    return channel;
  }

  /** 数据关闭对象 */
  public void close() {
    LocalIOUtils.close(channel);
    LocalIOUtils.close(outputStream);
  }


}
