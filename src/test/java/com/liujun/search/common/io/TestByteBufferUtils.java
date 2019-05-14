package com.liujun.search.common.io;

import com.liujun.search.utilscode.element.constant.HtmlHrefFileEnum;
import com.liujun.search.utilscode.element.html.HtmlReaderUtils;
import com.liujun.search.utilscode.io.code.PathUtils;
import com.liujun.search.utilscode.io.constant.PathEnum;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * 测试数据进入缓冲区并写入文件的操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/27
 */
public class TestByteBufferUtils {

  /** 获取文件信息 */
  private File outFile;

  @Before
  public void getOutFile() {
    String path = PathUtils.GetClassPath(PathEnum.FILE_OUTPUT_IO);
    String outFileStr = path + "testOutput.txt";
    outFile = new File(outFileStr);
  }

  /** 测试数据入软件的缓冲区或者文件的操作方法 */
  @Test
  public void tetsBigByteBufferUtils() throws IOException {

    String bigSinaHtml = HtmlReaderUtils.ReadHtml(HtmlHrefFileEnum.SINA);

    // 写入磁盘操作
    int wirteBytes = this.writeData(outFile, bigSinaHtml);

    System.out.println("字符长度：" + bigSinaHtml.length());
    System.out.println("写入长度：" + wirteBytes);

    // 读取写入磁盘的网页内容
    String contextValue = FileUtils.readFileToString(outFile, StandardCharsets.UTF_8);

    Assert.assertEquals(bigSinaHtml, contextValue);
  }

  /**
   * 进行小文件内容的测试
   *
   * @throws IOException 异常信息
   */
  @Test
  public void testSmailHtml() throws IOException {

    String bigSmallHtml = HtmlReaderUtils.ReadHtml(HtmlHrefFileEnum.SMALL);

    // 写入磁盘操作
    int wirteBytes = this.writeData(outFile, bigSmallHtml);

    System.out.println("字符长度：" + bigSmallHtml.length());
    System.out.println("写入长度：" + wirteBytes);

    // 读取写入磁盘的网页内容
    String contextValue = FileUtils.readFileToString(outFile, StandardCharsets.UTF_8);

    Assert.assertEquals(bigSmallHtml, contextValue);
  }

  /**
   * 写入数据内容
   *
   * @param outFile 输入文件信息
   * @param outContext 文件内容
   */
  private int writeData(File outFile, String outContext) {
    FileOutputStream input = null;
    FileChannel channel = null;
    try {
      input = new FileOutputStream(outFile, true);
      channel = input.getChannel();
      ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
      return ByteBufferUtils.wirteChannel(byteBuffer, channel, outContext);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      CommonIOUtils.close(channel);
      CommonIOUtils.close(input);
    }

    return 0;
  }

  /** 进行删除操作 */
  @After
  public void clean() {
    outFile.delete();
  }
}
