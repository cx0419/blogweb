package com.service;

import com.alibaba.fastjson.JSON;
import com.pojo.User;
import com.util.MD5Util;
import jdk.nashorn.internal.scripts.JD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC获取user表信息
 */
public class JDBCservice {

	public static List<User> selectAllService(){
		try {
			//加载驱动程序
			Class.forName("com.mysql.cj.jdbc.Driver");

			//创建连接
			//java10为数据库名
			String url="jdbc:mysql://localhost:3306/blogweb?useSSL=false&serverTimezone=UTC";
			String username="root";
			String userpwd="chenxiang";
			Connection conn = DriverManager.getConnection(url,username,userpwd);

			//创建Statement，执行sql
			Statement st=conn.createStatement();
			ResultSet rs=st.executeQuery("select * from t_user");
			List<User> users = new ArrayList<>();

			while(rs.next()){
				User user = new User();
				user.setName(rs.getString("name"));
				user.setGender(rs.getBoolean("gender"));
				user.setAccount(rs.getString("account"));
				users.add(user);
			}

			//关闭连接
			rs.close();
			st.close();
			conn.close();
			return users;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  null;
	}

	/*
	*根据账号密码查询user
	 */
	public static User selectUserByAccountAndPassword(String account,String password){
		try {
			//加载驱动程序
			Class.forName("com.mysql.cj.jdbc.Driver");

			//创建连接
			//java10为数据库名
			String url="jdbc:mysql://localhost:3306/blogweb?useSSL=false&serverTimezone=UTC";
			String username="root";
			String userpwd="chenxiang";
			Connection conn = DriverManager.getConnection(url,username,userpwd);
			//创建Statement，执行sql
			Statement st=conn.createStatement();
			password = MD5Util.code(password);
			String sql = "select * from t_user where account = "+account + " and `password` = '"+password+"'";
			System.out.println("sql = " + sql);
			ResultSet rs=st.executeQuery(sql);
			User user = new User();

			while(rs.next()){
				user.setId(Long.valueOf(rs.getString("id")));
				user.setAccount(rs.getString("account"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setCreateTime(rs.getString("create_time"));
				user.setGender(rs.getBoolean("gender"));
				user.setIntroduction(rs.getString("introduction"));
				user.setBirthday(rs.getString("birthday"));
				user.setLocation(rs.getString("location"));
				user.setAdmin(rs.getBoolean("admin"));
				user.setAvatar(rs.getString("avatar"));
				user.setIsdelete(rs.getBoolean("isdelete"));
			}

			//关闭连接
			rs.close();
			st.close();
			conn.close();
			return user;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  null;
	}

	public static void testConnection(String ip,String dataBaseName){
		try {
			//加载驱动程序
			Class.forName("com.mysql.cj.jdbc.Driver");

			//创建连接
			//java10为数据库名
			String url="jdbc:mysql://"+ip+":3306/"+dataBaseName+"?useSSL=false&serverTimezone=UTC";
			String username="root";
			String userpwd="123456";
			Connection conn = DriverManager.getConnection(url,username,userpwd);
			System.out.println("连接成功");
			System.out.println(JSON.toJSON(conn));
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}



	public static void main(String[] args) {
		JDBCservice.testConnection("101.35.43.156 ","oss");

	}
}
