package com.service;

import com.mapper.CommentMapper;
import com.pojo.Comment;
import com.service.BaseService.BaseService;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CommentService implements BaseService {

    /**
     * 根据博客id获取所有顶级评论
     */
    public static List<Comment> selectCommentsByBlogId(int pagesize,int pagenum,Long id){
        SqlSession sqlSession = factory.openSession();
        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        List<Comment> comments = commentMapper.selectCommentsByBlogId(pagesize*(pagenum-1),pagesize,id);
        sqlSession.close();
        return comments;
    }

    public static int selectCommentsNumByBlogId(Long id){
        SqlSession sqlSession = factory.openSession();
        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        int comments = commentMapper.selectCommentsNumByBlogId(id);

        sqlSession.close();
        return comments;
    }


    /**
     * 根据顶级评论获取到评论
     */
    public static List<Comment> selectCommentsByTopCId(Long id){
        SqlSession sqlSession = factory.openSession();
        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        List<Comment> comments = commentMapper.selectCommentsByTopCId(id);
        sqlSession.close();
        return comments;
    }

    /**
     * 插入一条评论
     */
    public static void insertComment(Comment comment){
        SqlSession sqlSession = factory.openSession();
        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        commentMapper.insertComment(comment);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 根据顶级评论获取到评论
     */
    public static Comment selectCommentsById(Long id){
        SqlSession sqlSession = factory.openSession();
        CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
        Comment comment = commentMapper.selectCommentById(id);
        sqlSession.close();
        return comment;
    }


}
