package com.service;

import com.mapper.UserMapper;
import com.pojo.UserExtend;
import com.service.BaseService.BaseService;
import org.apache.ibatis.session.SqlSession;

public class UserExtendService implements BaseService {

    /**
     * 通过userid获取扩展对象
     * @param id
     * @return
     */
    public static UserExtend selectUserExtendsById(Long id){
        SqlSession sqlSession = factory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        UserExtend userExtend = userMapper.selectUserExtendByUId(id);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
        return userExtend;
    }

}
