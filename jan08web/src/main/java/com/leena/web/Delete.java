package com.leena.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.leena.dao.BoardDAO;
import com.leena.dto.BoardDTO;
import com.leena.util.Util;

@WebServlet("/delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Delete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//		글 삭제하기
		//string 들어오면 자동으로 int로 바꾸는 util 만들기
		
		//01.16 세션 추가
		HttpSession session = request.getSession();
		// no가 숫자야? + (01.16)로그인 했어?
		if(Util.intCheck(request.getParameter("no")) && session.getAttribute("mid") != null) {
			//숫자 맞아 -> 삭제 진행 -> board 로 이동
			System.out.println("숫자입니다");
			
			//번호 잡기
			int no = Util.str2Int(request.getParameter("no"));
//			int no = request.getParameter("no");
			
			//DAO 에게 일 시키기
			BoardDAO dao = new BoardDAO();
			
			//01.16 mid 까지 같이 가져와야 함, board_no 랑 mid 랑 같이 있는 클래스는 boardDTO
			BoardDTO dto = new BoardDTO();
			dto.setNo(no);
			dto.setMid((String)session.getAttribute("mid"));
//			int result = dao.delete(no);
			int result = dao.delete(dto);
			
			
			//잘 삭제되었는지 값 받기
			System.out.println("삭제여부 :"+result);
			if(result == 1) {
				//값이 1이면 어디로
				response.sendRedirect("./board");
			} else {
			//값이 0이면 어디로
			response.sendRedirect("./error.jsp");
			} 
			
		} else {
			//숫자가 아니야 -> 에러 표시
			System.out.println("문자입니다");
			response.sendRedirect("./error.jsp");
		}
		}		

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		doGet(request, response);
	}

}
