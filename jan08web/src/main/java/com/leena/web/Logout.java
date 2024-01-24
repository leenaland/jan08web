package com.leena.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Logout() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("GET 으로 들어왔어요");
		/*
		 * 세션 쿠키 세션 : 서버에 저장 로그인 정보 자바 쿠키 : 클라이언트에 저장(브라우저) 쇼핑정보, 장바구니, 방문내역,.. 스크립트
		 */
		// 세션 종료
		HttpSession session = request.getSession();
		if (session.getAttribute("mname") != null) {
//			세션 유효시간 : 로그인 유지 시간
//			session.setMaxInactiveInterval(3600); 세션 시간 연장.ex) 로그인 상태 유지하시겠습니까?
			System.out.println("세션 유효시간 : " + session.getMaxInactiveInterval());//1800 : 30분
			System.out.println("mname : " + session.getAttribute("mname"));
			session.removeAttribute("mname");
		}
		if (session.getAttribute("mid") != null) {
			System.out.println("mid : " + session.getAttribute("mid"));
			session.removeAttribute("mid");
		}
		session.invalidate();
		// mname, mid 각각 세션 종료(removeAttribute)시켜줄 필요 없이 한 번에 처리 가능
		// invalidate 는 세션 자체를 무효화, 제거하고
		// removeAttribute 는 현재 세션에서 특정 key-value 만 제거한다
		// removeAttribute 로 키만 제거를 하면 httpSession 인스턴스다 남아있어
		// invalidate 해주는 것이 좋다.

		// login 페이지로 보내기
		RequestDispatcher rd = request.getRequestDispatcher("logout.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// doGet(request, response);
		System.out.println("POST 으로 들어왔어요");
	}

}
