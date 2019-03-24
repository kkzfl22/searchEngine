package com.liujun.search.utilscode.element.html;

import com.liujun.search.engine.collect.constant.WebEntryEnum;
import com.liujun.search.engine.collect.operation.html.HtmlHrefAnalyze;
import com.liujun.search.utilscode.element.constant.HtmlHrefFileEnum;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * 测试网页链接的分析操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/20
 */
public class HtmlHrefUtils {

  private Logger logger = LoggerFactory.getLogger(HtmlHrefUtils.class);

  public static final HtmlHrefUtils INSTANCE = new HtmlHrefUtils();

  /** 分析的网页路径 */
  private static final String ANALLYZE_PATH = "html/analyze";

  private static final String BASE_PATH =
      HtmlHrefUtils.class.getClassLoader().getResource(ANALLYZE_PATH).getPath();

  /**
   * 获取网页链接信息
   *
   * @param file 文件名称
   * @return 链接的集合信息
   * @throws IOException 异常信息
   */
  public Set<String> getHrefUrl(HtmlHrefFileEnum file) {

    // 获取网页内容
    String htmlContext = this.getHtmlContext(file);

    // 进行网页分析
    Set<String> hrefSet = HtmlHrefAnalyze.INSTANCE.getHref(htmlContext);

    return hrefSet;
  }

  /**
   * 获取网页内容信息
   *
   * @param file 网页文件信息
   * @return 网页内容
   */
  public String getHtmlContext(HtmlHrefFileEnum file) {

    String htmlContext = null;
    try {
      File sohoFile = new File(BASE_PATH, file.getFile());
      htmlContext = FileUtils.readFileToString(sohoFile, StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("HtmlHrefUtils getHtmlContext IOException", e);
    }

    return htmlContext;
  }
}
