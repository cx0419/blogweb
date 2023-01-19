package com.web.Servlet;

import com.web.Servlet.BaseServlet.BaseServlet;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/ToolsServlet")
public class ToolsServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private static final String axios = "a:";
    private static final String re = "r:";
    public String gotoURL(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();

        return  re+ new JSONObject(mp).toString();
    }

    public String Method02(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();

        return axios + new JSONObject(mp).toString();
    }

    public String Method03(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();

        return axios + new JSONObject(mp).toString();
    }

}
