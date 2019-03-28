package com.liujun.search.common.classes;

import java.io.File;
import java.lang.reflect.Field;

/**
 * 对象的属性操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/28
 */
public class ObjectFieldOper {

  private static final String BASE_CLASS = "java.lang.Object";

  /** 实例对象 */
  public static final ObjectFieldOper INSTANCE = new ObjectFieldOper();

  /**
   * 设置属性值的方法
   *
   * @param instance 实例对象
   * @param name 名称
   * @param value 设置的值
   */
  public void setFieldValue(Object instance, String name, Object value) {

    Class objclass = instance.getClass();
    try {

      Field field = getField(objclass, name);

      if (null == field) {
        throw new NoSuchFieldException("field " + name + "no such");
      }

      // 设置为true，无视java的封装
      field.setAccessible(true);
      field.set(instance, value);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }
  }

  /**
   * 递归查找父级中的所有属性
   *
   * @param objclass 类型
   * @param name 名称
   * @return
   */
  private Field getField(Class objclass, String name) {

    String className = objclass.getName();

    // 找到根路径，则退出
    if (BASE_CLASS.equals(className)) {
      return null;
    }

    Field findField = null;

    Field[] fields = objclass.getDeclaredFields();

    for (Field fieldItem : fields) {
      if (fieldItem.getName().equals(name)) {
        findField = fieldItem;
        break;
      }
    }

    if (null == findField) {
      return getField(objclass.getSuperclass(), name);
    }

    return findField;
  }
}
