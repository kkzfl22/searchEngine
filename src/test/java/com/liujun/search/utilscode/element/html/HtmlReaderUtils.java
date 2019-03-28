package com.liujun.search.utilscode.element.html;

import com.liujun.search.utilscode.element.constant.HtmlHrefFileEnum;
import com.liujun.search.utilscode.io.code.PathUtils;
import com.liujun.search.utilscode.io.constant.PathEnum;
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
}
