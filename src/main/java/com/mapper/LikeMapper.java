package com.mapper;

import com.pojo.Like;
import org.apache.ibatis.annotations.Param;

public interface LikeMapper {
    int selectLikeNumByblogId(@Param("blogid") Long blogid);

    void InsertMoreLike(@Param("userid")Long userid,@Param("blogid")Long blogid,@Param("isdelete")Boolean isdelete);

    Like selectLikeByUIdAndBId(@Param("userid")Long userid,@Param("blogid")Long blogid);

}
