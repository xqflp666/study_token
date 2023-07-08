package top.xqf.template.servlet;

import top.xqf.template.entity.TbToken;
import top.xqf.template.filter.DTokenFilter;
import top.xqf.template.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetTokenServlet",urlPatterns = "/get.token")
public class GetTokenServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        http://127.0.0.1:8080/study_token//get.token?server_token=72eb1106-e302-446c-9d38-e48189eb5249
        TbToken token=(TbToken) req.getAttribute(DTokenFilter.REQUEST_TOKEN_NAME);
        resp.setContentType("text/html");
        try {
            resp.getWriter().println(JsonUtil.stringify(token));
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
