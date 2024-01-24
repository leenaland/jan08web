package com.leena.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.leena.db.DBConnection;
import com.leena.dto.BoardDTO;
import com.leena.dto.CommentDTO;
import com.leena.util.Util;

public class BoardDAO extends AbstractDAO{

	public List<BoardDTO> boardList(int page) { // dto 에 있는 값을 list 로 가져올거다
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		// db 정보
		// DBConnection db = DBConnection.getInstance();
		// conn 객체
		Connection con = DBConnection.getInstance().getConnection();
		// pstmt
		PreparedStatement pstmt = null;
		// 원래는 statement 썼는데, 이거 쓰면 요기어때 해킹 사건 일어날 수도 있음
		// rs
		ResultSet rs = null;
		// sql
		String sql = "SELECT * FROM boardview LIMIT ?, 10";

		try {
			pstmt = con.prepareStatement(sql);
			//01.16 페이징      
			pstmt.setInt(1, (page - 1) * 10); 
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardDTO e = new BoardDTO();
				e.setNo(rs.getInt(1));
				e.setTitle(rs.getString(2));
				e.setWrite(rs.getString(3));
				e.setDate(rs.getString(4));
				e.setCount(rs.getInt(5));
				e.setComment(rs.getInt(6));//01.19 추가
				list.add(e);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 거꾸로 닫아줘야함
			close(rs,pstmt,con); // 닫을때도 에러 날 수 있으니까 TRY 해주세요
		}	// try 문 너무 길어지까 아예 메소드로 만들어 주세요.

		return list;
	}
	


	public BoardDTO detail(int no) {
		BoardDTO dto = new BoardDTO();
		
		Connection con = DBConnection.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = 
				
				"SELECT b.board_no, b.board_title, b.board_content, m.mname as board_write, m.mid, "
			            + "b.board_date, b.board_ip, "
			            + "(SELECT COUNT(*) FROM visitcount WHERE board_no=b.board_no) AS board_count " 
			            + "FROM board b JOIN member m ON b.mno=m.mno " 
			            + "WHERE b.board_no=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { //반복이 아니니까 굳이 while 안쓴것
				dto.setNo(rs.getInt("board_no"));
				dto.setTitle(rs.getString("board_title"));
				dto.setContent(rs.getString("board_content"));
				dto.setWrite(rs.getString("board_write"));
				dto.setDate(rs.getString("board_date"));
				dto.setCount(rs.getInt("board_count"));
				dto.setMid(rs.getString("mid")); 
				dto.setIp(rs.getString("board_ip"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	public int write(BoardDTO dto) {
		int result = 0;
		
		Connection con = DBConnection.getInstance().getConnection();
		PreparedStatement pstmt = null;
//		result 가 값 가져와서 rs 안만들어도됨
	//	String sql = "INSERT INTO board (board_title, board_content, mno) "
	//			+ "VALUES(?,?,(SELECT mno FROM member WHERE mid=?))";
		//01.23 글쓰니 ip 추가
		String sql = "INSERT INTO board (board_title, board_content, mno, board_ip) "
				+ "VALUES(?,?,(SELECT mno FROM member WHERE mid=?), ?)";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
//			pstmt.setString(3, "이대중앙");
			pstmt.setString(3, dto.getMid());
			//01.23 글쓰니 ip 추가
			pstmt.setString(4, dto.getIp());
			
			result = pstmt.executeUpdate(); //영향받은 행의 갯수를 result에 저장합니다

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, pstmt, con);
		}
		
		return result;
	}
	
//01.16 delete(no) 에서 delete(BoardDTO dto) 로 타입, 변수 변경
	public int delete(BoardDTO dto) {
		int result = 0;
		//conn
		Connection con = DBConnection.getInstance().getConnection();
		//pstmt
		PreparedStatement pstmt = null;
		//spl
//		String sql = "DELETE FROM board WHERE board_no=?";
		
		// 01.16 보드 no 랑 mno 랑 일치하는 애들을 삭제한다
//		String sql = "DELETE FROM board WHERE board_no=? AND mno=(SELECT mno FROM  member WHERE mid=?)";
		
		//01.19 0이면 보드뷰에 안보이게, 1이면 보드뷰에 보이게. 보드뷰에는 안보이지만, 데이터에는 남아있음.
		String sql = "UPDATE board SET board_del='0' WHERE board_no=? AND mno=(SELECT mno FROM member WHERE mid=?)";
		
		try {
			pstmt = con.prepareStatement(sql);
//			pstmt.setInt(1, dto);
			
			//01.16	물음표 2개 처리 
			pstmt.setInt(1, dto.getNo());
			pstmt.setString(2, dto.getMid());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, pstmt, con);
		}
		return result;
	}
	
	public int edit(BoardDTO dto) {
		int result = 0;
		
		Connection con = DBConnection.getInstance().getConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE board SET board_title=?, board_content=? "
				+ "WHERE board_no=? AND mno=(SELECT mno FROM member WHERE mid=?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getNo());
			//pstmt.execute();
			
			//01.16
			pstmt.setString(4, dto.getMid());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, pstmt, con);
		}
		
		return result;
	}
	
//01.16
	public int totalCount() {
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) FROM boardview";
		int result = 0;
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	//01.19 조회수 추가
	public void countUp(int no, String mid) {
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(*) FROM visitcount "
				+ "WHERE board_no=? AND mno=(SELECT mno FROM member WHERE mid=?)";
	
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.setString(2, mid);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int result = rs.getInt(1);
				if(result == 0) { //0(읽은적 없다면) 이라면 
					realCountUp(no, mid);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		} close(null, pstmt, con);
	
	}

	private void realCountUp(int no, String mid) {
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO visitcount (board_no, mno) VALUES (?, (SELECT mno FROM member WHERE mid=?))";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.setString(2, mid);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, pstmt, con);
		}
		
		
	}

//01.22 댓글이 있다면 list 뽑아내기
	public List<CommentDTO> commentList(int no) {
		List<CommentDTO> list = new ArrayList<CommentDTO>();
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM commentview WHERE board_no=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CommentDTO dto = new CommentDTO();
				dto.setCno(rs.getInt(1));
				dto.setBoard_no(rs.getInt(2));
				dto.setCcomment(rs.getString(3));
				dto.setCdate(rs.getString(4));
				dto.setClike(rs.getInt(5));
				dto.setMno(rs.getInt(6));
				dto.setMid(rs.getString(7));
				dto.setMname(rs.getString(8));
				dto.setIp(Util.ipMasking(rs.getString(9)));
				
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
		return list;
	}
}
