package com.vanilla.web.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.vanilla.web.vo.MoneyVo;

@Repository
public class MoneyDao {
	
	public List getMoneyLog(String year, String month) {
		
		String url = "jdbc:mysql://localhost:3306/banana";
		String id = "yousend";
		String password = "1111";
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		ResultSet rs = null;
		List<MoneyVo> list = new ArrayList<MoneyVo>();
		String gubun = "";
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, password);
			stmt = conn.createStatement();
			sql = "SELECT SEQ, YEAR, MONTH, DAY, TITLE, ISREGULAR, INTERVALS, PLUSMINUS, AMOUNT " +
				  "FROM MONEYLOG " +
				  "WHERE YEAR = '" + year + "' " +
				  "AND MONTH = '" + month + "' " +
				  "ORDER BY DAY";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				MoneyVo moneyVo = new MoneyVo();
				moneyVo.setSeq(rs.getInt("seq"));
				moneyVo.setYear(rs.getString("year"));
				moneyVo.setYear(rs.getString("year"));
				moneyVo.setMonth(rs.getString("month"));
				moneyVo.setDay(rs.getString("day"));
				moneyVo.setTitle(rs.getString("title"));
				moneyVo.setIsregular(rs.getString("isregular"));
				moneyVo.setIntervals(rs.getString("intervals"));
				
				if (rs.getString("plusminus").equals("P")) {
					gubun = "+";
				} else {
					gubun = "-";
				}
				
				moneyVo.setPlusminus(gubun);
				moneyVo.setAmount(rs.getInt("amount"));
				list.add(moneyVo);
			}
			
		} catch (Exception e) {
			
		} finally {
			try{
				rs.close();
				stmt.close();
				conn.close();
			} catch (Exception e){};
		}
		
		return list;
	}
	
	public List getTitleSum(String year, String month) {
		
		String url = "jdbc:mysql://localhost:3306/banana";
		String id = "yousend";
		String password = "1111";
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		ResultSet rs = null;
		List<MoneyVo> list = new ArrayList<MoneyVo>();
		String gubun = "";
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, password);
			stmt = conn.createStatement();
			sql = "SELECT TITLE, PLUSMINUS, SUM(AMOUNT) AS AMOUNT " +
				  "FROM MONEYLOG " +
				  "WHERE YEAR = '" + year + "' " +
				  "AND MONTH = '" + month + "' " +
				  "GROUP BY TITLE, PLUSMINUS " +
				  "ORDER BY SUM(AMOUNT) DESC";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				MoneyVo moneyVo = new MoneyVo();
				moneyVo.setTitle(rs.getString("title"));
				
				if (rs.getString("plusminus").equals("P")) {
					gubun = "+";
				} else {
					gubun = "-";
				}
				
				moneyVo.setPlusminus(gubun);
				moneyVo.setAmount(rs.getInt("amount"));
				list.add(moneyVo);
			}
			
		} catch (Exception e) {
			
		} finally {
			try{
				rs.close();
				stmt.close();
				conn.close();
			} catch (Exception e){};
		}
		
		return list;
	}
	
	public List getPlusminusList(String year, String plusminus) {
		
		String url = "jdbc:mysql://localhost:3306/banana";
		String id = "yousend";
		String password = "1111";
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		ResultSet rs = null;
		List<MoneyVo> list = new ArrayList<MoneyVo>();
		String gubun = "";
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, password);
			stmt = conn.createStatement();
			sql = "SELECT SEQ, YEAR, MONTH, DAY, TITLE, ISREGULAR, INTERVALS, PLUSMINUS, AMOUNT " +
				  "FROM MONEYLOG " +
				  "WHERE YEAR = '" + year + "' " +
				  "AND PLUSMINUS = '" + plusminus + "' " +
				  "ORDER BY TITLE, MONTH, DAY";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				MoneyVo moneyVo = new MoneyVo();
				moneyVo.setSeq(rs.getInt("seq"));
				moneyVo.setYear(rs.getString("year"));
				moneyVo.setYear(rs.getString("year"));
				moneyVo.setMonth(rs.getString("month"));
				moneyVo.setDay(rs.getString("day"));
				moneyVo.setTitle(rs.getString("title"));
				moneyVo.setIsregular(rs.getString("isregular"));
				moneyVo.setIntervals(rs.getString("intervals"));
				
				if (plusminus.equals("P")) {
					gubun = "+";
				} else {
					gubun = "-";
				}
				
				moneyVo.setPlusminus(gubun);
				moneyVo.setAmount(rs.getInt("amount"));
				list.add(moneyVo);
			}
			
		} catch (Exception e) {
			
		} finally {
			try{
				rs.close();
				stmt.close();
				conn.close();
			} catch (Exception e){};
		}
		
		return list;
	}
	
	public void insertMoneyLog(Map param) {
		
		String url = "jdbc:mysql://localhost:3306/banana";
		String id = "yousend";
		String password = "1111";
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, password);
			stmt = conn.createStatement();
			sql = "INSERT INTO MONEYLOG (YEAR, MONTH, DAY, TITLE, ISREGULAR, INTERVALS, PLUSMINUS, AMOUNT) " +
				  "VALUES ( " + 
				  "'" + param.get("year") + "' ," +
				  "'" + param.get("month") + "' ," +
				  "'" + param.get("day") + "' ," +
				  "'" + param.get("title") + "' ," +
				  "'" + param.get("isregular") + "' ," +
				  "'" + param.get("intervals") + "' ," +
				  "'" + param.get("plusminus") + "' ," +
				  param.get("amount") +
				  ")";
			stmt.executeUpdate(sql);
			
		} catch (Exception e) {
			
		} finally {
			try{
				stmt.close();
				conn.close();
			} catch (Exception e){};
		}
		
	}
	
	public void deleteMoneyLog(Map param) {
		
		String url = "jdbc:mysql://localhost:3306/banana";
		String id = "yousend";
		String password = "1111";
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, password);
			stmt = conn.createStatement();
			sql = "DELETE FROM MONEYLOG WHERE SEQ = " + param.get("seq");
			stmt.executeUpdate(sql);
			
		} catch (Exception e) {
			
		} finally {
			try{
				stmt.close();
				conn.close();
			} catch (Exception e){};
		}
		
	}

}
