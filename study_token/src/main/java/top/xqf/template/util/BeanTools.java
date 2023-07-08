package top.huhuiyu.servlet.util;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

/**
 * 反射工具
 *
 * @author 胡辉煜
 */
public class BeanTools {

  /**
   * 使用map的数据创建对应类型的实例
   *
   * @param map 实例的字段数据
   * @param c   实例的类型
   * @param <T> 类型泛型参数
   * @return 对应数据的对象实例
   * @throws Exception 无法创建对应的对象
   */
  public static <T> T mapping(Map<String, ? extends Object> map, Class<T> c) throws Exception {
    T t = c.getDeclaredConstructor().newInstance();
    BeanUtils.populate(t, map);
    return t;
  }

  /**
   * 使用map的数据填充对象
   *
   * @param map 实例的字段数据
   * @param t   要填充字段的对象
   * @param <T> 类型泛型参数
   * @return 填充字段后的对象
   * @throws Exception 无法创建对应的对象
   */
  public static <T> T mapping(Map<String, ? extends Object> map, T t) throws Exception {
    BeanUtils.populate(t, map);
    return t;
  }
}
