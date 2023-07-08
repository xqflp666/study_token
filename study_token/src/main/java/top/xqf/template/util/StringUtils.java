package top.huhuiyu.servlet.util;

/**
 * 字符串工具类
 *
 * @author 胡辉煜
 */
public class StringUtils {
  /**
   * 判断字符串是否包含字符内容，null或者全是空白字符都会返回false
   *
   * @param info 要检测的字符串
   * @return 符串是否包含字符内容
   */
  public static boolean hasTest(String info) {
    if (info == null || "".equals(info.trim())) {
      return false;
    }
    return true;
  }
}
