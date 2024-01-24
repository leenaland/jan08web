package com.leena.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.leena.dao.BoardDAO;
import com.leena.dao.LogDAO;
import com.leena.dto.BoardDTO;
import com.leena.dto.CommentDTO;
import com.leena.util.Util;

@WebServlet("/detail")
public class Detail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Detail() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		//오는 no 잡기
//		int no = Integer.parseInt(request.getParameter("no")); //전에는 string no 를 던졌는데, int로 쫌 더 안정화 시켜준다
		int no = Util.str2Int(request.getParameter("no"));
		
		// 01.23 ip 로그 기록(무슨 페이지 봤는지) 남기기
		// logWrite 를 Abstract로 옮겼어. LOGDAO 객체 필요없어짐
//		LogDAO log = new LogDAO();
//		log.logWrite(Util.getIP(request),"./detail","no="+no);
		
		//데이터베이스에 질의하기
		BoardDAO dao = new BoardDAO();
		//01.23 위에 log.logWrite abstract 로 옮겨줘서 dao 로 불러온거임
		dao.logWrite(Util.getIP(request),"./detail","no="+no);
		
		
		//01.19 로그인한 회원이라면 읽음수 올리기
		HttpSession session = request.getSession();
		if(session.getAttribute("mid") != null) {
			//bno, mno
			dao.countUp(no, (String) session.getAttribute("mid"));
		}
		//01.19 로그인 안한 사람이라면 아래로 가기
		
		BoardDTO dto = dao.detail(no); //dto가 get, set 할 수 있이니까 dto 한테 넘겨줘
		
		System.out.println(dto.getTitle()); //0번 글이 없다면 
		System.out.println(dto.getContent() == null);
		
		if(no == 0 || dto.getContent() == null) {
			//no가 1 이거나 null(db 에 no 없는 경우) 이면 에러로
			response.sendRedirect("error.jsp");
		} else {
			//정상출력
			//내용 가져오기
			request.setAttribute("detail", dto);
			
			//01.22 댓글이 있다면 list 뽑아내기
			List<CommentDTO> commentList = dao.commentList(no);
			
			if(commentList.size()>0) {
				request.setAttribute("commentList", commentList);
			} 
			/*request.setAttribute("commentList", commentList);
			 * 서블릿에서 JSP로 데이터를 전달하는 데 사용되는 메서드
			 * 이를 통해 서버 측에서 생성된 데이터를 클라이언트(브라우저)로 전달하여 화면에 표시할 수 있습니
			 * "commentList" 는속성의 이름이며, JSP에서 이 이름을 사용하여 데이터에 접근할 수 있다.
			 * commentList: 이 부분은 전달하려는 데이터, 여기서는 List<CommentDTO> 객체인 commentList입니다.
			 * 즉, 이 코드는 commentList라는 이름으로 commentList라는 데이터를 JSP에 전달한다는 의미입니다.
			 * JSP에서는 이 데이터를 request.getAttribute("commentList")를 통해 사용할 수 있습니다.
			 * 
			*/
			//리퀘스트디스패쳐 호출하기
			RequestDispatcher rd = request.getRequestDispatcher("detail.jsp");
			rd.forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		doGet(request, response);
	}

}
