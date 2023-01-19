package com.mapper;

import com.pojo.User;
import com.pojo.UserExtend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    /**
     * 查询所有用户
     * @return
     */
    List<Long> selectAll();

    /**登录验证
     * @param account
     * @param password
     * @return
     */
    User selectByUP(@Param("account") String account, @Param("password") String password);
    /**
     * 通过账号判断该用户信息是否存在
     * @param account
     * @return
     */
    User selectByAccount(@Param("account") String account);

    User selectByEmail(@Param("email")String email);

    User selectById(@Param("id")Long id);
    /**
     * 注册用户
     * @param user
     */
    void addUser(User user);

    void updateUser(User user);

    void UpdatePassword(@Param("account") String account, @Param("password") String password);

    //更新头像
    void UpdateAvatar(@Param("id") Long id,@Param("avatar") String avatar);

    //通过关键字查找相关用户
    List<User> selectUsersByKey(@Param("offset")int offset,@Param("pagesize")int pagesize,@Param("key") String key);
    int selectUsersNumByKey(@Param("key") String key);

    //通过userid获取到扩展对象
    UserExtend selectUserExtendByUId(@Param("uid")Long uid);

    //删除user
    void updateUserIsDelete(@Param("St") Boolean st,@Param("id") Long id);

}
