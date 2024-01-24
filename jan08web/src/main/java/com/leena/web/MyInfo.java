package com.leena.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.leena.dao.MemberDAO;
import com.leena.dto.MemberDTO;

@WebServlet("/myInfo")
public class MyInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MyInfo() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		//01.16
		HttpSession session = request.getSession();
		if(session.getAttribute("mid") != null) {
			//mid 를 데이터베이스에서 질의
			MemberDTO dto = new MemberDTO();
			dto.setMid((String)session.getAttribute("mid"));
			
			//DTO 에 담아서
			MemberDAO dao = new MemberDAO();
			dto = dao.myInfo(dto);
			
			//myinfo.jsp 에 찍어주세요.
			request.setAttribute("myInfo", dto);
			
			//01.23 방문기록 만들기
			List<Map<String, Object>> readData = dao.readData(dto);
			request.setAttribute("readData", readData);
			
			
			RequestDispatcher rd = request.getRequestDispatcher("myInfo.jsp");
			rd.forward(request, response);
		} else {
			response.sendRedirect("./login");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doGet(request, response);
		
		//세션 만들기
		HttpSession session = request.getSession();
		
		//비번 수정
		MemberDTO dto = new MemberDTO();
		dto.setMpw(request.getParameter("newPW"));
		
		//01.16 세션값 보내기
		dto.setMid((String)session.getAttribute("mid"));
		
		MemberDAO dao = new MemberDAO();
		int result = dao.newpw(dto);
		
		System.out.println("수정 결과 : " + result);
		response.sendRedirect("./board");
		
	}

}
