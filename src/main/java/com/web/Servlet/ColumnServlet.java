package com.web.Servlet;

import com.dto.BlogsOfColumns;
import com.dto.ColumnAndNum;
import com.dto.Page;
import com.pojo.Blog;
import com.pojo.BlogColumn;
import com.pojo.Column;
import com.pojo.User;
import com.service.BlogListService;
import com.service.ColumnService;
import com.service.UserListService;
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

@WebServlet("/ColumnServlet")
public class ColumnServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private static final String axios = "a:";

    /**
     * 根据key值模糊查找所有相关的专栏
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String selectColumnsByKey(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        int pagenum = new Integer(getParameter(request, "pagenum"));
        String key = getParameter(request, "key");
        int pageSize = 10;
        List<Column> columns = ColumnService.selectColumnsByKey(pageSize,pagenum,key);
        //新建一个
        List<BlogsOfColumns> blogsOfColumns = new ArrayList<>(0);
        for(int i = 0 ; i < columns.size() ; i++){
            BlogsOfColumns blogsOfColumn = new BlogsOfColumns();
            //为dto设置专栏
            blogsOfColumn.setColumn(columns.get(i));
            //获取这个专栏的id 然后根据专栏id获取到这个专栏的所有博客id，放入到诸葛dto当中去
            List<BlogColumn> blogColumns = ColumnService.selectBlogColumns(columns.get(i).getId());
            blogsOfColumn.setBlogColumns(blogColumns);
            List<Blog> blogs = BlogListService.selectBlogsByIds(blogColumns);
            blogsOfColumn.setBlogs(blogs);
            //获取当前
            blogsOfColumns.add(blogsOfColumn);
        }
        Page<Blog> page = new Page(10,pagenum,ColumnService.selectColumnsByKey(key),blogsOfColumns);
        mp.put("page", page);
        mp.put("msg", "查询专栏成功！");
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 根据专栏id找博客 分页处理
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String selectBlogsByColumnId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        int pagenum = new Integer(getParameter(request, "pagenum"));
        Long columnid = new Long(getParameter(request, "columnid"));
        String key = getParameter(request, "key");
        int pageSize = 10;
        //先获取专栏
        Column column = ColumnService.selectColumnsByCId(columnid);
        mp.put("column",column);
        Long userId = column.getUserId();
        //再获取博客列表
        List<Blog> blogs = ColumnService.selectBlogsByCId(pageSize,pagenum,columnid);
        mp.put("blogs",blogs);
        //获取用户
        User user = UserListService.selectById(userId);
        mp.put("user",user);
        //封装分页对象
        Page<Blog> page = new Page(10,pagenum,blogs.size(),null);
        mp.put("page", page);
        mp.put("msg", "查询博客列表成功");
        return axios + new JSONObject(mp).toString();
    }



    /**
     *通过userid获取到这个user的所有分页专栏
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String getColumnsByUId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        //需要这一页的资源
        int pagenum = new Integer(getParameter(request, "pagenum"));
        //userid
        Long userid = Long.valueOf(getCookie(request, "id"));
        int pageSize = 10;
        List<Column> columns = ColumnService.selectColumnsByUId(pageSize,pagenum,userid);
        Page<Blog> page = new Page(10,pagenum,ColumnService.selectColumnsNumByUId(userid),columns);
        mp.put("page", page);
        mp.put("msg", "查询专栏成功！");
        return axios + new JSONObject(mp).toString();
    }

    /**
     * 添加专栏
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String addColumn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        String name = getParameter(request, "name");
        String introduction = getParameter(request, "introduction");
        String url = getParameter(request,"url");
        String idstr = getCookie(request, "id");
        Boolean show = Boolean.valueOf(getParameter(request, "show"));
        assert idstr != null;
        Long id = new Long(idstr);
        if(ColumnService.selectColumnNumByUserId(id)<50){
            //执行添加专栏
            ColumnService.addColumn(name,introduction,url,show,id);
            mp.put("msg", "专栏添加成功");
        }else{
            mp.put("msg", "专栏添加失败");
        }
        return axios + new JSONObject(mp).toString();
    }

    //删除专栏
    public String deleteColumnById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        String idstr = getParameter(request, "columnid");
        Long id = new Long(idstr);
        ColumnService.deleteAboutAColumn(id);
        mp.put("msg","删除成功");
        return axios + new JSONObject(mp).toString();
    }

    //通过columnid 获取专栏
    public String selectColumnById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long columnid = new Long(getParameter(request, "columnid"));
        Column column = ColumnService.selectColumnById(columnid);
        mp.put("column", column);
        return axios + new JSONObject(mp).toString();
    }




    //更新专栏
    public String updateColumn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long id = new Long(getParameter(request, "id"));
        String name = getParameter(request, "name");
        String introduction = getParameter(request, "introduction");;
        Long userId = new Long((getCookie(request, "id")));
        String picture = getParameter(request, "url");
        ColumnService.updateColumn(id,name,introduction,userId,picture);
        return axios + new JSONObject(mp).toString();
    }

    //更新show字段
    public String alterColumnShow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long columnid = new Long(getParameter(request, "columnid"));
        Boolean show = getObjectParameter(request, "show",Boolean.class);
        ColumnService.alterColumnShow(columnid,show);
        mp.put("msg", "修改show成功!");
        return axios + new JSONObject(mp).toString();
    }


    //拉取专栏下拉框所需要的信息
    public String getDropdownColumn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long userid = new Long(getCookie(request, "id"));
        List<Column> columns = ColumnService.selectColumnByUserId(userid);
        mp.put("msg", "查询专栏下拉菜单成功!");
        mp.put("columns", columns);
        return axios + new JSONObject(mp).toString();
    }



    //根据userid 找出这个人所有的专栏 一次全部拉取 放在个人主页当中
    public String getAllColumnByUserId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        //这里userid 需要在前台获取
        Long userid = Long.valueOf(getParameter(request, "userid"));
        List<Column> columns = ColumnService.getAllColumnByUserId(userid);
        List<ColumnAndNum> columnAndNums = new ArrayList<>(0);
        for(Column column:columns){
            ColumnAndNum columnAndNum = new ColumnAndNum();
            columnAndNum.setColumn(column);
            int num = ColumnService.selectBlogNumColumns(column.getId());
            columnAndNum.setNum(num);
            columnAndNums.add(columnAndNum);
        }
        mp.put("msg", "查询专栏下拉菜单成功!");
        mp.put("columnAndNums", columnAndNums);
        return axios + new JSONObject(mp).toString();
    }









}
