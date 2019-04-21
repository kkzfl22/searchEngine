package com.liujun.search.engine.analyze.operation.htmlanalyze.process;

import com.liujun.search.engine.analyze.pojo.DataTagPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * 进行标签段的转换操作
 *
 * @author liuju
 * @version 0.0.1
 * @date 2019/04/20
 */
public class DataTagPosCommonProc {

  public static final DataTagPosCommonProc INSTANCE = new DataTagPosCommonProc();

  /**
   * 进行内容段的转化操作
   *
   * @param dataTagList
   * @return
   */
  private List<DataTagPosition> parseContextSection(List<DataTagPosition> dataTagList, int length) {

    List<DataTagPosition> newTagSection = new ArrayList<>();

    if (dataTagList == null || dataTagList.isEmpty()) {
      return newTagSection;
    }

    DataTagPosition dataNewTag = new DataTagPosition();

    DataTagPosition item;

    if (dataTagList.size() > 1) {
      for (int i = 0; i < dataTagList.size(); i++) {

        item = dataTagList.get(i);

        // 如果当前为第一个，需要记录
        if (i == 0) {
          if (item.getStart() == 0) {
            dataNewTag.setStart(item.getEnd());
          } else {
            dataNewTag.setStart(0);
            dataNewTag.setEnd(item.getStart());
            newTagSection.add(dataNewTag);
            dataNewTag = new DataTagPosition();
            dataNewTag.setStart(item.getEnd());
          }
        }
        // 最后一个需要与最长字符做比较
        else if (i == dataTagList.size() - 1) {
          dataNewTag.setEnd(item.getStart());
          newTagSection.add(dataNewTag);

          if (item.getEnd() < length) {
            dataNewTag = new DataTagPosition();
            dataNewTag.setStart(item.getEnd());
            dataNewTag.setEnd(length);
            newTagSection.add(dataNewTag);
          }
        }
        // 中间段直接处理
        else {
          dataNewTag.setEnd(item.getStart());
          newTagSection.add(dataNewTag);
          dataNewTag = new DataTagPosition();
          dataNewTag.setStart(item.getEnd());
        }
      }
    } else if (dataTagList.size() == 1) {
      item = dataTagList.get(0);
      dataNewTag.setStart(0);
      dataNewTag.setEnd(item.getStart());
      newTagSection.add(dataNewTag);

      dataNewTag = new DataTagPosition();
      dataNewTag.setStart(item.getEnd());
      dataNewTag.setEnd(length);
      newTagSection.add(dataNewTag);
    }

    return newTagSection;
  }

  /**
   * 进行重组网页中的内容
   *
   * @param htmlArray 原始网页信息
   * @param dataTagList 网页标签
   * @return
   */
  public char[] htmlRegroup(char[] htmlArray, List<DataTagPosition> dataTagList) {

    if (dataTagList.isEmpty()) {
      return htmlArray;
    }

    // 查询
    List<DataTagPosition> dataPraseScopeList =
        this.parseContextSection(dataTagList, htmlArray.length);

    // 1,统计去掉段的大小
    int countNewLen = 0;

    for (DataTagPosition dataItem : dataPraseScopeList) {
      countNewLen += (dataItem.getEnd() - dataItem.getStart());
    }

    char[] newContextCon = new char[countNewLen];

    int targetStart = 0;

    for (DataTagPosition dataItem : dataPraseScopeList) {
      int copyDataLength = dataItem.getEnd() - dataItem.getStart();
      System.arraycopy(htmlArray, dataItem.getStart(), newContextCon, targetStart, copyDataLength);
      targetStart += copyDataLength;
    }

    return newContextCon;
  }
}
