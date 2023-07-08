package top.huhuiyu.servlet.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理跨域的过滤器
 *
 * @author 胡辉煜
 */
@WebFilter(filterName = "BCrossDomainFilter", urlPatterns = "/*")
public class BCrossDomainFilter implements Filter {
  private static Logger logger = LoggerFactory.getLogger(BCrossDomainFilter.class);

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    logger.info("跨域配置过滤器初始化");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    logger.debug("跨域处理");
    HttpServletResponse resp = (HttpServletResponse) response;
    // 处理跨越
    resp.setHeader("Access-Control-Allow-Origin", "*");
    resp.setHeader("Access-Control-Allow-Methods", "*");
    resp.setHeader("Access-Control-Allow-Headers", "*");
    resp.setHeader("Access-Control-Expose-Headers", "*");
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    logger.info("跨域配置过滤器销毁");
  }

}
