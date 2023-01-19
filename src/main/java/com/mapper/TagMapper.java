package com.mapper;

import com.pojo.Blog;
import com.pojo.BlogTag;
import com.pojo.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapper {
    Long insertTag(Tag tag);
    void updateTag(Tag tag);
    Tag selectTagByName(@Param("name")String name);

    BlogTag selectBlogTagByBT(@Param("blogId") Long blogid,@Param("tagId") Long tagid);
    void inertBlogTag(BlogTag blogTag);
    void deleteBlogTagByBlogId(BlogTag blogTag);
    List<Tag> selectAllTagByBlogId(@Param("id") Long id);

    void deleteTagById(@Param("id")Long id);

    List<Blog> selectAllBlogsByTagName(@Param("offset")int offset,@Param("pagesize")int pagesize,@Param("tagname")String tagname,@Param("userid")Long userid);
    int selectAllBlogsNumByTagName(@Param("tagname")String tagname,@Param("userid")Long userid);

    List<Tag> selectTagsByUId(@Param("userid")Long userid);
}
