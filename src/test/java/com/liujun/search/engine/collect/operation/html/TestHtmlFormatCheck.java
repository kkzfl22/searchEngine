package com.liujun.search.engine.collect.operation.html;

import com.liujun.search.utilscode.io.code.PathUtils;
import com.liujun.search.utils.PathEnum;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 进行网页内容的检查
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/07
 */
public class TestHtmlFormatCheck {

  private static final String BASE_PATH = PathUtils.GetClassPath(PathEnum.FILE_ANALYZE_HTML_PATH);

  private static final String BASE_ERROR_PATH =
      PathUtils.GetClassPath(PathEnum.FILE_ANALYZE_ERROR_HTML_PATH);

  /**
   * 解决bug的问题
   *
   * @param fileSoho 错误文件
   * @return 文件信息
   * @throws IOException 异常
   */
  private boolean checkHtml(String fileSoho) throws IOException {
    File sohoFile = new File(BASE_ERROR_PATH, fileSoho);

    String htmlContext = FileUtils.readFileToString(sohoFile, StandardCharsets.UTF_8);

    if (StringUtils.isNotEmpty(htmlContext)) {
      return HtmlFormatCheck.INSTANCE.checkHtml(htmlContext.toCharArray(), 0);
    }

    return true;
  }

  @Test
  public void checkHtml() throws IOException {

    File fileList = new File(BASE_ERROR_PATH);

    String[] listFileNames = fileList.list();

    for (String nameValues : listFileNames) {

      boolean htmlFlag = this.checkHtml(nameValues);
      System.out.println("检查网页：" + nameValues);
      Assert.assertEquals(htmlFlag, true);
    }
  }
}
