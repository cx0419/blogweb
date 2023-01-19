package com.web.Servlet;

import com.pojo.User;
import com.pojo.UserExtend;
import com.service.UserExtendService;
import com.service.UserListService;
import com.web.Servlet.BaseServlet.BaseServlet;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import static com.util.WebUtil.getCookie;
import static com.util.WebUtil.getParameter;

@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private static final String axios = "a:";

    public String getUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        String account = getParameter(request,"account");
        User user = UserListService.selectByAccount(account);
        UserExtend userExtend = UserExtendService.selectUserExtendsById(new Long(Objects.requireNonNull(getCookie(request, "id"))));
        mp.put("user", user);
        mp.put("userExtend",userExtend);
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 通过id获取user对象
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String getUserById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long id = null;
        if(!getParameter(request, "id").equals("")){
            id = new Long(getParameter(request, "id"));
        }else{
            mp.put("msg", "拉去失败！");
        }
        User user = UserListService.selectById(id);
        mp.put("user",user);
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 删除user对象
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        String idstr = getParameter(request, "id");
        Long id = new Long(idstr);
        UserListService.updateUserIsDelete(true, id);
        mp.put("msg", "删除成功!");
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 获取一个用户的所有博客的浏览量
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String getViewNumByUId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long userid = new Long(getCookie(request, "id"));
        int num = UserListService.getViewsByUserId(userid);
        mp.put("num",num);
        mp.put("msg", "获取浏览数成功");
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 获取一个用户的所有博客的博客点赞量
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String getLikeNumByUId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long userid = new Long(getCookie(request, "id"));
        int num = UserListService.getLikeNumByUserId(userid);
        mp.put("num",num);
        mp.put("msg", "获取点赞数成功");
        return axios + new JSONObject(mp).toString();
    }




}
