package com.mapper;

import com.pojo.BlogColumn;
import com.pojo.Column;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ColumnMapper {
    Long insertColumn(Column column);
    Column selectColumnByNameAndUId(@Param("name")String name, @Param("userid")Long userid);

    BlogColumn selectBlogColumnByBC(@Param("blogId") Long blogId, @Param("columnId") Long columnId);
    void insertBlogColumn(BlogColumn blogColumn);
    List<Column> selectColumnsByKey(@Param("offset")int offset,@Param("pagesize")int pagesize,@Param("key")String key);
    Column selectColumnsByCId(@Param("id")Long id);
    int selectColumnsNumByKey(@Param("key")String key);
    List<BlogColumn> selectBlogColumnsByCId(@Param("id") Long id);

    List<Column> selectColumnsByBlogId(@Param("id")Long id);

    int selectBlogNumByColumnId(@Param("id")Long id);

    int selectColumnNumByUId(@Param("id")Long id);

    void deleteColumnByCId(@Param("id")Long id);

    void deleteBlogColumnByByCId(@Param("id")Long id);

    void updateColumnById(Column column);
    void deleteColumnById(@Param("id")Long id);

    List<Column> selectColumnsByUId(@Param("offset")int offset,@Param("pagesize")int pagesize,@Param("userid")Long userid);
    int selectColumnsNumByUId(@Param("userid")Long userid);

    Column selectColumnById(@Param("columnid")Long columnid);

    void alterColumnShow(@Param("columnid")Long columnid,@Param("show")Boolean show);

    List<Column> selectColumnByUserId(@Param("userid")Long userid);

    List<Column> getAllColumnByUserId(@Param("userid")Long userid);



}
