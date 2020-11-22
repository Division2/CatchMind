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
	
	//DB���� �޼ҵ�
	public void Connect() {
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			
		//	stmt = conn.createStatement();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	//SQL Insert �޼ҵ�
	public void Insert(String dbInsert) {
		try {
			pstmt = conn.prepareStatement(dbInsert);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	//SQL Select �޼ҵ�
	public void Select(String dbSelect) {
		try {
			pstmt = conn.prepareStatement(dbSelect);
		//	rs = stmt.executeQuery(dbSelect);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	//SQL Delete �޼ҵ�
	public void Delete(String dbDelete) {
		try {
			rs = stmt.executeQuery(dbDelete);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	//SQL Update �޼ҵ�
	public void Update(String dbUpdate) {
		try {
			pstmt = conn.prepareStatement(dbUpdate);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	//DB���� ���� �޼ҵ�
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
	
	//���� ó�� �޼ҵ�
	public void setOnline(String userid) {
		Update("UPDATE Account SET isOnline = 1 WHERE UserID = ?");
		try {
			pstmt.setString(1, userid);
			pstmt.executeUpdate();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	//�α׾ƿ� ó�� �޼ҵ�
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