package com.liujun.search.engine.query.cache;

import com.liujun.search.engine.query.process.DocIdReaderProcess;

import java.util.HashMap;
import java.util.Map;

/**
 * 进行网页id与网页链接的缓存操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/13
 */
public class DocIdCache {

  public static final DocIdCache INSTANCE = new DocIdCache();

  private static final Map<Long, String> DOCIDMAP = new HashMap<>();

  static {
    // 进行初始化操作
    Init();
  }

  /** 进行初始化操作 */
  public static void Init() {
    // 在不确定大小的情况下，使用默认的大小
    DocIdReaderProcess.INSTANCE.loadDocMap(DOCIDMAP);
  }

  public String getUrl(Long docId) {
    return DOCIDMAP.get(docId);
  }
}
