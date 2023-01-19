package com.web.Servlet;

import com.pojo.User;
import com.service.UserListService;
import com.util.JwtUitls;
import com.util.SqlSessionFactoryUtils;
import com.web.Servlet.BaseServlet.BaseServlet;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/LoginServlet")
public class LoginServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private static final String axios = "a:";
    private static final String redirect = "r:";
    private static final String forward = "f:";
    public String Login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String account = request.getParameter("loginusername");
        String password = request.getParameter("loginpassword");
        System.out.println("SqlSessionFactoryUtils.getSqlSessionFactory() = " + SqlSessionFactoryUtils.getSqlSessionFactory());
        User user = UserListService.selectByAP(account, password);
        if(user!=null){
            //验证·成功！发放token
            System.out.println("登陆成功！");
            String token = JwtUitls.createToken(account, password);
            Cookie cookie = new Cookie("token", token);
            Cookie cookie1 = new Cookie("account", user.getAccount());
            Cookie cookie2 = new Cookie("id", user.getId().toString());
            if(user.getAdmin()){
                cookie2 = new Cookie("adminid", user.getId().toString());
            }
            cookie.setMaxAge(60*60*24*30);
            cookie1.setMaxAge(60*60*24*30);
            cookie2.setMaxAge(60*60*24*30);
            response.addCookie(cookie);
            response.addCookie(cookie1);
            response.addCookie(cookie2);
            System.out.println(user.getAdmin());
            if(user.getAdmin()){
                return redirect +"/backstage.html";
            }else{
                return redirect+"/index.html";
            }

        }else{
            //登录失败
            System.out.println("登录失败");
            request.setAttribute("msg", "账号或者密码错误");
            return forward+"/Login.html";
        }
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String exitLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Cookie cookie = new Cookie("id",null);
        Cookie cookie1 = new Cookie("adminid",null);
        Cookie cookie2 = new Cookie("token",null);
        cookie.setMaxAge(0);
        cookie1.setMaxAge(0);
        cookie2.setMaxAge(0);
        cookie.setPath(request.getContextPath());
        cookie1.setPath(request.getContextPath());
        cookie2.setPath(request.getContextPath());
        response.addCookie(cookie);
        response.addCookie(cookie1);
        response.addCookie(cookie2);
        mp.put("msg", "退出登录成功");
        return axios + new JSONObject(mp).toString();
    }

    public String Method03(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();

        return axios + new JSONObject(mp).toString();
    }

}
