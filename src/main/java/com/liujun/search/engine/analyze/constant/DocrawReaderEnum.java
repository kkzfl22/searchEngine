package com.liujun.search.engine.analyze.constant;

/**
 * 进行docraw文件读取的枚举信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/08
 */
public enum DocrawReaderEnum {

  /** 公用的内存缓冲区 */
  DOCRAW_INPUT_READER_BUFFER("docraw_input_reader_buffer"),

  /** 文件列表，需待处理的文件列表 */
  DOCRAW_INPUT_FILE_LIST("docraw_input_reader_fileLis"),

  /** 基础的文件路径信息 */
  DOCRAW_INPUT_BASE_PATH("docraw_input_base_path"),

  /** 开始处理的文件索引 */
  DOCRAW_INPUT_FILE_INDEX("docraw_input_file_index"),

  /** 开始处理的文件的位置 */
  DOCRAW_INPUT_FILE_POSITION("docraw_input_file_position"),

  /** 遍历的结果中的数据信息 */
  DOCRAW_INPUT_RESULT_LIST("docraw_input_resultlist"),

  /** 输入的文件流信息 */
  DOCRAW_PROC_INPUT_STREAM("docraw_proc_input_stream"),

  /** 输入的文件流通道信息 */
  DOCRAW_PROC_INPUT_CHANNEL("docraw_proc_input_channel"),

  /** 当前读取的大小 */
  DOCRAW_PROC_READ_BUFFERSIZE("docraw_proc_reader_buffersize"),

  /** 用于缓存临时数据的 */
  DOCRAW_PROC_CACHE_BUFFERLIST("docraw_proc_cahce_buffer_list"),
  ;

  private String key;

  DocrawReaderEnum(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
