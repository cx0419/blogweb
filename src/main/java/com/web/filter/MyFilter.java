package com.web.filter;

import com.pojo.User;
import com.util.JwtUitls;
import com.util.StringUtil;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebFilter("/*")
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String[] urls = {"/Login.html","/image/","/css/","/iconfont/","/js/","/LoginServlet",
                "/registerServlet","/sRVCServlet","/RegisterServlet","/AlterPassword.html","/uploadfile","/MyBlogWebFile","/person.html","/index.html"};
        Map<String,String> map = new HashMap<>();
        String url =  ((HttpServletRequest)servletRequest).getRequestURI();
        if(url != null){
            //放行一些登录前的资源
            for(String u:urls){
                if(url.contains(u)){
                    System.out.println(StringUtil.getTime() +" 登录前允许访问的:"+url);
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
            //登录请求直接放行
            if("/BlogWeb/Login.html".equals(url)){
                filterChain.doFilter(servletRequest,servletResponse);
                return;
            }else{
                //其他请求验证token
                String token = null;
                //((HttpServletRequest)servletRequest).getHeader("token");
                Cookie[] cookies = ((HttpServletRequest) servletRequest).getCookies();
                if(cookies==null){
                    map.put("errorMsg","未携带token信息");
                    ((HttpServletResponse)(servletResponse)).sendRedirect("http://localhost:8080/BlogWeb/Login.html");
                    return;
                }
                for(Cookie cookie: cookies){
                    if("admin".equals(cookie.getName())){
                        if("2".equals(cookie.getValue())){
                            filterChain.doFilter(servletRequest,servletResponse);
                        }
                    }
                    if("token".equals(cookie.getName())){
                        token = cookie.getValue();
                    }
                }
                if(!StringUtil.isBlank(token)){
                    //token验证结果
                    User user  = JwtUitls.verify(token);
                    if(user == null){
                        //验证失败
                        map.put("errorMsg","token已过期或者用户信息验证失败");
                        ((HttpServletResponse)(servletResponse)).sendRedirect("http://localhost:8080/BlogWeb/Login.html");
                    }else{
                        //验证成功，放行
                        System.out.println("登录后允许访问的:"+url);
                        req.setAttribute("user", new JSONObject(user).toString());
                        filterChain.doFilter(servletRequest,servletResponse);
                        return;
                    }
                }else{
                    //token为空的返回
                    map.put("errorMsg","未携带token信息");
                    ((HttpServletResponse)(servletResponse)).sendRedirect("http://localhost:8080/BlogWeb/Login.html");
                }
            }
            JSONObject jsonObject = new JSONObject(map);
            servletResponse.setContentType("application/json");
            //设置响应的编码
            servletResponse.setCharacterEncoding("utf-8");
            //响应
            PrintWriter pw=servletResponse.getWriter();
            pw.write(jsonObject.toString());
            pw.flush();
            pw.close();
        }
    }

    @Override
    public void destroy() {

    }


    public static void main(String[] args) {
        System.out.println("1");
    }
}
