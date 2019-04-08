package com.liujun.search.engine.collect.operation.docraw;

import com.liujun.search.utilscode.io.constant.PathCfg;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 进行网页存储的文件管理
 *
 * <p>目前的管理方式为，限制文件大小为1GB,超过之后则切换到下一个文件
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/25
 */
public class DocRawFileManager {

  /** 网页输出的html页面的对象信息 */
  private static final String DOC_RAW_DIR = "docraw";

  /** 网页存储的文件名的前缀 */
  private static final String DOC_ID_FILE_SUFFIX = "doc_raw_";

  /** 网页后缀名 */
  private static final String DOC_ID_SUFFIX_NAME = ".bin";

  /** url与id对应的关系的文件存储路径 */
  protected static final String DOC_FILEPATH = PathCfg.BASEPATH + PathCfg.COLLEC_PATH;

  /** 当前文件的索引号 */
  private AtomicInteger currFileIndex = new AtomicInteger(0);

  /** 当前文件大小 */
  private AtomicLong currFileSize = new AtomicLong(0);

  /** 初始化读取数据信息 */
  public void initReadIndex() {
    String basePath = this.getPath();

    // 检查并创建目录录
    File fileCheckPath = this.dirCheckAndCreate(basePath);

    int lastFile = this.lastFileIndex(fileCheckPath);

    // 进行文件的创建操作
    this.createFile();

    if (-1 != lastFile) {
      // 设置文件索引号
      this.currFileIndex.set(lastFile);

      // 设置文件大小
      long currSize = this.getDiskFileSize();
      this.currFileSize.set(currSize);
    } else {
      this.currFileIndex.set(0);
      this.currFileSize.set(0);
    }
  }

  public long getCurrFileSize() {
    return currFileSize.get();
  }

  /**
   * 进行原子级别的更新操作
   *
   * @param adddSize 网页增加的大小
   */
  public void fileSizeAdd(long adddSize) {
    currFileSize.addAndGet(adddSize);
  }

  /**
   * 获取基础路径信息
   *
   * @return 路径信息
   */
  protected String getPath() {
    StringBuilder outputPath = new StringBuilder();

    outputPath.append(DOC_FILEPATH);
    outputPath.append(DOC_RAW_DIR).append(File.separator);

    return outputPath.toString();
  }

  /**
   * 输出文件的路径信息
   *
   * @return 文件路径信息
   */
  protected String getPathFile() {

    StringBuilder outputPath = new StringBuilder();
    outputPath.append(this.getPath());
    outputPath.append(this.getFileName());

    return outputPath.toString();
  }

  /**
   * 切换文件索引
   *
   * @return
   */
  protected void switchNextFileIndex() {
    currFileIndex.incrementAndGet();
  }

  /**
   * 获取文件操作的索引号
   *
   * @return 文件索引号
   */
  protected int getFileIndex() {
    return currFileIndex.get();
  }

  /** 设置一个新文件大小为0 */
  protected void setNewFileSizeZero() {
    currFileSize.set(0);
  }

  /**
   * 获取最后的文件，如果存在返回最后文件，不存在返回为-1
   *
   * @param path
   * @return
   */
  private int lastFileIndex(File path) {
    // 读取文件下的文件信息
    String[] dirList = path.list();

    // 如果存在，则找到当前最大的文件获取文件大小
    if (dirList != null && dirList.length > 0) {
      // 进行文件名的排序操作
      Arrays.sort(dirList);

      // 最大文件
      return this.getFileIndex(dirList[dirList.length - 1]);
    }

    return -1;
  }

  /**
   * 文件路径检查并创建操作
   *
   * @param basePath
   * @return 文件对象
   */
  private File dirCheckAndCreate(String basePath) {
    File pathBaseTmp = new File(basePath);
    if (!pathBaseTmp.exists()) {
      pathBaseTmp.mkdirs();
    }

    return pathBaseTmp;
  }

  /** 进行文件的创建操作 */
  private void createFile() {
    String pathFile = this.getPathFile();
    File outFile = new File(pathFile);
    if (!outFile.exists()) {
      try {
        outFile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 获取文件大小
   *
   * @return 当前文件的大小
   */
  private long getDiskFileSize() {
    String pathName = this.getPathFile();
    File curFile = new File(pathName);
    return curFile.length();
  }

  /**
   * 从文件名中获取文件索引号
   *
   * @param pathName
   * @return
   */
  private int getFileIndex(String pathName) {
    String indexStr =
        pathName.substring(DOC_ID_FILE_SUFFIX.length(), pathName.lastIndexOf(DOC_ID_SUFFIX_NAME));

    return Integer.parseInt(indexStr);
  }

  /**
   * 获取文件名称
   *
   * @return
   */
  private String getFileName() {
    StringBuilder outputPath = new StringBuilder();
    outputPath.append(DOC_ID_FILE_SUFFIX).append(currFileIndex.get());
    outputPath.append(DOC_ID_SUFFIX_NAME);

    return outputPath.toString();
  }

  /**
   * 获取文件列表，并且需要按名称默认排序
   *
   * @return 文件信息
   */
  protected String[] fileList() {
    File dataFile = new File(this.getPath());

    String[] list = dataFile.list();

    if (list.length > 1) {
      Arrays.sort(list);
    }

    return list;
  }
}
