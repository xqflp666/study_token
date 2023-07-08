package top.xqf.template.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.xqf.template.base.BaseResult;
import top.xqf.template.dao.TbTokenDAO;
import top.xqf.template.dao.TbUserDAO;
import top.xqf.template.entity.TbToken;
import top.xqf.template.entity.TbUser;
import top.xqf.template.entity.TokenInfo;
import top.xqf.template.filter.DTokenFilter;
import top.xqf.template.util.BeanTools;
import top.xqf.template.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginoutServlet",urlPatterns = "/loginout.token")
public class LoginoutServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(LoginoutServlet.class);
    private TbUserDAO tbUserDAO=new TbUserDAO();
    private TbTokenDAO tbTokenDAO=new TbTokenDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//      //  http://127.0.0.1:8080/study_token//loginout.token?server_token=62fee806-e116-4092-8520-554168463121
        TbToken token=(TbToken) req.getAttribute(DTokenFilter.REQUEST_TOKEN_NAME);
    try {
        BaseResult<String> result=new BaseResult<>();
            //删除token中的用户信息
            TokenInfo tokenInfo=token.content();
            tokenInfo.setUser(null);
            token.setTokenInfo(JsonUtil.stringify(tokenInfo));
            tbTokenDAO.update(token);
            //成功应答

            logger.debug("token信息：{}",token);
            result.setCode(200);
            result.setMessage("登出成功");
            result.setSuccess(true);
            result.setToken(token.getToken());
            resp.getWriter().println(JsonUtil.stringify(result));

        } catch (Exception e) {
            throw new ServletException(e);
        }

    }
}
