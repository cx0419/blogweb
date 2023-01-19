package com.web.Servlet;

import com.pojo.Blog;
import com.service.LikeService;
import com.service.RecommendService;
import com.web.Servlet.BaseServlet.BaseServlet;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.util.WebUtil.getCookie;
import static com.util.WebUtil.getParameter;

@WebServlet("/LikeServlet")
public class LikeServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private static final String axios = "a:";

    /**
     * 点赞
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String likeBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("点赞");
        HashMap<String, Object> mp = new HashMap<>();
        Long userid = new Long(getCookie(request, "id"));
        Long blogid = new Long(getParameter(request, "blogid"));
        LikeService.AddLike(userid,blogid);
        mp.put("msg", "点赞成功");
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 取消点赞
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String cancelLikeBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("取消点赞");
        HashMap<String, Object> mp = new HashMap<>();
        Long userid = new Long(getCookie(request, "id"));
        Long blogid = new Long(getParameter(request, "blogid"));
        LikeService.CancelAddLike(userid,blogid);
        mp.put("msg", "取消点赞成功");
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 获取一篇文章的点赞数
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String getLikeNum(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("获取点赞");
        HashMap<String, Object> mp = new HashMap<>();
        Long blogid = new Long(getParameter(request, "blogid"));
        int num = LikeService.CheckLikeNumByBlogId(blogid);
        mp.put("likenum", num);
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 检查用户是否被博客点赞
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String CheckUserHaveLikeForBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("获取点赞");
        HashMap<String, Object> mp = new HashMap<>();
        Long blogid = new Long(getParameter(request, "blogid"));
        Long userid = new Long(getCookie(request, "id"));
        System.out.println("状态:"+LikeService.CheckUserHaveLikeForBlog(userid, blogid));
        mp.put("likest", LikeService.CheckUserHaveLikeForBlog(userid,blogid));
        return axios + new JSONObject(mp).toString();
    }


    /**
     * 通过推荐获取博客
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String getBlogsByCommend(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        System.out.println("获取推荐");
        if(getCookie(request, "id")!=null){
            Long userid = Long.valueOf(getCookie(request, "id"));
            List<Blog> blogList = RecommendService.getRecommendList(userid,true);
            mp.put("msg", "获取推荐博客成功");
            mp.put("blogs", blogList);
        }else{
            mp.put("msg", "获取推荐博客之前先登录");
        }
        return axios + new JSONObject(mp).toString();
    }
}
