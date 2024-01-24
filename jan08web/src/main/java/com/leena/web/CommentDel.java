package com.leena.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.leena.dao.CommentDAO;
import com.leena.dto.CommentDTO;
import com.leena.util.Util;

@WebServlet("/commentDel")
public class CommentDel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CommentDel() {
		super();
	}

//01.22 댓글 삭제 기능
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		// 세션 가져오기
		HttpSession session = request.getSession();
		// 로그인 했는지 확인, board_no 값 있어? , 댓글 번호 있어?
		if (session.getAttribute("mid") != null && Util.intCheck(request.getParameter("no"))
				&& Util.intCheck2(request.getParameter("cno"))) {
			// System.out.println(request.getParameter("no"));
			// System.out.println(request.getParameter("cno"));
			CommentDTO dto = new CommentDTO();
			dto.setMid((String) session.getAttribute("mid"));
			dto.setCno(Util.str2Int(request.getParameter("cno")));

			CommentDAO dao = new CommentDAO();
			int result = dao.commentDelete(dto);
			if (result == 1) {
				response.sendRedirect("./detail?no=" + request.getParameter("no"));
			} else {
				response.sendRedirect("./error.jsp");
			}
		} else {
			response.sendRedirect("./error.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		doGet(request, response);

		// 01.24 ajax

		HttpSession session = request.getSession();

		// get 에서 if 문 복붙
		// 로그인 했어?? cno 값 가져와
		int result = 0;
		if (session.getAttribute("mid") != null && Util.intCheck2(request.getParameter("no"))) {

			CommentDTO dto = new CommentDTO();
			dto.setMid((String) session.getAttribute("mid"));
			dto.setCno(Util.str2Int(request.getParameter("no")));

			CommentDAO dao = new CommentDAO();
			result = dao.commentDelete(dto);
		}

		PrintWriter pw = response.getWriter();
		pw.print(result);
	}

}
