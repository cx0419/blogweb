package com.web.Servlet;

import com.dto.Page;
import com.dto.UserForFocus;
import com.pojo.Attention;
import com.pojo.Blog;
import com.pojo.User;
import com.service.AttentionService;
import com.service.UserListService;
import com.web.Servlet.BaseServlet.BaseServlet;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.util.WebUtil.*;

@WebServlet("/AttentionServlet")
public class AttentionServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private static final String axios = "a:";

    public String AttentionIsNotEmpty(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Attention attention = getObjectParameter(request, "attention",Attention.class);
        if(AttentionService.SelectAttentionIsempty(attention)){
            mp.put("ans", false);
        }else{
            mp.put("ans", true);
        }
        return axios + new JSONObject(mp).toString();
    }

    public String getFans(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long id = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie: cookies){
            if("id".equals(cookie.getName())){
                id = new Long(cookie.getValue());
            }
        }

        List<User> users = AttentionService.selectAllFansByBloggerId(id);
        List<UserForFocus> userForFocus = new ArrayList<>(0);
        for (User user:users) {
            UserForFocus focusEachOther = new UserForFocus();
            focusEachOther.setUser(user);
            //交换身份，查看是否互粉了
            Attention attention = new Attention();
            attention.setBloggerId(user.getId());
            attention.setFansId(id);
            if(AttentionService.SelectAttentionIsempty(attention)){
                focusEachOther.setStatus(false);
            }else{
                focusEachOther.setStatus(true);
            }
            userForFocus.add(focusEachOther);
        }
        mp.put("users", userForFocus);
        return axios + new JSONObject(mp).toString();
    }

    public String getFocus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long id = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie: cookies){
            if("id".equals(cookie.getName())){
                id = new Long(cookie.getValue());
            }
        }
        List<User> users = AttentionService.selectAllFocusByBloggerId(id);
        mp.put("users", users);
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 取消关注某人
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String cancelAttention(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Attention attention = getObjectParameter(request, "attention",Attention.class);
        attention.setDelete(true);
        attention.setCreateTime(new Timestamp(new Date().getTime()).toString());
        AttentionService.addAttention(attention);
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 关注某人
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String AttentionSomeone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        Attention attention = getObjectParameter(request, "attention",Attention.class);
        attention.setDelete(false);
        attention.setCreateTime(new Timestamp(new Date().getTime()).toString());
        AttentionService.addAttention(attention);
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 根据关键字查询所有人
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String getUsersByKey(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long id = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie: cookies){
            if("id".equals(cookie.getName())){
                id = new Long(cookie.getValue());
            }
        }
        int pagenum = new Integer(getParameter(request, "pagenum"));
        String key = getParameter(request, "key");
        int pagesize = 10;

        List<User> users = UserListService.selectUsersByKey(pagesize,pagenum,key);
        List<UserForFocus> userForFocus = new ArrayList<>(0);
        for (User user:users) {
            UserForFocus focusEachOther = new UserForFocus();
            focusEachOther.setUser(user);
            //查看他是否被你关注
            Attention attention = new Attention();
            attention.setBloggerId(user.getId());
            attention.setFansId(id);
            if(AttentionService.SelectAttentionIsempty(attention)){
                focusEachOther.setStatus(false);
            }else{
                focusEachOther.setStatus(true);
            }
            userForFocus.add(focusEachOther);
        }
        Page<Blog> page = new Page(10,pagenum, UserListService.selectUsersNumByKey(key),userForFocus);
        mp.put("page", page);
        return axios + new JSONObject(mp).toString();
    }


    /**
     * 通过userid获取粉丝数量
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String getFansNumByUserId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long userid = Long.valueOf(getCookie(request, "userid"));
        int num = AttentionService.getFansNumByUserId(userid);
        mp.put("num", num);
        mp.put("msg", "用户"+userid+"的粉丝数量获取成功");
        return axios + new JSONObject(mp).toString();
    }


    /**
     * 通过userid获取粉丝数量
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String getFocusNumByUserId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long userid = Long.valueOf(getCookie(request, "userid"));
        int num = AttentionService.getFocusNumByUserId(userid);
        mp.put("num", num);
        mp.put("msg", "用户"+userid+"的粉丝数量获取成功");
        return axios + new JSONObject(mp).toString();
    }

}
