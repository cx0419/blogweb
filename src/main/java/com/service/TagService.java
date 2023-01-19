package com.service;

import com.mapper.TagMapper;
import com.pojo.Blog;
import com.pojo.BlogTag;
import com.pojo.Tag;
import com.service.BaseService.BaseService;
import com.util.StringUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class TagService implements BaseService {

    /**
     * 根据名字查询标签
     * @param name
     * @return
     */
    public static Tag selectTagByName(String name){
        SqlSession sqlSession = factory.openSession();
        TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
         Tag tag = tagMapper.selectTagByName(name);
        sqlSession.close();
        return tag;
    }

    public static BlogTag selectBlogTagByBT(Long blogId,Long tagId){
        SqlSession sqlSession = factory.openSession();
        TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
        BlogTag blogTag = tagMapper.selectBlogTagByBT(blogId,tagId);
        sqlSession.close();
        return blogTag;
    }
    /**
     * 将一个标签插入
     * @param name
     */
    public static Long addTag(String name){
        Tag tag = new Tag();
        Tag tag1 = selectTagByName(name);
        //先判断标签是否已经存在了
        if(tag1==null){
            SqlSession sqlSession = factory.openSession();
            TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
            tag.setName(name);
            tagMapper.insertTag(tag);
            sqlSession.commit();
            sqlSession.close();
        }
        return tag.getId()!=null?tag.getId():tag1.getId();
    }

    /**
     *将一个中间关系插入
     * @param blogId
     * @param tagId
     */
    public static void addBlogTag(Long blogId, Long tagId){
        Tag tag = new Tag();
        if(selectBlogTagByBT(blogId,tagId)==null){
            SqlSession sqlSession = factory.openSession();
            TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
            BlogTag blogTag = new BlogTag();
            blogTag.setBlogId(blogId);
            blogTag.setTagId(tagId);
            tagMapper.inertBlogTag(blogTag);
            sqlSession.commit();
            sqlSession.close();
        }
    }

    /**
     * 添加多个标签
     * @param tags
     */
    public static void addTags(Long blogid,List<String> tags){
        for(String tag:tags) {
            Long tagId = addTag(tag);
            addBlogTag(blogid,tagId);
        }

    }

    /**
     *根据博客id找出所有标签
     * @param id
     * @return
     */
    public static List<Tag> selectAllTagByBlogId(Long id){
        SqlSession sqlSession = factory.openSession();
        TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
        List<Tag> tags = tagMapper.selectAllTagByBlogId(id);
        sqlSession.close();
        return tags;
    }


    /**
     * 根据标签id删除标签
     * @param id
     * @return
     */
    public static void deleteTagById(Long id){
        SqlSession sqlSession = factory.openSession();
        TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
        tagMapper.deleteTagById(id);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 通过id 修改tags的关系 和 tags
     * @param blogid
     * @param tagNames
     * @throws Exception
     */
    public static void alterTags(Long blogid,List<String> tagNames) throws Exception{
        //先找到数据库中没有的进行添加
        for (String temp:tagNames) {
            if(TagService.selectTagByName(temp)==null){
                TagService.addTag(temp);
            }
        }
        //找到数据库中多余的进行删除
        List<Tag> tags =  TagService.selectAllTagByBlogId(blogid);
        for (Tag tag: tags) {
            if(!StringUtil.findString(tagNames,tag.getName())){
                //如果在数据库拿到的tag在本次的tags里面没有找到 那么就可以直接删除
                deleteTagById(tag.getId());
            }
        }

    }

    /**
     * 根据标签找到所有含有这个标签的博客
     * @param tagname
     * @return
     */
    public static List<Blog> selectAllBlogsByTagName(int pagesize,int pagenum,String tagname,Long userid){
        SqlSession sqlSession = factory.openSession();
        TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
        List<Blog> blogs = tagMapper.selectAllBlogsByTagName(pagesize*(pagenum-1),pagesize,tagname,userid);
        sqlSession.commit();
        sqlSession.close();
        return blogs;
    }

    /**
     * 根据标签找到所有含有这个标签的博客
     * @param tagname
     * @return
     */
    public static int selectAllBlogsNumByTagName(String tagname,Long userid){
        SqlSession sqlSession = factory.openSession();
        TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
        int num = tagMapper.selectAllBlogsNumByTagName(tagname,userid);
        sqlSession.commit();
        sqlSession.close();
        return num;
    }

    /**
     * 通过UId 去寻找这个人所有博客使用过的标签
     * @param userid
     * @return
     */
    public static List<Tag> selectTagsByUId(Long userid){
        SqlSession sqlSession = factory.openSession();
        TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
        List<Tag> tags = tagMapper.selectTagsByUId(userid);
        sqlSession.commit();
        sqlSession.close();
        return tags;
    }


}
