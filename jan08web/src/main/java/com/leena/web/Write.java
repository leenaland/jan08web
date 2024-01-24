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

@WebServlet("/write")
public class Write extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Write() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());

//		로그인 안했는데, 강제로 주소창에 .write~ 적고 들어오는 방법까지 막아버리자
		HttpSession session = request.getSession();

		// mname 이라는 세션이 없다면 로그인 화면으로 가라
		if (session.getAttribute("mname") == null) {
			response.sendRedirect("./login"); // url, 화면 모두 변경
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("write.jsp");// url 고정, 화면만 변경
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		doGet(request, response);
//		jsp 에서 name 으로 만들어준거 java로 가져오기 위해 이름 똑같이 만들어줘

		// 한글처리
		request.setCharacterEncoding("UTF-8");
		// 세션에 들어있는 mid 가져오기위해 세션 만들어주기
		HttpSession session = request.getSession();
		// if문으로 로그인 되어있는(=세션이 있는) 사람만 아래 로직 수행할 수 있도록 변경
		if (session.getAttribute("mid") == null || session.getAttribute("mname") == null) {
			// 로그인 하지 않았다면 login.jsp 로 가게 하겠다
			response.sendRedirect("./login?login=nologin");
		} else {
			// 로그인 했다면 아래 로직 수행하겠다
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			//01.23 제목에 html 태그가 들어오면 특수기호로 처리하기
			title = Util.removeTag(title);

			System.out.println(title);
			System.out.println(content); /* 웹에서 글 작성하고 저장하기 하면 콘솔창에 나옴 */

			// DAO 에 write 메소드 만들기
			BoardDTO dto = new BoardDTO();
			dto.setTitle(title);
			dto.setContent(content);
			dto.setMid(content);
			dto.setMid((String) session.getAttribute("mid")); // 세션에서 mid 가져온다
			// session.getAttribute으로 얻어오는 것은 모두 object 임
			//01.23 제목에 글쓰니 ip 추가해주기
			dto.setIp(Util.getIP(request));

			BoardDAO dao = new BoardDAO();
			int result = dao.write(dto);
			System.out.println("글쓰기 결과는: " + result);

			// 페이지 이동하기 : url 값과 화면 모두 이동하기
			// 여기서 디스패쳐 쓰면 : 화면은 .jsp , url 은 servlet
			if (result == 1) {
				response.sendRedirect("./board");
			} else {
				response.sendRedirect("./error.jsp");
			}
		}
	}
}
