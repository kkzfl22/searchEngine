package com.liujun.search.engine.index.mergeIndex;

import com.liujun.search.engine.index.outputDescIndex.DescIndexOutput;
import com.liujun.search.engine.index.pojo.SortTempIndexData;
import com.liujun.search.engine.index.pojo.TempIndexData;

import java.io.File;
import java.util.*;

/**
 * 进行有序的索引文件合并操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/05/10
 */
public class MergeSortIndex {

  public static final MergeSortIndex INSTANCE = new MergeSortIndex();

  /** 索引文件数据 */
  private SortIndexFileReader[] indexFiles;

  /** 临时索引文件信息 */
  private SortTempIndexData[] cacheSortDatasFile;

  /** 进行倒排索引文件的合并排序操作 */
  public void mergeIndex() {
    // 1,获取文件集合
    File[] lists = SortIndexFile.INSTANCE.indexFiles();

    // 不存在排序完成的临时索引文件，则退出
    if (lists.length == 0) {
      return;
    }

    // 1,文件初始化
    this.fileInit(lists);

    // 2,进行首次的读取操作
    firstReader();

    List<TempIndexData> tempIndexList = new ArrayList<>();
    List<Integer> nextReadIndex = null;

    int wordId = -1;

    int runNum = 0;

    do {
      // 进行数据的排序操作
      List<TempIndexData> sortList = getSortList();

      // 首次使用排序后的第一个单词的id
      if (wordId == -1) {
        wordId = sortList.get(0).getTempId();
      }
      // 其他则需要检查是否与上一次读取的单词id一致
      else {
        // 当一个id读取完成后，则需要将同一个word的不同docid输出到文件中
        if (wordId != sortList.get(0).getTempId()) {
          // 获取最新的单词的id
          wordId = sortList.get(0).getTempId();
          // 数据进行输出，然后清空对应的集合信息
          DescIndexOutput.INSTANCE.writeIndex(tempIndexList);
          // 完成后进行清理操作
          tempIndexList.clear();
        }
      }

      // 对wordid相同的数据进行聚合操作
      nextReadIndex = tempIndexGroup(tempIndexList, wordId);

      // 进行下一次的读取操作
      nextReader(nextReadIndex);

      runNum++;

    } while (nextReadCheck());

    // 当最后还存在数据，则城要进行输出操作
    if (!tempIndexList.isEmpty()) {
      // 完成后检查一遍，作最后的输出操作
      // 数据进行输出，然后清空对应的集合信息
      DescIndexOutput.INSTANCE.writeIndex(tempIndexList);

      DescIndexOutput.INSTANCE.close();
    }
  }

  /**
   * 检查是否进行下一次的读取操作
   *
   * @return
   */
  private boolean nextReadCheck() {
    boolean check = false;

    for (SortIndexFileReader indexFiles : indexFiles) {

      if (!indexFiles.finishFlag()) {
        check = true;
        break;
      }
    }

    return check;
  }

  /**
   * 进行临时索引文件的按word的id进行分组操作
   *
   * @param tempIndexList 索引集合
   * @return
   */
  private List<Integer> tempIndexGroup(List<TempIndexData> tempIndexList, int wordId) {
    List<Integer> indexNextReader = new ArrayList<>();

    for (int i = 0; i < cacheSortDatasFile.length; i++) {
      // 当分词相同时，则继续遍历，不同则不再需要遍历
      if (null != cacheSortDatasFile[i].getIndexData()
          && wordId == cacheSortDatasFile[i].getIndexData().getTempId()) {
        tempIndexList.add(cacheSortDatasFile[i].getIndexData());
        indexNextReader.add(cacheSortDatasFile[i].getIndex());
      }
    }

    return indexNextReader;
  }

  /**
   * 进行文件的初始化操作
   *
   * @param lists
   */
  private void fileInit(File[] lists) {
    indexFiles = new SortIndexFileReader[lists.length];
    cacheSortDatasFile = new SortTempIndexData[lists.length];
    // 进行文件的初始化操作
    for (int i = 0; i < lists.length; i++) {
      indexFiles[i] = new SortIndexFileReader(lists[i]);
      cacheSortDatasFile[i] = new SortTempIndexData();
      cacheSortDatasFile[i].setIndex(i);
    }
  }

  /** 进行文件的首次读取操作 */
  public void firstReader() {
    for (int i = 0; i < indexFiles.length; i++) {
      if (!indexFiles[i].finishFlag()) {
        cacheSortDatasFile[i].setIndexData(indexFiles[i].getIndexData());
      }
    }
  }

  /** 进行指定索引文件的读取操作 */
  public void nextReader(List<Integer> nextReadIndex) {
    for (int index : nextReadIndex) {
      if (!indexFiles[index].finishFlag()) {
        cacheSortDatasFile[index].setIndexData(indexFiles[index].getIndexData());
      }
    }
  }

  /**
   * 获取排序的数据集合
   *
   * @return
   */
  private List<TempIndexData> getSortList() {
    List<TempIndexData> tempList = new ArrayList<>(cacheSortDatasFile.length);

    for (SortTempIndexData sortTmp : cacheSortDatasFile) {
      if (null != sortTmp.getIndexData()) {
        tempList.add(sortTmp.getIndexData());
      }
    }

    Collections.sort(tempList);

    return tempList;
  }
}
