package com.service;

import com.mapper.UserMapper;
import com.pojo.Blog;
import com.pojo.User;
import com.service.BaseService.BaseService;
import com.util.MD5Util;
import com.util.StringUtil;
import org.apache.ibatis.session.SqlSession;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class UserListService implements BaseService {
    /**
     * 查询所有
     * @return
     */
    public static List<Long> selectAll(){
//        2. 获取sqlsession
        SqlSession sqlSession = factory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        System.out.println("usermapper:"+userMapper);
        List<Long> users = userMapper.selectAll();
        sqlSession.close();
        return users;
    }

    /**
     * 通过账号和密码来获取用户信息
     * @param account
     * @param password
     * @return
     */
    public static User selectByAP(String account, String password){
//        SqlSession sqlSession = factory.openSession();
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//        password = MD5Util.code(password);
//        User user = userMapper.selectByUP(account, password);
//        sqlSession.close();
        return JDBCservice.selectUserByAccountAndPassword(account,password);
    }

    /**
     * 通过账号找到一个user对象
     * @param account
     * @return
     */
    public static User selectByAccount(String account){
        SqlSession sqlSession = factory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectByAccount(account);
        sqlSession.close();
        return user;
    }

    /**
     * 通过账号找到一个user对象
     * @param email
     * @return
     */
    public static User selectByEmail(String email){
        SqlSession sqlSession = factory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectByEmail(email);
        sqlSession.close();
        return user;
    }


    public static User selectById(Long id){
        SqlSession sqlSession = factory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectById(id);
        sqlSession.close();
        return user;
    }


    /**
     * 注册账号
     * @param name
     * @param password
     * @param email
     */
    public static String addUser(String name, String password, String email){
        SqlSession sqlSession = factory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        String account = StringUtil.getNum(10);
        User user = new User();
        user.setName(name);
        password = MD5Util.code(password);
        user.setPassWord(password);
        user.setEmail(email);
        user.setAvatar("https://profile.csdnimg.cn/3/D/A/1_seventy70");
        user.setAccount(account);
        user.setCreateTime(new Timestamp(new Date().getTime()).toString());
        userMapper.addUser(user);
        sqlSession.commit();
        sqlSession.close();
        return account;
    }

    /**
     * 资料更新
     * @param user
     */
    public static void UpdateInfo(User user){
        SqlSession sqlSession = factory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.updateUser(user);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
    }

    /**
     * 修改密码
     */
    public static void  AlterPassword(String account,String password){
        SqlSession sqlSession = factory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        password = MD5Util.code(password);
        userMapper.UpdatePassword(account, password);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
    }

    /**
     *更新头像
     */
    public static void  AlterAvatar(Long id,String avatar){
        SqlSession sqlSession = factory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.UpdateAvatar(id, avatar);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
    }

    /**
     * 通过关键字查询出所有博客
     * @param key
     * @return
     */
    public static List<User>  selectUsersByKey(int pagesize,int pagenum,String key){
        SqlSession sqlSession = factory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.selectUsersByKey(pagesize*(pagenum-1),pagesize,key);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
        return users;
    }

    public static int  selectUsersNumByKey(String key){
        SqlSession sqlSession = factory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int num = userMapper.selectUsersNumByKey(key);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
        return num;
    }

    /**
     * 更新用户是否被删除字段
     * @param st
     * @param id
     */
    public static void updateUserIsDelete(Boolean st,Long id){
        SqlSession sqlSession = factory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.updateUserIsDelete(st,id);
        sqlSession.commit();
        //5.关闭SqlSession
        sqlSession.close();
    }

    /**
     * 通过userid获取总浏览量
     */
    public static int getViewsByUserId(Long userid){
        SqlSession sqlSession = factory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        //获取这个user对象的所有博客的view字段
        List<Blog> blogs = BlogListService.seletAllBlogById(userid);
        int total = 0;
        for (Blog blog:blogs) {
            total += blog.getViews();
        }
        sqlSession.close();
        return total;
    }


    /**
     * 通过userid获取总点赞数
     */
    public static int getLikeNumByUserId(Long userid){
        SqlSession sqlSession = factory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        //获取这个user对象的所有博客的view字段
        List<Blog> blogs = BlogListService.seletAllBlogById(userid);
        int total = 0;
        for (Blog blog:blogs) {
            total += LikeService.CheckLikeNumByBlogId(blog.getId());
        }
        sqlSession.close();
        return total;
    }



}
