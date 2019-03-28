package com.liujun.search.utilscode.io.code;

import com.liujun.search.utilscode.io.constant.PathEnum;

import java.io.File;
import java.net.URL;

/**
 * 进行路径操作的一些公共方法
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/27
 */
public class PathUtils {

  /**
   * 获取当前系统工程中的目录
   *
   * @param path 定义的目录信息
   * @return 目录信息,如果不存在，则返回为null
   */
  public static String GetClassPath(PathEnum path) {
    URL url = PathUtils.class.getClassLoader().getResource(path.getPath());

    if (null == url) {
      throw new IllegalArgumentException("path loader error");
    }

    return url.getPath() + File.separator;
  }
}
