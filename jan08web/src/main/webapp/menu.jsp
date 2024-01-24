<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 		<nav>
				<ul>
					<li onclick="url('./index')">홈</li>
					<li onclick="url('./board')">게시판</li>
					<li onclick="url('./qna')">문의게시판</li>
					<li onclick="url('./notice')">공지사항</li>
					<% if(session.getAttribute("mname") == null){ %>
					<li onclick="url('./login')">로그인</li>
					<!-- 강제로 주소창에 .login~ 적고 들어오는 방법까지 막아버리자 -->
					<%} else { %>
					<li onclick="url('./logout')">로그아웃</li>
					<%} %>
					<!-- 로그아웃은 servlet 만 존재하면 됨. jsp 없어도 됨 -->
					<li onclick="url('./info')">info</li>
				</ul>
			</nav> --%>

<!-- jstl 사용해서 -->
<nav>
	<ul>
		<li onclick="url('./index')">홈</li>
		<li onclick="url('./board')">게시판</li>
		<li onclick="url('./qna')">문의게시판</li>
		<li onclick="url('./notice')">공지사항</li>
		<!-- 01.17 부트스트렙 추가 -->
		<li onclick="url('./bootstrap')">부트스트랩</li>
		
		<li onclick="url('./notice')">info</li>
		<li onclick="url('./team')">팀</li>
		<c:choose>
			<c:when test="${sessionScope.mname eq null }">
				<li onclick="url('./login')">로그인</li>
			</c:when>
			<c:otherwise>
				<li onclick="url('./myInfo')">${sessionScope.mname }님</li>
				<li onclick="url('./logout')">로그아웃</li>
			</c:otherwise>
		</c:choose>

	</ul>
</nav>