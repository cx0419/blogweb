package com.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/addServlet")
public class addServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受表单提交的数据
        String username = request.getParameter("loginusername");
        String password = request.getParameter("loginpassword");
//        //封装为一个user对象
//        User user = new User();
//        user.setAccount(username);
//        user.setPassWord(password);
//
//        //调用Service 完成添加操作
//        UserListService userListService = new UserListService();
//        userListService.add(user);
//
//        //转发
//        request.getRequestDispatcher("/selectAllServlet").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
