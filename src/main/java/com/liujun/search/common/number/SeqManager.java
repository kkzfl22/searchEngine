package com.liujun.search.common.number;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 序列号管理器对象
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/28
 */
public class SeqManager {

  /** 序列号管理器对象 */
  public static final SeqManager INSTANCE = new SeqManager();

  /** 序列号存储器对象 */
  private static final ConcurrentHashMap<SeqNameEnum, NumberLoopSeq> SEQ_MAP =
      new ConcurrentHashMap<>();

  /**
   * 获取序列号
   *
   * @param nameEnum 名称枚举
   * @return 序列号生成器
   */
  public NumberLoopSeq getOrCreateSeqNum(SeqNameEnum nameEnum) {

    NumberLoopSeq numSeq = SEQ_MAP.get(nameEnum);

    if (null == numSeq) {
      return createSeqNum(nameEnum);
    }

    return numSeq;
  }

  /**
   * 创建一个序列号生成器
   *
   * @param nameEnum
   * @return
   */
  private NumberLoopSeq createSeqNum(SeqNameEnum nameEnum) {

    NumberLoopSeq instanceObj = NumberLoopSeq.getNewInstance();

    NumberLoopSeq putRsp = SEQ_MAP.putIfAbsent(nameEnum, instanceObj);

    if (putRsp == null) {
      return instanceObj;
    }

    return putRsp;
  }
}
