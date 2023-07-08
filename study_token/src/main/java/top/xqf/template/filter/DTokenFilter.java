package top.xqf.template.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.xqf.template.dao.TbTokenDAO;
import top.xqf.template.entity.TbToken;
import top.xqf.template.entity.TokenInfo;
import top.xqf.template.util.IpUtil;
import top.xqf.template.util.JsonUtil;
import top.xqf.template.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * 处理编码的过滤器
 *
 * @author 胡辉煜
 */
@WebFilter(filterName = "DTokenFilter", urlPatterns = "*.token")
public class DTokenFilter implements Filter {
  public static final String REQUEST_TOKEN_NAME = "server_token";
  private static Logger logger = LoggerFactory.getLogger(DTokenFilter.class);

  private TbTokenDAO tbTokenDAO = new TbTokenDAO();

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    logger.info("token过滤器初始化");
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    //第一步，获取对象中token信息
    HttpServletRequest request=(HttpServletRequest) servletRequest;
    String token=request.getParameter(REQUEST_TOKEN_NAME);
    token = token==null? "" :token.trim();
    TbToken tbToken=null;

    try {
      //去数据库校验token是否存在
      TbToken check=tbTokenDAO.queryByToken(token);
      if(check==null){
        //不存在就创建一个新的token
        token=UUID.randomUUID().toString();
        tbToken=new TbToken();
        TokenInfo tokenInfo=new TokenInfo();
        tokenInfo.setIp(IpUtil.getIpAddr(request));
        tbToken.setToken(token);
        tbToken.setTokenInfo(JsonUtil.stringify(tokenInfo));
        tbTokenDAO.Insert(tbToken);
      }else {
        //存在就更新ip地址
        tbToken=check;
        TokenInfo tokenInfo=tbToken.content();
        tokenInfo.setIp(IpUtil.getIpAddr(request));
        tbToken.setTokenInfo(JsonUtil.stringify(tokenInfo));
        tbTokenDAO.update(tbToken);
      }
    } catch (Exception e) {
      throw new ServletException(e);
    }

    //放在请求中
    logger.debug("token信息:{}",tbToken);
    request.setAttribute(REQUEST_TOKEN_NAME,tbToken);
    filterChain.doFilter(servletRequest,servletResponse);
  }


  @Override
  public void destroy() {

    logger.info("token过滤器销毁");
  }

}
