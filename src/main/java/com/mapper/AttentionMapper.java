package com.mapper;

import com.pojo.Attention;
import com.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AttentionMapper {
    /**
     * 返回一个账号的所有粉丝
     * @param id
     * @return
     */
    List<User> selectAllFansByBloggerId(@Param("id") Long id);


    /**
     * 返回一个账号的所有关注
     * @param id
     * @return
     */
    List<User> selectAllFocusByFansId(@Param("id")Long id);

    /**
     * 添加一个关注
     */
    void addAttention(Attention attention);

    /**
     * 更新关注信息
     * @param attention
     */
    void updateAttention(Attention attention);


    Attention selectAttentionIsEmpty(Attention attention);

    Attention selectAttentionIsEmptyNodelete(Attention attention);

    int getFansNumByUserId(@Param("userid")Long userid);

    int getFocusNumByUserId(@Param("userid")Long userid);
}
