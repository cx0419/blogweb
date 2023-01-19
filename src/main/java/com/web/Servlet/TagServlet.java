package com.web.Servlet;

import com.dto.Page;
import com.pojo.Blog;
import com.pojo.Tag;
import com.service.TagService;
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

/**
 * 关于标签的servlet
 */
@WebServlet("/TagServlet")
public class TagServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private static final String axios = "a:";
    /**
     * 获取含有一个标签的所有文章 分页处理
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public String selectAllBlogsByTagName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        String tagname = getParameter(request, "tagname");
        int pagenum = new Integer(getParameter(request, "pagenum"));
        String useridstr = getParameter(request, "userid");
        Long userid = null;
        if(useridstr!=null){
            userid = new Long(useridstr);
        }
        //此id需要从前端获取,当前端不发送id时,说明查询的是所有文章当中去查,当id存在时 说明查询的是自己的文章
        List<Blog> blogs = TagService.selectAllBlogsByTagName(10,pagenum,tagname,userid);
        Page page = new Page(10, pagenum,TagService.selectAllBlogsNumByTagName(tagname,userid) , blogs);
        System.out.println("后台收到的tagname:"+tagname);
        mp.put("page",page);
        mp.put("msg", "通过标签获取文章成功");
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 拉取一个人的所有标签
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String selectTagsByUId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long userid = new Long(getCookie(request, "id"));
        List<Tag> tags = TagService.selectTagsByUId(userid);
        mp.put("msg", "拉取此人所有使用过的标签成功");
        mp.put("tags", tags);
        return axios + new JSONObject(mp).toString();
    }
}
