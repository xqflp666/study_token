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

@WebServlet(name = "LoginServlet",urlPatterns = "/login.token")
public class LoginServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private TbUserDAO tbUserDAO=new TbUserDAO();
    private TbTokenDAO tbTokenDAO=new TbTokenDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//      //  http://127.0.0.1:8080/study_token//login.token?server_token=62fee806-e116-4092-8520-554168463121&username=ceshi&password=12345
        TbToken token=(TbToken) req.getAttribute(DTokenFilter.REQUEST_TOKEN_NAME);
        resp.setContentType("text/plain");
        //获取参数
        try {
            TbUser user= BeanTools.mapping(req.getParameterMap(),TbUser.class);
            logger.debug("用户信息：{}",user);
            TbUser check = tbUserDAO.queryByUsername(user);
            BaseResult<TbUser> result=new BaseResult<>();
            //检测用户名是否存在
            if(check==null){
                result.setCode(500);
                result.setMessage("用户不存在");
                result.setSuccess(false);
                result.setToken(token.getToken());
                resp.getWriter().println(JsonUtil.stringify(result));
                return;
            }
            //检测密码是否正确
            if(!check.getPassword().equals(user.getPassword())){
                result.setCode(500);
                result.setMessage("密码错误");
                result.setSuccess(false);
                result.setToken(token.getToken());
                resp.getWriter().println(JsonUtil.stringify(result));
                return;
            }
            //检测是否启用
            String enable= "y";
            if(!enable.equals(check.getEnable())){
                result.setCode(500);
                result.setMessage("用户未启用");
                result.setSuccess(false);
                result.setToken(token.getToken());
                resp.getWriter().println(JsonUtil.stringify(result));
                return;
            }
            //成功复制token绑定
            TokenInfo tokenInfo=token.content();
            tokenInfo.setUser(check);
            token.setTokenInfo(JsonUtil.stringify(tokenInfo));
            tbTokenDAO.update(token);
            //成功应答

            logger.debug("token信息：{}",token);
            result.setCode(200);
            result.setMessage("登入成功");
            result.setSuccess(true);
            result.setToken(token.getToken());
            resp.getWriter().println(JsonUtil.stringify(result));

        } catch (Exception e) {
            throw new ServletException(e);
        }

    }
}
