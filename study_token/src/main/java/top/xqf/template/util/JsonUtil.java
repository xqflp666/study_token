package top.huhuiyu.servlet.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json处理工具类
 *
 * @author 胡辉煜
 */
public class JsonUtil {
  private static final ObjectMapper MAPPER = new ObjectMapper();

  static {
    MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  /**
   * 获取对象包装对象
   *
   * @return 对象包装对象
   */
  public static ObjectMapper getMapper() {
    return MAPPER;
  }

  /**
   * 转换对象为json字符串
   *
   * @param obj java对象
   * @return json字符串
   * @throws Exception 转换发生异常
   */
  public static String stringify(Object obj) throws Exception {
    return MAPPER.writeValueAsString(obj);
  }

  /**
   * 转换json字符串为对象
   *
   * @param <T>  泛型参数
   * @param json json字符串
   * @param c    对象类型
   * @return 对象转换结果
   * @throws Exception 转换发生异常
   */
  public static <T> T parse(String json, Class<T> c) throws Exception {
    return MAPPER.readValue(json, c);
  }

  /**
   * 转换json字符串为对象
   *
   * @param <T>  泛型参数
   * @param json json字符串
   * @param type 封装的java类型
   * @return 对象转换结果
   * @throws Exception 转换发生异常
   */
  public static <T> T parse(String json, JavaType type) throws Exception {
    return MAPPER.readValue(json, type);
  }

  /**
   * 转换json字符串为泛型对象
   *
   * @param <T>   泛型参数
   * @param json  json字符串
   * @param base  基本类型
   * @param types 泛型参数
   * @return 对象转换结果
   * @throws Exception 转换发生异常
   */
  public static <T> T parse(String json, Class<?> base, Class<?>... types) throws Exception {
    return MAPPER.readValue(json, getParametricType(base, types));
  }

  /**
   * 获取泛型的JavaType
   *
   * @param base  基本类型
   * @param types 泛型参数
   * @return 泛型的JavaType
   */
  public static JavaType getParametricType(Class<?> base, Class<?>... types) {
    return MAPPER.getTypeFactory().constructParametricType(base, types);
  }

}
