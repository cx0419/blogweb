package com.mapper;

import com.pojo.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface BlogMapper {

    List<Long> selectAllBlogToReCommend();
    /**
     * 添加博客
     * @param blog
     */
    Long addBlog(Blog blog);

    /**
     * 根据id找到一个user的所有博客 带page 的分页处理 不带的不分页处理
     * @return
     */
    List<Blog> selectBlogByUserIdpage(@Param("offset")int offset,@Param("pagesize")int pagesize,@Param("id") Long id);
    List<Blog> selectBlogByUserId(@Param("id") Long id);
    int selectAllBlogNumByUId(@Param("userid")Long userid);

    /**
     * 这 2 个方法包含了可以查到草稿 , 必须携带userid才能执行查询
     */
    List<Blog> seletAllBlogByIdIncludeCao(@Param("offset")int offset, @Param("pagesize")int pagesize, @Param("id") Long id, @Param("nian") String nian, @Param("yue") String yue, @Param("column") String column,@Param("tag")String tag ,@Param("draft")String draft, @Param("examine")String examine,@Param("key")String key,@Param("original")String original);
    int seletAllBlogNumByIdIncludeCao(@Param("id") Long id ,@Param("nian") String nian, @Param("yue") String yue, @Param("column") String column,@Param("tag")String tag, @Param("draft")String draft, @Param("examine")String examine,@Param("key")String key,@Param("original")String original);
    /**
     * 更新博客
     * @param blog
     */
    void updateBlog(Blog blog);

    /**
     * 根据id删除博客
     */
    void deleteBlogById(@Param("id") Long id);

    //根据id查找博客
    Blog selectBlogById(@Param("id") Long id);

    //根据关键词查找博客
    List<Blog> selectBlogsByKey(@Param("offset")int offset,@Param("pagesize")int pagesize,@Param("key") String key);
    int selectBlogsNumByKey(@Param("key") String key);

    //根据博客id 查找bloglist(只含id 和 标题)
    Blog selectBlogTitleById(@Param("id") Long id);



    List<Blog> selectBlogsByCId(@Param("offset")int offset,@Param("pagesize")int pagesize,@Param("columnid")Long columnid);


    List<Blog> selectExamineBlogs(@Param("offset")int offset,@Param("pagesize")int pagesize);
    int selectExamineBlogsnum();
    //更新审核 状态
    void updateBlogPass(@Param("st") String st,@Param("id") Long id);

    Blog selectBlogByBIdAndUId(@Param("blogid")Long blogid,@Param("userid")Long userid);

    List<Blog> selectBlogsByData(@Param("nian")String nian,@Param("yue")String yue,@Param("column")String column);
}
