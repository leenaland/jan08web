package com.leena.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.leena.db.DBConnection;

//부모 DAO = DBConn, close
public class AbstractDAO {
	DBConnection db = DBConnection.getInstance();
	
	//01.23 로그아이피 저장하는 메서드 
	public void logWrite(String ip, String url, String data) {
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO iplog (iip, iurl, idata) VALUES (?, ?, ?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ip);
			pstmt.setString(2, url);
			pstmt.setString(3, data);

			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void close(ResultSet rs, PreparedStatement pstmt, Connection con) {
		if(rs != null)	{
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
