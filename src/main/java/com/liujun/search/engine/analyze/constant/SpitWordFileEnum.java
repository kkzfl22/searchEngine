package com.liujun.search.engine.analyze.constant;

/**
 * 分词的文件信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/20
 */
public enum SpitWordFileEnum {

  /** 英文分词的词库 */
  EN_WORKD("en_word_103977.csv"),

  /** 中文分词库动物 */
  CH_WORD_ANIMAL("THUOCL_animal.txt"),

  /** 中文分词库财经 */
  CH_WORD_CJ("THUOCL_caijing.txt"),

  /** 中文分词词库汽车 */
  CH_WORD_CAR("THUOCL_car.txt"),

  /** 中文分词词库成语 */
  CH_WORD_CY("THUOCL_chengyu.txt"),

  /** 中文分词地名 */
  CH_WORD_DM("THUOCL_diming.txt"),

  /** 中文分词食物 */
  CH_WORD_FOOD("THUOCL_food.txt"),

  /** 中文分词it */
  CH_WORD_IT("THUOCL_it.txt"),

  /** 中文分词法律 */
  CH_WORD_LAW("THUOCL_law.txt"),

  /**中文分词历史名人 */
  CH_WORD_LSMR("THUOCL_lishimingren.txt"),

  /** 中文分词医学 */
  CH_WORD_MEDICAL("THUOCL_medical.txt"),

  /** 中文分词诗 */
  CH_WORD_POEM("THUOCL_poem.txt"),
  ;

  private String file;

  SpitWordFileEnum(String file) {
    this.file = file;
  }

  public String getFile() {
    return file;
  }
}
