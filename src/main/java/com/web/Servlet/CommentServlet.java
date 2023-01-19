package com.web.Servlet;

import com.dto.Page;
import com.dto.SonComment;
import com.dto.TopComment;
import com.pojo.Comment;
import com.pojo.User;
import com.service.CommentService;
import com.service.UserListService;
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
import java.util.Objects;

import static com.util.WebUtil.getCookie;
import static com.util.WebUtil.getParameter;

@WebServlet("/CommentServlet")
public class CommentServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private static final String axios = "a:";

    /**
     * 根据博客id获取到指定页的评论
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String selectCommentByBlogId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Long id = new Long(getParameter(request, "id"));
        int pagenum = Integer.parseInt(getParameter(request, "pagenum"));
        //获取这篇文章的所有顶级评论
        List<Comment> sqltopComments = CommentService.selectCommentsByBlogId(10,pagenum,id);
        System.out.println("sqltopComments:"+sqltopComments.size());
        //new 一个待放的dto
        List<TopComment> topComments = new ArrayList<>(0);
        for(Comment comment:sqltopComments){
            System.out.println("进入循环");
            Long tcid = comment.getId();
            TopComment topComment = new TopComment();
            topComment.setComment(comment);
            Long userId = comment.getUserId();
            User user = UserListService.selectById(userId);
            topComment.setUser(user);
            //根据顶级评论的id获取这个底下所有的评论
            List<Comment> sqlsonComment = CommentService.selectCommentsByTopCId(tcid);
            List<SonComment> sonComments = new ArrayList<>(0);
            for(Comment comment1:sqlsonComment){
                SonComment sonComment = new SonComment();
                sonComment.setComment(comment1);
                //获取到这个儿子评论的发布者id 然后获取到user对象
                User user1 = UserListService.selectById(comment1.getUserId());
                //获取到这个儿子评论回复的人的id 然后获取到user对象
                Long parentid = CommentService.selectCommentsById(sonComment.getComment().getParentCommentId()).getUserId();
                User user2 = UserListService.selectById(parentid);
                sonComment.setUser(user1);
                sonComment.setParentUser(user2);
                System.out.println(sonComment.getParentUser().getName());

                sonComments.add(sonComment);
            }
            //将这个儿子评论设置到顶级评论数组当中去
            topComment.setSonComments(sonComments);
            //最后将这个顶级评论加入dto
            topComments.add(topComment);
        }
        int pagetotal = CommentService.selectCommentsNumByBlogId(id);
        Page page = new Page(10,pagenum,pagetotal,topComments);
        mp.put("page",page);
        mp.put("msg", "查找评论成功");
        return axios + new JSONObject(mp).toString();
    }

    /**
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String ReplyComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();
        Comment comment = new Comment();

        String content = getParameter(request, "content");
        comment.setContent(content);
        Long parentCommentId = new Long(getParameter(request, "object"));
        comment.setParentCommentId(parentCommentId);
        Long blogId = new Long(getParameter(request, "blogid"));
        comment.setBlogId(blogId);
        Long topCommentId = new Long(getParameter(request, "topcommentid"));
        comment.setTopCommentId(topCommentId);
        comment.setCreateTime(StringUtil.getTime());
        comment.setIsdelete(false);
        comment.setUserId(new Long(Objects.requireNonNull(getCookie(request, "id"))));
        CommentService.insertComment(comment);
        mp.put("comment",comment);
        mp.put("msg", "评论成功");

        return axios + new JSONObject(mp).toString();
    }

    public String Method03(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> mp = new HashMap<>();

        return axios + new JSONObject(mp).toString();
    }

}
