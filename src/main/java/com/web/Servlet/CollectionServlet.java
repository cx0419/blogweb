package com.web.Servlet;

import com.dto.CollectionForBlog;
import com.dto.Page;
import com.pojo.Blog;
import com.pojo.BlogCollection;
import com.pojo.Collection;
import com.service.CollectionService;
import com.util.StringUtil;
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

import static com.util.WebUtil.getCookie;
import static com.util.WebUtil.getParameter;

@WebServlet("/CollectionServlet")
public class CollectionServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private static final String axios = "a:";

    /**
     * 获取博客页面的收藏列表,此处以添加为主 [用户自己]
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String selectCollectionsAndStateByblogId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long blogid = new Long(getParameter(request, "blogid"));
        Long userid = new Long(getCookie(request, "id"));
        //先获取这个人的所有收藏夹
        List<Collection> collections = CollectionService.getCollectionsByUserId(userid);
        //新建dto
        List<CollectionForBlog> collectionForBlogs = new ArrayList<>(0);
        for(Collection collection:collections){
            CollectionForBlog collectionForBlog = new CollectionForBlog();
            collectionForBlog.setCollection(collection);
            //获取这篇文章与这个收藏夹的关系
            BlogCollection blogCollection = CollectionService.selectBlogCollectionByCollectionIdAndBlogId(collection.getId(),blogid);
            Boolean state = blogCollection != null;
            collectionForBlog.setStatus(state);
            collectionForBlogs.add(collectionForBlog);
        }
        mp.put("collectionForBlogs",collectionForBlogs);
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 通过userid找出这个人所有的收藏夹 加载到管理界面左侧 [用户自己]
     */
    public String selectCollectionsAndStateByUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long userid = new Long(getCookie(request, "id"));
        //先获取这个人的所有收藏夹
        List<Collection> collections = CollectionService.getCollectionsByUserId(userid);
        mp.put("msg","获取所有收藏夹成功");
        mp.put("collections",collections);
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 添加一个收藏夹 [用户自己]
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String addCollection(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long userid = new Long(getCookie(request, "id"));
        String name = getParameter(request, "collectionname");
        String time = StringUtil.getTime();
        Collection collection = new Collection();
        collection.setCreateTime(time);
        collection.setName(name);
        collection.setUserId(userid);
        collection.setIsdelete(false);
        if(CollectionService.InsertCollection(collection)){
            mp.put("msg","增加收藏栏成功");
        }else{
            mp.put("msg","增加收藏栏失败");
        }
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 添加一个博客到收藏夹当中去  [所有人]
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String addBlogToCollection(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long userid = new Long(getCookie(request, "id"));
        Long blogId = new Long(getParameter(request, "blogid"));
        Long collectionId =new Long(getParameter(request, "collectionid"));
        CollectionService.InsertBlogTOCollection(blogId,collectionId);
        mp.put("msg","成功添加博客至该收藏夹");
        return axios + new JSONObject(mp).toString();
    }

    //将博客从目标收藏夹中删除
    public String deleteBlogFromCollection(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long blogId = new Long(getParameter(request, "blogid"));
        Long collectionId =new Long(getParameter(request, "collectionid"));
        CollectionService.InsertBlogTOCollection(blogId,collectionId);
        mp.put("msg","成功添加博客至该收藏夹");
        return axios + new JSONObject(mp).toString();
    }


    /**
     * 删除一个收藏夹
     */
    public String deleteCollection(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long blogId = new Long(getParameter(request, "blogid"));
        Long collectionId =new Long(getParameter(request, "collectionid"));
        CollectionService.updateBlogCollectionDeleteByCIdAndBId(blogId,collectionId,true);

        return axios + new JSONObject(mp).toString();
    }


    /**
     * 通过collectionid来获取博客 分页处理 [他人 自己] 不包含草稿
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String getBlogsByCollectionId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        int pagenum = Integer.parseInt(getParameter(request, "pagenum"));
        Long collectionId =new Long(getParameter(request, "collectionid"));
        List<Blog> blogs = CollectionService.selectBlogsByCollectionId(10,pagenum,collectionId);
        int total = CollectionService.selectBlogsNumByCollectionId(collectionId);
        Page page = new Page(10, pagenum,total , blogs);
        mp.put("page", page);
        mp.put("msg","获取博客成功");
        return axios + new JSONObject(mp).toString();
    }


    /**
     * 取消收藏博客
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String cancelCollection(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long blogId = new Long(getParameter(request, "blogid"));
        Long collectionId =new Long(getParameter(request, "collectionid"));
        CollectionService.cancelCollection(collectionId, blogId);
        mp.put("msg","取消收藏成功");
        return axios + new JSONObject(mp).toString();
    }


    /**
     * 更改收藏夹名字
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String alterCollectionName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long collectionId = new Long(getParameter(request, "collectionid"));
        Long userid = new Long(getCookie(request, "id"));
        String name  = getParameter(request, "name");
        //先根据名字和userid查重 一个user账号不允许出现同名收藏夹
        System.out.println("collectionId:"+collectionId);
        if(CollectionService.selectCollectionByUIdAndName(userid,name)==null){
            CollectionService.alterCollectionName(collectionId, name);
            mp.put("msg","修改成功");
        }else{
            mp.put("msg","修改成功失败");
        }

        return axios + new JSONObject(mp).toString();
    }


    /**
     * 删除收藏夹
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String DeleteOnesCollection(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long collectionid = new Long(getParameter(request, "collectionid"));
        //此方法将会删除收藏夹和对应的关系
        CollectionService.DeleteOnesCollection(collectionid);
        mp.put("msg", "删除成功");
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 获取一个文章的收藏数量
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String selectCollectionNumsByBlogId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long blogid = new Long(getParameter(request, "blogid"));
        int num  = CollectionService.selectCollectionNumByBlogId(blogid);
        mp.put("msg","博客:"+blogid+"的收藏数量获取成功");
        mp.put("num", num);
        return axios + new JSONObject(mp).toString();
    }

}
