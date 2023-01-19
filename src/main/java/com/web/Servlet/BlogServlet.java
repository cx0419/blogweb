package com.web.Servlet;

import com.dto.ColumnAndNum;
import com.dto.Page;
import com.pojo.*;
import com.service.*;
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

import static com.util.WebUtil.*;

@WebServlet("/BlogServlet")
public class BlogServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private static final String axios = "a:";

    /**
     * 保存博客 , 更新博客 [用户自己]
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String saveBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Blog blog = getObjectParameter(request, "blog",Blog.class);
        List<String> tags = getObjectParameter(request, "tags", List.class);
        List<String> columns = getObjectParameter(request, "columns", List.class);
        //没有id参数说明是上传, 或者在前端判断时发现文章id为错误格式
        System.out.println("本次上传/更新的博客id为:"+blog.getId());
        if(blog.getId()==null){
            Long blogid = BlogListService.addBlog(blog);
            //添加标签
            TagService.addTags(blogid,tags);
            //添加专栏
            Long userId = new Long(getCookie(request, "id"));
            ColumnService.addCloumns(blogid,userId,columns);
            mp.put("msg", "添加博客成功！");
            mp.put("blogid",blogid);
        }else if(BlogListService.checkBlogWriter(blog.getId(),
                Long.valueOf(getCookie(request, "id")))){
            //此时为了保证这url不是用户自己输入 确保修改的不是别人的文章 , 所以去数据库在判断一次作者
            //有id参数说明是修改编辑
//            先对博客信息进行修改
            BlogListService.updateBlog(blog);
            try {
                //标签如果与之前不同则进行判断删除 增加
                TagService.alterTags(blog.getId(),tags);
                //专栏如果与之前不同则进行判断删除 增加
                ColumnService.alterColumn(blog.getId(),columns);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mp.put("msg", "修改博客成功");
            mp.put("blogid",blog.getId());
        }else{
            //当id不合规范 id不存在 id不是他的时 这个博客不可被修改
            mp.put("msg", "您没有权限");
        }
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 查询一个用户id的所有博客 [用户自己一个人]
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String selectAllBlogIncludeCao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long id = getObjectParameter(request, "id",Long.class);
        String nian = request.getParameter("nian");
        String yue = getParameter(request, "yue");
        String column = getParameter(request, "column");
        String tag = getParameter(request, "tag");
        String draft = getParameter(request, "draft");
        String examine = getParameter(request, "examine");
        String key = getParameter(request, "key");
        String original = getParameter(request, "original");
        int pagenum = Integer.parseInt(getParameter(request, "pagenum"));
        if("nonian".equals(nian)){
            nian = null;
        }
        if("nonian".equals(yue)){
            yue = null;
        }
        if("nonian".equals(column)){
            column = null;
        }
        if("nonian".equals(tag)){
            tag = null;
        }
        if("nonian".equals(draft)){
            draft = null;
        }
        if("nonian".equals(examine)){
            examine = null;
        }
        if("nonian".equals(key)){
            key = null;
        }
        if("nonian".equals(original)){
            original = null;
        }
        System.out.println(nian+" "+yue+" "+column+" "+tag+" "+draft+" "+examine +" "+key +" "+original);
        List<Blog> blogs = BlogListService.seletAllBlogByIdIncludeCao(10,pagenum,id,nian,yue,column,tag,draft,examine,key,original);

        Page page = new Page(10, pagenum,BlogListService.seletAllBlogNumByIdIncludeCao(id,nian,yue,column,tag,draft,examine,key,original),blogs);
        mp.put("page",page);
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 查询一个用户的所有博客 [其他人]
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String selectAllBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long id = getObjectParameter(request, "userid",Long.class);
        int pagenum = Integer.valueOf(getParameter(request, "pagenum"));
        int pageSize = 10;
        List<Blog> blogs = BlogListService.seletAllBlogById(pageSize,pagenum,id);
        Page page = new Page(pageSize, pagenum,BlogListService.selectAllBlogNumByUId(id),blogs);
        mp.put("page",page);
        mp.put("msg", "查询成功");
        return axios + new JSONObject(mp).toString();
    }


    /**
     * 更新博客 [管理员][本人]
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String updateBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        mp.put("msg", "更新");
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 查询博客页面相关数据 [所有人]
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String selectBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long id = new Long(getParameter(request, "id"));
        System.out.println("id:"+id);
        Blog blog = BlogListService.selectBlogById(id);
        UserExtend userExtend = UserExtendService.selectUserExtendsById(blog.getUserId());
        User user = UserListService.selectById(blog.getUserId());
        List<Column> columns = ColumnService.selectColumnsByBlogId(blog.getId());
        List<ColumnAndNum> columnAndNums = new ArrayList<>(0);
        for(Column column:columns){
            ColumnAndNum columnAndNum = new ColumnAndNum();
            columnAndNum.setColumn(column);
            int num = ColumnService.selectBlogNumColumns(column.getId());
            columnAndNum.setNum(num);
            columnAndNums.add(columnAndNum);
        }
        List<Tag> tags = TagService.selectAllTagByBlogId(blog.getId());

        mp.put("blog", blog);
        mp.put("userExtend",userExtend);
        mp.put("user", user);
        mp.put("columnAndNums", columnAndNums);
        mp.put("msg", "查询博客成功！");
        mp.put("tags", tags);
        return axios + new JSONObject(mp).toString();
    }

    //通过关键字来查询所有博客(带分页) 查找博客 这些博客一定是已经上线的博客  [所有人]
    public String selectBlogByKey(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        int pagenum = new Integer(getParameter(request, "pagenum"));
        String key = getParameter(request, "key");
        int pageSize = 10;
        Page<Blog> page = new Page(10,pagenum,BlogListService.selectBlogsNumByKey(key),BlogListService.selectBlogsByKey(pageSize,pagenum,key));
        mp.put("page", page);
        mp.put("msg", "查询博客成功！");
        return axios + new JSONObject(mp).toString();
    }

    //找出来审核中的文章  [管理员]
    public String  selectNoExamineBlogs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        int pagenum = new Integer(getParameter(request, "pagenum"));
        int pageSize = 10;
        List<Blog> blogs = BlogListService.selectNoExamineBlogs(pageSize,pagenum);
        Page<Blog> page = new Page(10,pagenum,BlogListService.selectNoExamineBlogsnum(),blogs);
        mp.put("msg", "查找未审核文章成功");
        mp.put("page",page);
        return axios + new JSONObject(mp).toString();
    }


   //审核同意 将字段修改为审核通过  [管理员]
    public String PassBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long id = new Long(getParameter(request, "id"));
        BlogListService.passBlog(id);
        mp.put("msg", "已审核通过文章:"+id);
        return axios + new JSONObject(mp).toString();
    }

    //审核不同意 将字段修改为审核不通过  [管理员]
    public String nopassBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long id = new Long(getParameter(request, "id"));
        BlogListService.nopassBlog(id);
        mp.put("msg", "未审核通过文章:"+id);
        return axios + new JSONObject(mp).toString();
    }


    /**
     * 删除文章 [管理员,用户自己]
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String deleteBlogById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long id = new Long(getParameter(request, "id"));
        BlogListService.deleteBlogById(id);
        mp.put("msg", "删除成功");
        return axios + new JSONObject(mp).toString();
    }




}
