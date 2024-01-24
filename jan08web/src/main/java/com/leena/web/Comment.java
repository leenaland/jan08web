package com.leena.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.leena.dao.CommentDAO;
import com.leena.dto.CommentDTO;
import com.leena.util.Util;

@WebServlet("/comment")
public class Comment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Comment() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		//한글 처리
		request.setCharacterEncoding("UTF-8");
		
		//로그인 되어 있는지, 댓글이랑 글번호가 있는지 확인 하고 댓글 보이게.
//		if(session.getAttribute("mname") == null) {
//			
//		} else {
//			
//		}
		//오는 값 받기
		String commentcontent = request.getParameter("commentcontent");
		String bno = request.getParameter("bno"); //글번호
		System.out.println(commentcontent + ":" + bno);
		
		//01.23 댓글이 html 태그가 들어오면 특수기호로 처리하기
		commentcontent = Util.removeTag(commentcontent);
		//01.23 댓글에 엔터 안먹는것 수정(/r, /n, /nr 을 엔터로 변경)
		commentcontent = Util.addBR(commentcontent);
		
		//서버에 저장하기
		HttpSession session = request.getSession();
		
		CommentDTO dto = new CommentDTO();
		dto.setCcomment(commentcontent);
		dto.setBoard_no(Util.str2Int(bno));
		dto.setMid((String) session.getAttribute("mid"));
		dto.setIp(Util.getIP(request));
		
		CommentDAO dao = new CommentDAO();
		int result = dao.commentWrite(dto);
		System.out.println("처리결과:"+result);
		
		//이동시키기
		if(result == 1) {
			response.sendRedirect("./detail?no="+bno);
		} else {
			response.sendRedirect("./error.jsp");
		}
	}

}
