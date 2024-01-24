package com.leena.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leena.dao.BoardDAO;
import com.leena.dao.LogDAO;
import com.leena.dto.BoardDTO;
import com.leena.util.Util;

@WebServlet("/board") //BoardList -> board
public class BoardList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardList() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//DAO 랑 연결
		
		//01.16 페이징
		int page = 1;
		if(request.getParameter("page") != null) {
		 page = Util.str2Int(request.getParameter("page"));
		}
		
		//01.16 페이징 (page) 추가
		BoardDAO dao = new BoardDAO();
		List<BoardDTO> list = dao.boardList(page);
		int totalCount = dao.totalCount();
		
		request.setAttribute("list", list);
		request.setAttribute("totalCount", totalCount);
		//01.17 페이지 세션 만들기
		request.setAttribute("page", page);
		
		//01.23 ip, log
		Map<String, Object>	log = new HashMap<String, Object>();
		log.put("ip", Util.getIP(request));
		log.put("url", "./board");
		log.put("data", "page="+page);
		LogDAO logDAO = new LogDAO();
		logDAO.write(log);
		
		
		RequestDispatcher rd = request.getRequestDispatcher("board.jsp"); //Dispatcher : url 고정, 화면 내용만 바꾼다.
		rd.forward(request, response); // 내가 간 곳은 .jsp 인데, url 은 board 로 고정, 화면은 .jsp 로 바꾼다
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
	}

}
