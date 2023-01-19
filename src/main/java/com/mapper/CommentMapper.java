package com.mapper;

import com.pojo.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {

    //发表一条评论
    void addComment();

    List<Comment> selectCommentsByBlogId(@Param("offset")int offset,@Param("pagesize")int pagesize,@Param("id")Long id);
    int selectCommentsNumByBlogId(@Param("id")Long id);

    List<Comment> selectCommentsByTopCId(@Param("id")Long id);

    void insertComment(Comment comment);

    Comment selectCommentById(@Param("id")Long id);

    List<Long> selectAllBlogIdByUserLike(@Param("userid")Long userid);
}
