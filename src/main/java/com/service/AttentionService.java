package com.service;

import com.mapper.AttentionMapper;
import com.pojo.Attention;
import com.pojo.User;
import com.service.BaseService.BaseService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class AttentionService implements BaseService {
    /**
     * 查询一个账号的所有粉丝
     * @param id
     * @return
     */
    public static List<User> selectAllFansByBloggerId(@Param("id")Long id){
        SqlSession sqlSession = factory.openSession();
        AttentionMapper attentionMapper = sqlSession.getMapper(AttentionMapper.class);
        List<User> users = attentionMapper.selectAllFansByBloggerId(id);
        sqlSession.close();
        return users;
    }

    /**
     * 查询一个账号的所有关注
     * @param id
     * @return
     */
    public static List<User> selectAllFocusByBloggerId(@Param("id") Long id){
        SqlSession sqlSession = factory.openSession();
        AttentionMapper attentionMapper = sqlSession.getMapper(AttentionMapper.class);
        List<User> users = attentionMapper.selectAllFocusByFansId(id);
        sqlSession.close();
        return users;
    }

    /**
     * 添加一行关注，没有则添加 有则更新
     * @param attention
     */
    public static void addAttention(Attention attention){
        if(SelectAttentionIsemptyNodelete(attention)){
            //插入
            SqlSession sqlSession = factory.openSession();
            AttentionMapper attentionMapper = sqlSession.getMapper(AttentionMapper.class);
            attentionMapper.addAttention(attention);
            sqlSession.commit();
            sqlSession.close();
        }else{
            //更新
            UpdateInfo(attention);
        }

    }

    /**
     * 更新Attention
     * @param attention
     */
    public static void UpdateInfo(Attention attention){
        SqlSession sqlSession = factory.openSession();
        AttentionMapper attentionMapper = sqlSession.getMapper(AttentionMapper.class);
        attentionMapper.updateAttention(attention);;
        sqlSession.commit();
        sqlSession.close();
    }

    public static boolean SelectAttentionIsempty(Attention attention){
        SqlSession sqlSession = factory.openSession();
        AttentionMapper attentionMapper = sqlSession.getMapper(AttentionMapper.class);
        Attention attention1 = attentionMapper.selectAttentionIsEmpty(attention);
        sqlSession.close();
        return attention1==null;
    }
    public static boolean SelectAttentionIsemptyNodelete(Attention attention){
        SqlSession sqlSession = factory.openSession();
        AttentionMapper attentionMapper = sqlSession.getMapper(AttentionMapper.class);
        Attention attention1 = attentionMapper.selectAttentionIsEmptyNodelete(attention);
        sqlSession.close();
        return attention1==null;
    }

    /**
     * 获取粉丝数量
     * @param userid
     * @return
     */
    public static int getFansNumByUserId(Long userid){
        SqlSession sqlSession = factory.openSession();
        AttentionMapper attentionMapper = sqlSession.getMapper(AttentionMapper.class);
        int num = attentionMapper.getFansNumByUserId(userid);
        return num;
    }

    /**
     * 获取关注数量
     * @param userid
     * @return
     */
    public static int getFocusNumByUserId(Long userid){
        SqlSession sqlSession = factory.openSession();
        AttentionMapper attentionMapper = sqlSession.getMapper(AttentionMapper.class);
        int num = attentionMapper.getFocusNumByUserId(userid);
        return num;
    }
}
