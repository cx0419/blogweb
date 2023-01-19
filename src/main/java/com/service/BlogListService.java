package com.service;

import com.mapper.BlogMapper;
import com.pojo.Blog;
import com.pojo.BlogColumn;
import com.service.BaseService.BaseService;
import com.util.StringUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class BlogListService implements BaseService {

    /**
     * 找到所有已经上线的文章id
     * @return
     */
    public static List<Long> selectAllBlogToReCommend(){
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        return blogMapper.selectAllBlogToReCommend();
    }
    /**
     * 添加一篇博客
     * @param blog
     */
    public static Long addBlog(Blog blog){
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        String account = StringUtil.getNum(10);
        Long id = blogMapper.addBlog(blog);
        sqlSession.commit();
        sqlSession.close();
        return blog.getId();
    }

    /**
     *根据用户id找出所有博客
     * @param id
     * @return
     */
    public static List<Blog> seletAllBlogById(int pagesize,int pagenum,Long id){
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        List<Blog> blogs = blogMapper.selectBlogByUserIdpage(pagesize*(pagenum-1),pagesize,id);
        sqlSession.close();
        return blogs;
    }

    /**
     * 方法重载 此方法一次全部查出来  这里主要用来查找出总浏览量方法调用
     * @param id
     * @return
     */
    public static List<Blog> seletAllBlogById(Long id){
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        List<Blog> blogs = blogMapper.selectBlogByUserId(id);
        sqlSession.close();
        return blogs;
    }

    /**
     *根据用户id找出所有博客 包含草稿
     * @param id
     * @return
     */
    public static int selectAllBlogNumByUId(Long id){
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        int num = blogMapper.selectAllBlogNumByUId(id);
        sqlSession.close();
        return num;
    }


    /**
     *根据用户id找出所有博客 包含草稿
     * @param id
     * @return
     */
    public static List<Blog> seletAllBlogByIdIncludeCao(int pagesize,int pagenum,Long id,String nian,String yue,String column,String tag,String draft,String examine,String key,String original){
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        List<Blog> blogs = blogMapper.seletAllBlogByIdIncludeCao(pagesize*(pagenum-1),pagesize,id,nian,yue,column,tag,draft,examine,key,original);
        sqlSession.close();
        return blogs;
    }

    /**
     *根据用户id找出所有博客数量
     * @param id
     * @return
     */
    public static int seletAllBlogNumByIdIncludeCao(Long id,String nian,String yue,String column,String tag,String draft,String examine,String key,String original){
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        int num = blogMapper.seletAllBlogNumByIdIncludeCao(id,nian,yue,column,tag,draft,examine,key,original);
        sqlSession.close();
        return num;
    }


    /**
     * 更新博客
     * @param blog
     */
    public static void updateBlog(Blog blog){
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        blogMapper.updateBlog(blog);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
    }

    public static Blog selectBlogById(Long id){
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = blogMapper.selectBlogById(id);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
        return blog;
    }

    /**
     * 根据关键字查询所有博客
     * @param key
     * @return
     */
    public static List<Blog> selectBlogsByKey(int pagesize,int pagenum,String key){
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        List<Blog> blogs = blogMapper.selectBlogsByKey(pagesize*(pagenum-1),pagesize,key);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
        return blogs;
    }

    public static int selectBlogsNumByKey(String key){
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        int i = blogMapper.selectBlogsNumByKey(key);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
        return i;
    }


    public static Blog selectBlogTitleById(Long id){
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = blogMapper.selectBlogTitleById(id);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
        return blog;
    }

    /**
     *根据博客id列表将这些博客的标题全部取出来
     * @param blogColumns
     * @return
     */
    public static List<Blog> selectBlogsByIds(List<BlogColumn> blogColumns){
        SqlSession sqlSession = factory.openSession();
        List<Blog> blogs = new ArrayList<>(0);
        for(BlogColumn blogColumn:blogColumns){
            blogs.add(selectBlogTitleById(blogColumn.getBlogId()));
        }
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
        return blogs;
    }


    /**
     * 上传博客
     * @param blog
     */
    public static void uploadBlog(Blog blog){

    }

    /**
     * 找出审核中的文章
     * @return
     */
    public static List<Blog> selectNoExamineBlogs(int pagesize,int pagenum){
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        List<Blog> blogs = blogMapper.selectExamineBlogs(pagesize*(pagenum-1),pagesize);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
        return blogs;
    }

    /**
     * 找出该种文章的数量
     * @return
     */
    public static int selectNoExamineBlogsnum(){
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        int num = blogMapper.selectExamineBlogsnum();
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
        return num;
    }



    //审核通过
    public static void  passBlog(Long id){
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        blogMapper.updateBlogPass("已通过", id);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
    }

    //审核不通过
    public static void  nopassBlog(Long id) {
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        blogMapper.updateBlogPass("不通过", id);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
    }

    //删除博客
    public static void  deleteBlogById(Long id) {
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        blogMapper.deleteBlogById(id);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
    }


    //检查这片博客的作者 是否与前端传来的作者一致
    public static Boolean  checkBlogWriter(Long blogid,Long userid) {
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = blogMapper.selectBlogByBIdAndUId(blogid,userid);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();

        if(blog==null){
            return false;
        }else{
            return true;
        }
    }

    public static List<Blog> selectBlogsByData(){
        SqlSession sqlSession = factory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        List<Blog>  blogs = blogMapper.selectBlogsByData("2021","08",null);
        sqlSession.commit();
        //5.关闭SqlSession
        return blogs;
    }

    public static void main(String[] args) {
        List<Blog> blogs = selectBlogsByData();
        for (Blog blog:blogs) {
            System.out.println(blog.getTitle());
        }
        System.out.println("1");
    }



}
