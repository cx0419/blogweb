package com.service;

import com.mapper.CollectionMapper;
import com.pojo.Blog;
import com.pojo.BlogCollection;
import com.pojo.Collection;
import com.service.BaseService.BaseService;
import com.util.StringUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CollectionService implements BaseService {

    /**
     * 通过用户id查询他的所有收藏夹
     * @param userid
     * @return
     */
    public static List<Collection> getCollectionsByUserId(Long userid){
        SqlSession sqlSession = factory.openSession();
        CollectionMapper collectionMapper = sqlSession.getMapper(CollectionMapper.class);
        List<Collection> collections = collectionMapper.selectAllCollectionByUserId(userid);
        sqlSession.close();
        return collections;
    }

    /**
     * 通过收藏夹id 和 博客 id 查询关系
     *
     * @return
     */
    public static BlogCollection selectBlogCollectionByCollectionIdAndBlogId(Long collection,Long blogid){
        SqlSession sqlSession = factory.openSession();
        CollectionMapper collectionMapper = sqlSession.getMapper(CollectionMapper.class);
        BlogCollection blogCollection= collectionMapper.selectBlogCollectionByCollectionIdAndBlogId(collection,blogid);
        sqlSession.close();
        return blogCollection;
    }

    /**
     * 在数据库增加一行
     * @param collection
     */
    public static void addCollection(Collection collection){
        SqlSession sqlSession = factory.openSession();
        CollectionMapper collectionMapper = sqlSession.getMapper(CollectionMapper.class);
        collectionMapper.addCollection(collection);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 每个账号只能包含相同名字的收藏夹一个,所以在这检查这个行是否存在 这个检查不能在where当中含有isdelete判断字段
     * @param collection
     * @return
     */
    public static Collection selectCollectionByUidAndName(Collection collection){
        SqlSession sqlSession = factory.openSession();
        CollectionMapper collectionMapper = sqlSession.getMapper(CollectionMapper.class);
        Collection collection1= collectionMapper.selectCollectionByUidAndName(collection);
        sqlSession.close();
        return collection1;
    }

    /**
     * 增加新的收藏夹
     */
    public static Boolean InsertCollection(Collection collection){
        Collection checkcolleciontemp = selectCollectionByUidAndName(collection);
        if(checkcolleciontemp!=null){
            //不为空 说明已经存在 , 返回false 不允许增加同名收藏夹
            return false;
        }else{
            //为空则进行插入操作
            addCollection(collection);
            return true;
        }
    }


    public static void  updateCollectionDeleteByUIdAndName(String name,Long id,Boolean state){
        SqlSession sqlSession = factory.openSession();
        CollectionMapper collectionMapper = sqlSession.getMapper(CollectionMapper.class);
        collectionMapper.updateCollectionDelete(name,id,state);
        sqlSession.commit();
        sqlSession.close();
    }


    /**
     * 收藏某一篇文章的时候先看这个文章以前有没有被收藏过
     * @param blogid
     * @param collectionid
     * @return
     */
    public static BlogCollection checkBlogIsInCollection(Long blogid,Long collectionid){
        SqlSession sqlSession = factory.openSession();
        CollectionMapper collectionMapper = sqlSession.getMapper(CollectionMapper.class);
        BlogCollection blogCollection = collectionMapper.selectBlogCollectionByBIdAndCId(blogid,collectionid);
        sqlSession.close();
        return blogCollection;
    }

    //更新删除关系
    public static void updateBlogCollectionDeleteByCIdAndBId(Long blogid,Long collectionid,Boolean state){
        SqlSession sqlSession = factory.openSession();
        CollectionMapper collectionMapper = sqlSession.getMapper(CollectionMapper.class);
        collectionMapper.updateBlogCollectionDelete(blogid,collectionid,state);
        sqlSession.commit();
        sqlSession.close();
    }
    //增加一行关系
    public static void addBlogCollection(Long blogid,Long collectionid){
        SqlSession sqlSession = factory.openSession();
        CollectionMapper collectionMapper = sqlSession.getMapper(CollectionMapper.class);
        BlogCollection blogCollection = new BlogCollection();
        blogCollection.setCollectionId(collectionid);
        blogCollection.setBlogId(blogid);
        blogCollection.setUpdateTime(StringUtil.getTime());
        blogCollection.setIsdelete(false);
        collectionMapper.addBlogCollection(blogCollection);
        sqlSession.commit();
        sqlSession.close();
    }

    //逻辑增加一行
    public static void InsertBlogTOCollection(Long blogid,Long collectionid){
        BlogCollection blogCollection = checkBlogIsInCollection(blogid,collectionid);
        if(blogCollection!=null){
            //不为空则说明更新就好了
            updateBlogCollectionDeleteByCIdAndBId(blogid,collectionid,false);
        }else{
            //否则就修改数据库
            addBlogCollection(blogid,collectionid);
        }
    }

    //获取指定页 通过收藏夹id获取所有博客
    public static List<Blog> selectBlogsByCollectionId(int pagesize, int pagenum, Long collectionid){
        SqlSession sqlSession = factory.openSession();
        CollectionMapper collectionMapper = sqlSession.getMapper(CollectionMapper.class);
        List<Blog> blogs = collectionMapper.selectBlogsByCollectionId(pagesize*(pagenum-1),pagesize,collectionid);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
        return blogs;
    }


    //获取指定页 通过收藏夹id获取所有博客
    public static int selectBlogsNumByCollectionId(Long collectionid){
        SqlSession sqlSession = factory.openSession();
        CollectionMapper collectionMapper = sqlSession.getMapper(CollectionMapper.class);
        int num = collectionMapper.selectBlogsNumByCollectionId(collectionid);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
        return num;
    }

    //取消收藏博客
    public static void cancelCollection(Long collectionid,Long blogid){
        SqlSession sqlSession = factory.openSession();
        CollectionMapper collectionMapper = sqlSession.getMapper(CollectionMapper.class);
        collectionMapper.LogicalDeleteBlogCollection(collectionid,blogid);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
    }

    /**
     * 在一个用户所有的收藏夹中查找这个名字的收藏夹是否存在
     * @param userid
     * @param name
     */
    public static Collection selectCollectionByUIdAndName(Long userid,String name){
        SqlSession sqlSession = factory.openSession();
        CollectionMapper collectionMapper = sqlSession.getMapper(CollectionMapper.class);
        Collection collection = collectionMapper.selectCollectionByUIdAndName(userid,name);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
        return collection;
    }

    /**
     * 修改收藏夹名字
     * @param collectionid
     * @param name
     */
    public static void alterCollectionName(Long collectionid,String name){
        SqlSession sqlSession = factory.openSession();
        CollectionMapper collectionMapper = sqlSession.getMapper(CollectionMapper.class);
        collectionMapper.alterCollectionName(collectionid,name);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
    }

    /**
     * 删除收藏夹 这里直接删除掉这个收藏夹 和 这个收藏夹内的所有内容
     */
    public static void DeleteOnesCollection(Long collectionid){
        DeleteCollection(collectionid);
        DeleteBlogsCollection(collectionid);

    }

    public static void DeleteCollection(Long collectionid){
        SqlSession sqlSession = factory.openSession();
        CollectionMapper collectionMapper = sqlSession.getMapper(CollectionMapper.class);
        collectionMapper.deleteCollectionCollectionId(collectionid);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();

    }

    /**
     * 删除收藏里面的一个博客
     * @param collectionid
     */
    public static void DeleteBlogsCollection(Long collectionid){
        SqlSession sqlSession = factory.openSession();
        CollectionMapper collectionMapper = sqlSession.getMapper(CollectionMapper.class);
        collectionMapper.deleteBlogsCollectionByCollectionId(collectionid);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
    }


    /**
     * 通过博客id找到收藏数量 这个收藏数量 是指收藏的人数 将对userid进行去重
     * @param collectionid
     */
    public static int selectCollectionNumByBlogId(Long collectionid){
        SqlSession sqlSession = factory.openSession();
        CollectionMapper collectionMapper = sqlSession.getMapper(CollectionMapper.class);
        int num = collectionMapper.selectCollectionNumByBlogId(collectionid);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
        return num;
    }

    /**
     * 通过博客id 查看收藏夹它的人是否存在
     */
    public static Long selectUserIsCollectionByBIdAndUId(Long blogid, Long userid){
        SqlSession sqlSession = factory.openSession();
        CollectionMapper collectionMapper = sqlSession.getMapper(CollectionMapper.class);
        Long uid = collectionMapper.selectUserIsCollectionByBIdAndUId(userid,blogid);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
        return uid;
    }

}
