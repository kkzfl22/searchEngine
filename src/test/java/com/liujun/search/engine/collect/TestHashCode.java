package com.liujun.search.engine.collect;

import com.google.common.hash.Hashing;
import org.junit.Assert;
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
    int murmurcode2 =
        Hashing.murmur3_32(Integer.MAX_VALUE).hashString(url, StandardCharsets.UTF_8).asInt();
    int murmurcode3 =
        Hashing.murmur3_32(Integer.MAX_VALUE / 2).hashString(url, StandardCharsets.UTF_8).asInt();
    System.out.println("使用murmur3得到的hashCode:" + murmurcode);
    System.out.println("使用murmur3得到的hashCode:" + murmurcode2);
    System.out.println("使用murmur3得到的hashCode:" + murmurcode3);

    Assert.assertNotEquals(0, murmurcode);
  }



}
