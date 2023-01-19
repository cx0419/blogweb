package com.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.annotations.Param;
import java.io.IOException;
import java.io.InputStream;

/**
 * 静态代码块 mybatis 创建sqlSessionFactory 以便于数据库操作
 */
public class SqlSessionFactoryUtils {
    private static SqlSessionFactory sqlSessionFactory;
    static {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        System.out.println("sqlSessionFactory = " + sqlSessionFactory);

    }

    public static SqlSessionFactory getSqlSessionFactory(){
        return sqlSessionFactory;
    }


    public static void main(String[] args) {
        System.out.println(sqlSessionFactory);
    }
}
