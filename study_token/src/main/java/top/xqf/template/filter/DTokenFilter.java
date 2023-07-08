package top.huhuiyu.servlet.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.huhuiyu.servlet.dao.TbTokenDAO;
import top.huhuiyu.servlet.entity.TbToken;
import top.huhuiyu.servlet.entity.TokenInfo;
import top.huhuiyu.servlet.util.IpUtil;
import top.huhuiyu.servlet.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

/**
 * 处理编码的过滤器
 *
 * @author 胡辉煜
 */
@WebFilter(filterName = "DTokenFilter", urlPatterns = "*.token")
public class DTokenFilter implements Filter {
  private static Logger logger = LoggerFactory.getLogger(DTokenFilter.class);
  private static final String REQUEST_TOKEN = "javaweb_template_token";
  private static final String HEAD_TOKEN = "token";
  private static final String REQUEST_TOKEN_ATTRIBUTE = "javaweb_template_token";
  private TbTokenDAO tbTokenDAO = new TbTokenDAO();

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    logger.info("token过滤器初始化");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    logger.debug("token信息处理");
    HttpServletRequest req = (HttpServletRequest) request;
    // 获取请求头中的token
    String token = req.getHeader(HEAD_TOKEN);
    // 不存在就获取请求中的token
    if (!StringUtils.hasTest(token)) {
      token = req.getParameter(REQUEST_TOKEN);
    }
    token = Optional.ofNullable(token).orElse("");
    logger.debug("客户拿到的token信息：{}", token);
    // 获取数据库中的token信息
    TbToken tbToken = new TbToken();
    tbToken.setToken(token);
    try {
      tbToken = tbTokenDAO.selectOrInsert(tbToken);
      // 更新ip地址信息
      TokenInfo tokenInfo = tbToken.content();
      tokenInfo.setIp(IpUtil.getIpAddr(req));
      tbToken.setTokenInfo(tokenInfo.toString());
      // 将token信息放到请求中
      req.setAttribute(REQUEST_TOKEN_ATTRIBUTE, tbToken);
      logger.debug("处理后的token信息：{}", tbToken);
    } catch (Exception e) {
      throw new ServletException(e);
    }
    chain.doFilter(request, response);
    try {
      // 保存最新的token信息
      tbTokenDAO.update(tbToken);
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }

  /**
   * 获取请求中token信息
   *
   * @param request 请求对象
   * @return 请求中token信息
   */
  public static TbToken getTokenInfo(HttpServletRequest request) {
    return (TbToken) request.getAttribute(REQUEST_TOKEN_ATTRIBUTE);
  }

  @Override
  public void destroy() {
    logger.info("token过滤器销毁");
  }

}
