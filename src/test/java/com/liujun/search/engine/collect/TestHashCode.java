package com.liujun.search.engine.collect;

import com.google.common.hash.Hashing;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/04
 */
public class TestHashCode {

  /** 测试hashCode */
  @Test
  public void testmurmur3() {
    String url = "http://www.baidu.com";

    int murmurcode = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).asInt();
    System.out.println("使用murmur3得到的hashCode:" + murmurcode);
  }
}
