package top.huhuiyu.servlet.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.huhuiyu.servlet.util.IpUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

/**
 * 日志信息过滤器
 *
 * @author 胡辉煜
 */
@WebFilter(filterName = "CLoggerFilter", urlPatterns = "/*")
public class CLoggerFilter implements Filter {
  public static final String JOIN = ",";
  private static Logger logger = LoggerFactory.getLogger(CLoggerFilter.class);

  /**
   * 合并字符串数组成字符串
   *
   * @param infos 要合并的数组
   * @return 合并后的字符串结果
   */
  private String join(String[] infos) {
    return join(infos, JOIN);
  }

  /**
   * 合并字符串数组成字符串
   *
   * @param infos 要合并的数组
   * @param join  合并的连接字符
   * @return 合并后的字符串结果
   */
  private String join(String[] infos, String join) {
    if (infos == null || infos.length < 1) {
      return "";
    }
    join = (join == null || "".equals(join)) ? JOIN : join;
    StringBuilder sb = new StringBuilder();
    for (String info : infos) {
      sb.append(info).append(join);
    }
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    logger.info("日志过滤器初始化");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    Map<String, String[]> params = req.getParameterMap();
    try {
      logger.debug("请求地址：{},客户端ip：{}", req.getRequestURI(), IpUtil.getIpAddr(req));
    } catch (Exception ex) {
    }
    logger.debug("请求参数信息：{}", params.size());
    for (String key : params.keySet()) {
      logger.debug("{}:{}", key, join(params.get(key)));
    }
    logger.debug("请求头信息：");
    Enumeration<String> heads = req.getHeaderNames();
    while (heads.hasMoreElements()) {
      String head = heads.nextElement();
      if (head.startsWith("sec-") || head.startsWith("accept") || head.startsWith("upgrade-insecure") || head.startsWith("connection") || head.startsWith("cache-control")) {
        continue;
      }
      logger.debug("{}:{}", head, req.getHeader(head));
    }
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    logger.info("日志过滤器销毁");
  }

}
