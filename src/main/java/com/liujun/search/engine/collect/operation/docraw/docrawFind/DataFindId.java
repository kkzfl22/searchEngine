package com.liujun.search.engine.collect.operation.docraw.docrawFind;

import com.liujun.search.algorithm.boyerMoore.use.CharMatcherBMBadChars;
import com.liujun.search.utilscode.io.constant.SymbolMsg;
import com.liujun.search.common.flow.FlowServiceContext;
import com.liujun.search.common.flow.FlowServiceInf;
import com.liujun.search.engine.collect.constant.DocRawFindEnum;

/**
 * 进行id的查找操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/24
 */
public class DataFindId implements FlowServiceInf {

  public static final DataFindId INSTANCE = new DataFindId();

  @Override
  public boolean runFlow(FlowServiceContext context) throws Exception {

    long id = context.getObject(DocRawFindEnum.INPUT_FIND_ID.getKey());

    // 1,检查当前id的索引位置是否被找到
    Boolean findFlag = context.getObject(DocRawFindEnum.PROC_FIND_ID_FLAG.getKey());

    if (null == findFlag) {

      // 检查当前是否已经存在匹配器对象
      CharMatcherBMBadChars idMatchers =
          context.getObject(DocRawFindEnum.PROC_FIND_MATCHER_OBJ.getKey());

      // 如果不存在，构建匹配器对象
      if (null == idMatchers) {
        String matcherChars = String.valueOf(id) + SymbolMsg.DATA_COLUMN;
        CharMatcherBMBadChars idMatchersCreate =
            CharMatcherBMBadChars.getGoodSuffixInstance(matcherChars);

        context.put(DocRawFindEnum.PROC_FIND_MATCHER_OBJ.getKey(), idMatchersCreate);

        idMatchers = idMatchersCreate;
      }

      // 检查当前id是否能匹配成功
      byte[] bufferBytes = context.getObject(DocRawFindEnum.INPUT_SRC_CHARS.getKey());
      int idFindIndex = idMatchers.matcherIndex(bufferBytes, 0);

      if (idFindIndex != -1) {

        // 记录下查找到的索引的位置
        context.put(DocRawFindEnum.PROC_FIND_ID_INDEX.getKey(), idFindIndex);
        // 标识当前的id被查找到
        context.put(DocRawFindEnum.PROC_FIND_ID_FLAG.getKey(), true);
        // 同时移除id匹配器对象
        context.remove(DocRawFindEnum.PROC_FIND_MATCHER_OBJ.getKey());

        return true;
      } else {
        // 如果当前未找到则退出当前查找，进行下一次查找操作
        return false;
      }
    }

    return true;
  }
}
