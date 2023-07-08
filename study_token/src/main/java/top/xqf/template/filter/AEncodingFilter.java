package top.huhuiyu.servlet.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理编码的过滤器
 *
 * @author 胡辉煜
 */
@WebFilter(filterName = "AEncodingFilter", urlPatterns = "/*")
public class AEncodingFilter implements Filter {

  private static Logger logger = LoggerFactory.getLogger(AEncodingFilter.class);
  private static final String ENCODING_UTF_8 = "UTF-8";

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    logger.info("编码过滤器初始化:{}", ENCODING_UTF_8);
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    logger.debug("编码处理");
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;
    // 处理编码
    req.setCharacterEncoding(ENCODING_UTF_8);
    resp.setCharacterEncoding(ENCODING_UTF_8);
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    logger.info("编码过滤器销毁");
  }

}
