package com.leena.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class LogDAO extends AbstractDAO {
	
//01.23 ip 저장하기
//map 으로 가져오는 방법 또는 변수로 가져오는 방법(오버라이드)
	
	//얘를 통째로 abstract 로 옮겨줬음.
/*	public void logWrite(String ip, String url, String data) {
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
	*/
	
	public void write(Map<String, Object> log) {
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO iplog (iip, iurl, idata) VALUES (?, ?, ?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, (String) log.get("ip"));
			pstmt.setString(2, (String) log.get("url"));
			pstmt.setString(3, (String) log.get("data"));

			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
