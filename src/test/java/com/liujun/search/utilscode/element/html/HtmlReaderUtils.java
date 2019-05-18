package com.liujun.search.utilscode.element.html;

import com.liujun.search.utilscode.element.constant.HtmlHrefFileEnum;
import com.liujun.search.utilscode.io.code.PathUtils;
import com.liujun.search.utils.PathEnum;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 网页读取的公共类
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/27
 */
public class HtmlReaderUtils {

  private static final String BASE_ERROR_PATH =
      PathUtils.GetClassPath(PathEnum.FILE_ANALYZE_ERROR_HTML_PATH);

  /**
   * 获取网页内容信息
   *
   * @param fileEnum
   * @return
   */
  public static String ReadHtml(HtmlHrefFileEnum fileEnum) {

    String path = PathUtils.GetClassPath(PathEnum.FILE_ANALYZE_HTML_PATH);

    File sohoFile = new File(path, fileEnum.getFile());

    String htmlContext = null;

    try {
      htmlContext = FileUtils.readFileToString(sohoFile, StandardCharsets.UTF_8);

    } catch (IOException e) {
      e.printStackTrace();
    }

    if (null == htmlContext) {
      throw new IllegalArgumentException(fileEnum.getFile() + " file error");
    }

    return htmlContext;
  }

  /**
   * 获取错误文件列表
   *
   * @return
   */
  public static File[] GetErrorFileList() {
    File curFile = new File(BASE_ERROR_PATH);

    return curFile.listFiles();
  }

  /**
   * 获取错误文件列表
   *
   * @return
   */
  public static File GetErrorFile(String name) {
    File curFile = new File(BASE_ERROR_PATH);

    for (File fileItem : curFile.listFiles()) {
      if (fileItem.getName().equals(name)) {
        return fileItem;
      }
    }

    return null;
  }

  /**
   * 获取网页文件内容
   *
   * @param fileName 文件名
   * @return 网页内容
   */
  public static String GetContext(String fileName) {
    File currFile = GetErrorFile(fileName);

    if (null != currFile) {
      // 获取文件内容
      return GetContext(currFile);
    }

    return null;
  }

  /**
   * 获取网页文件内容
   *
   * @param file 文件信息
   * @return 网页内容
   */
  public static String GetContext(File file) {
    String htmlContext = null;

    try {
      htmlContext = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

    } catch (IOException e) {
      e.printStackTrace();
    }

    if (null == htmlContext) {
      throw new IllegalArgumentException(file + " file error");
    }

    return htmlContext;
  }
}
