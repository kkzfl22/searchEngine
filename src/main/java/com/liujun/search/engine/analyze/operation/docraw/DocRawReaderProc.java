package com.liujun.search.engine.analyze.operation.docraw;

import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.DocRawFindEnum;
import com.liujun.search.engine.collect.operation.docraw.DocRawFileStreamManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * docraw用来进行原始网页的读取操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/04
 */
public class DocRawReaderProc extends DocRawFileStreamManager {

  /** 使用32K的缓存来进行临时存储 */
  private static final int FIND_BUFFER_SIZE = 32 * 1024;

  public static final DocRawReaderProc INSTANCE = new DocRawReaderProc();

  /** 日志信息 */
  private Logger logger = LoggerFactory.getLogger(DocRawReaderProc.class);

  /** 流程信息 */
  private static final List<FlowServiceInf> FLOW = new ArrayList<>();

  static {
    // 初始化读取资源,读取文件列表排序
    // 检查是否有文件已经打开，未打打，则打开首个文件
    // 从指定的位置开始读取，没有指定位置为0
    // 循环开始读取当前文件

  }

  /**
   * 获取html的内容信息,根据网页的id
   *
   * @param pageSize 每次读取的条数
   * @return 获取的网页内容信息
   */
  public List<DocRawReaderProc> reader(int pageSize) {

    ByteBuffer readBuffer = ByteBuffer.allocate(FIND_BUFFER_SIZE);
    FlowServiceContext context = new FlowServiceContext();

    // 扫描文件夹，得到文件列表
    String[] dirList = this.fileList();

    String dataContext = null;
    for (String dataFile : dirList) {
      // 进行文件的查找遍历操作

      // 当查找到文件后，则退出文件遍历

    }

    return null;
  }
}
