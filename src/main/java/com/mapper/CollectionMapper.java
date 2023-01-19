package com.mapper;

import com.pojo.Blog;
import com.pojo.BlogCollection;
import com.pojo.Collection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectionMapper {

    List<Collection> selectAllCollectionByUserId(@Param("id")Long id);

    BlogCollection selectBlogCollectionByCollectionIdAndBlogId(@Param("collectionid")Long collectionid,@Param("blogid")Long blogid);

    void addCollection(Collection collection);

    void updateCollectionDelete(@Param("name")String name,@Param("id") Long id,@Param("state") Boolean state);

    //这个地方select是不需要判断isdelete的,因为这个字段
    Collection selectCollectionByUidAndName(Collection collection);

    //
    BlogCollection selectBlogCollectionByBIdAndCId(@Param("blogId")Long blogId, @Param("collectionId")Long collectionId);

    void updateBlogCollectionDelete(@Param("blogid")Long blogid, @Param("collectionid")Long collectionid, @Param("state")Boolean state);

    void addBlogCollection(BlogCollection blogCollection);

    List<Blog> selectBlogsByCollectionId(@Param("offset")int offset,@Param("pagesize")int pagesize,@Param("collectionid")Long collectionid);

    int selectBlogsNumByCollectionId(@Param("collectionid")Long collectionid);

    void LogicalDeleteBlogCollection(@Param("collectionid")Long collectionid,@Param("blogid")Long blogid);

    void alterCollectionName(@Param("collectionid")Long collectionid,@Param("name")String name);

    void deleteBlogsCollectionByCollectionId(@Param("collectionid")Long collectionid);

    void deleteCollectionCollectionId(@Param("collectionid")Long collectionid);

    Collection selectCollectionByUIdAndName(@Param("userid")Long userid,@Param("name")String name);

    int selectCollectionNumByBlogId(@Param("blogid")Long blogid);

    Long selectUserIsCollectionByBIdAndUId(@Param("userid") Long userid,@Param("blogid")Long blogid);
}
