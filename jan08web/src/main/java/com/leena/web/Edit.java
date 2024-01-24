package com.leena.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.leena.dao.BoardDAO;
import com.leena.dto.BoardDTO;
import com.leena.util.Util;

@WebServlet("/edit")
public class Edit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Edit() {
		super();
	}

	// doget에서 서블릿 만들기 -> jsp 에서 doget 에서 만든 서블릿(${}) 붙여주기 -> dopost 에서 db랑 연동 만들어주기

	protected void doGet(HttpServletRequest request, HttpServletResponse response) //서블릿 만들기
			throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());

		//01.16 세션, 조건문 추가
		HttpSession session = request.getSession();
		System.out.println(session.getAttribute("mid") != null);
		System.out.println("--------------");
		if(session.getAttribute("mid") != null) {
			//세션이 있을 때 = 정상 작업
			System.out.println("??????");
			
			// no 가져오기
			int no = Util.str2Int(request.getParameter("no"));
			// DAO에 질의하기
			BoardDAO dao = new BoardDAO();
			BoardDTO dto = dao.detail(no);
			
			//01.16 세션mid 랑 db 에서 불러온 mid랑 같아?
//			System.out.println(dto.getMid().equals(session.getAttribute("mid")));
//			System.out.println(session.getAttribute("mid").equals(dto.getMid()));
//			System.out.println(session.getAttribute("mid") == dto.getMid());
//			System.out.println(((String)session.getAttribute("mid")).equals(dto.getMid()));
//			if(true)~~ 로 값 비교해 보기
			
			System.out.println(session.getAttribute("mid").equals(dto.getMid()));
			System.out.println(">>>>>>>");
			
			if(session.getAttribute("mid").equals(dto.getMid())) {
				// jsp 에 보내기
				request.setAttribute("edit", dto);
				RequestDispatcher rd = request.getRequestDispatcher("edit.jsp");
				rd.forward(request, response);
			} else {
				response.sendRedirect("./error.jsp");
			}
		} else {
			response.sendRedirect("./login?login=no login");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// doGet(request, response);
		request.setCharacterEncoding("UTF-8");
		
		//01.16 세션 만들기
		HttpSession session = request.getSession();

		if (request.getParameter("title") != null && request.getParameter("content") != null
				&& Util.intCheck(request.getParameter("no")) && /*01.16 세션 조건 추가*/session.getAttribute("mid") != null) {
			// 진짜 수정
			BoardDTO dto = new BoardDTO();
			dto.setContent(request.getParameter("content"));
			dto.setTitle(request.getParameter("title"));
			dto.setNo(Util.str2Int(request.getParameter("no")));
			//01.16 세션값 보내기
			dto.setMid((String)session.getAttribute("mid"));

			BoardDAO dao = new BoardDAO();
			int result = dao.edit(dto);
			System.out.println("수정결과 : " + result);

			response.sendRedirect("./edit?no=" + request.getParameter("no"));
		} else {
			// error
			response.sendRedirect("./error.jsp");

		}
//		String title = request.getParameter("title");
//		String content = request.getParameter("content");
//		String no = request.getParameter("no");
//
//		System.out.println(title);
//		System.out.println(content);
//		System.out.println(no);
	}

}
