package com.java.ex.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DataBase {
	private static final String DB_URL = "jdbc:mysql://localhost/catchmind?useSSL=false";
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_USER = "catchmind";
	private static final String DB_PASS = "tjdehd1@";
	
	private Connection conn = null;
	private Statement stmt = null;
	public PreparedStatement pstmt = null;
	public ResultSet rs = null;
	
	public DataBase() {
		Connect();
	}
	
	//DB연결 메소드
	public void Connect() {
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			
		//	stmt = conn.createStatement();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	//SQL Insert 메소드
	public void Insert(String dbInsert) {
		try {
			pstmt = conn.prepareStatement(dbInsert);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	//SQL Select 메소드
	public void Select(String dbSelect) {
		try {
			pstmt = conn.prepareStatement(dbSelect);
		//	rs = stmt.executeQuery(dbSelect);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	//SQL Delete 메소드
	public void Delete(String dbDelete) {
		try {
			rs = stmt.executeQuery(dbDelete);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	//SQL Update 메소드
	public void Update(String dbUpdate) {
		try {
			pstmt = conn.prepareStatement(dbUpdate);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	//DB연결 종료 메소드
	public void Close() {
		try {
			if (conn != null) {
				conn.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	//접속 처리 메소드
	public void setOnline(String userid) {
		Update("UPDATE Account SET isOnline = 1 WHERE UserID = ?");
		try {
			pstmt.setString(1, userid);
			pstmt.executeUpdate();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	//로그아웃 처리 메소드
	public void setOffline(String userid) {
		Update("UPDATE Account SET isOnline = 0 WHERE UserID = ?");
		try {
			pstmt.setString(1, userid);
			pstmt.executeUpdate();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}