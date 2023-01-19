package com.web.Servlet;

import com.dto.Page;
import com.dto.RecommendForBlog;
import com.pojo.Blog;
import com.service.BlogListService;
import com.service.RecommendService;
import com.web.Servlet.BaseServlet.BaseServlet;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.util.WebUtil.getParameter;

@WebServlet("/RecommendServlet")
public class RecommendServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private static final String axios = "a:";

    /**
     * 获取推荐博客的列表 并判断每一个博客是否已经被管理员推荐了
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String getRecommendBlogs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        int pagenum = Integer.valueOf(getParameter(request, "pagenum"));
        List<Blog> blogs = BlogListService.selectBlogsByKey(10, pagenum, "");
        List<RecommendForBlog>  recommendForBlogs = new ArrayList<>(0);
        for(Blog blog:blogs){
            RecommendForBlog recommendForBlog = new RecommendForBlog();
            if(RecommendService.checkBlogIsInCommentList(blog.getId())){
                recommendForBlog.setRecommend(true);
            }else{
                recommendForBlog.setRecommend(false);
            }
            recommendForBlog.setBlog(blog);
            recommendForBlogs.add(recommendForBlog);
        }
        Page page = new Page(10, pagenum, BlogListService.selectBlogsNumByKey(""), recommendForBlogs);
        mp.put("page",page);
        mp.put("msg", "获取推荐列表成功");
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 添加一个博客到推荐列表
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String addBlogToRecommendList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long blogid = Long.valueOf(getParameter(request, "blogid"));
        RecommendService.manageAddBlogId(blogid);
        mp.put("msg", "添加推荐成功");
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 在推荐列表当中删除一个 博客id
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String deleteBlogFromRecommendList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long blogid = Long.valueOf(getParameter(request, "blogid"));
        RecommendService.manageDeleteBlogId(blogid);
        mp.put("msg", "删除推荐成功");
        return axios + new JSONObject(mp).toString();
    }

}
