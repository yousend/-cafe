package com.vanilla.web.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.vanilla.web.vo.UserVo;

@Repository
public class MainDao {
	
	public UserVo getUser(String email) {
		
		String url = "jdbc:mysql://localhost:3306/banana";
		String id = "yousend";
		String password = "1111";
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		ResultSet rs = null;
		UserVo user = new UserVo();
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, password);
			stmt = conn.createStatement();
			sql = "SELECT ID, EMAIL FROM USER WHERE email = '";
			rs = stmt.executeQuery(sql + email + "'");
			while(rs.next()) {
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
			}
			
		} catch (Exception e) {
			
		} finally {
			try{
				rs.close();
				stmt.close();
				conn.close();
			} catch (Exception e){};
		}
		
		return user;
	}

}
