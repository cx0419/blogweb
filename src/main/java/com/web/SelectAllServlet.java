package com.web;

import com.service.UserListService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

//@WebServlet("/selectAllServlet")
public class SelectAllServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserListService userListService = new UserListService();
        //List<User> list = userListService.selectAll();
        //存

        //Cookie cookie = new Cookie("name", "xxiaoming");
        //存中文
        String value = "小明asdasd";
        value = URLEncoder.encode(value,"UTF-8");
        Cookie cookie = new Cookie("name", value);


        response.addCookie(cookie);

        //加载
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie1 = cookies[i];
            String value1 = cookie1.getValue();
            URLDecoder.decode(value1, "UTF-8");
            System.out.println(cookie1.getName()+" "+cookie1.getValue());
        }
        //存入request域中
       // request.setAttribute("list", list);
        request.getRequestDispatcher("/users.jsp").forward(request, response);
        System.out.println(request);




    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }


}
