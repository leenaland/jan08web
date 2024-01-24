
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<-- 이 선언문 써줘야 jstl 쓸 수 있음 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>

<link href="./css/menu.css" rel="stylesheet" />
<link href="./css/index.css" rel="stylesheet" />
<link href="./css/board.css" rel="stylesheet" />

<script type="text/javascript" src="./js/menu.js"></script>

</head>
<body>

	<%-- 서블릿이 준 값 : <%=lists %> --%>

	<div class="container">
		<header>
			<%-- # <%@ include file="" %> <jsp:include> <c:import>  차이 참고 사이트 확인 --%>
			<!-- 먼저 menu.jsp 가져온 다음에 jstl 실행한다 -->
			<%@ include file="menu.jsp"%>
		</header>
		<div class="main">
			<div class="mainStyle">

				<div style="width: 900px; margin: 0 auto; padding-top: 10px">
					<article>

						<%-- 					for문 연습해보기
					<c:forEach items="${list}" var="e" varStatus="s">
					${e.no }/${s.first }/${s.last }/${s.index }/${s.count }<br>
					</c:forEach> --%>

					</article>
					<article>

						<%-- 	fn 이용해서 자료형 데이터 길이 뽑아내기 ${fn: length(list) }, if() { } else if {} 와
					비슷한 형식 --%>
						<!-- list 길이가 0보다 크면 출력 -->
						<c:choose>
							<c:when test="${fn:length(list) gt 0 }">
								<table>
									<tr>
										<th>번호</th>
										<th>제목</th>
										<th>글쓴이</th>
										<th>날짜</th>
										<th>읽음</th>
									</tr>
									<c:forEach items="${list}" var="row">
										<!-- list 는 dao 에서 가져오고 row는 dto 에서 가져온 변수명 -->
										<tr>
											<td>${row.no}</td>
											<!-- 01.19 <span>${row.comment }</span>추가 -->
											<%-- 	<td class="title"><a href="./detail?no=${row.no }">${row.title } --%>
										<!-- 01.23 게시판으로 클릭시 1페이지로 가는 오류 수정 -->
											<td class="title"><a href="./detail?page=${page}&no=${row.no }">${row.title }
													<c:if test="${row.comment ne 0 }">
														&ensp;
														<span>${row.comment }</span>
													</c:if>
											</a></td>

											<td>${row.write}</td>
											<td>${row.date}</td>
											<td>${row.count}</td>
										</tr>

									</c:forEach>
								</table>
								전체 글 수 : ${totalCount } 개 글이 있습니다.<br>
								<!--  페이지 수 : ${totalCount / 10 } 이렇게 쓰면 계산이 안됨 , jstl 로 써서 java 문 입력하자-->
								전체 페이지 수 : <c:set var="totalPage" value="${totalCount / 10 }" />
								<!-- 파스넘버: 소수점을 정수로 처리 -->
								<fmt:parseNumber integerOnly="true" value="${totalPage }"
									var="totalPage" />
								<!-- 나머지 값이 있다면 +1 시켜줘 -->
								<c:if test="${totalCount % 10 gt 0 }">
									<!-- 나머지값이 있다면 -->
									<c:set var="totalPage" value="${totalPage + 1 }" />
								</c:if>

								<c:out value="${totalPage }" />
								<!-- 01.17 한 화면에 10개 페이지 까지 보이게 하기 -->
								/ startPage : <c:set var="startPage" value="1" />
								<c:if test="${page gt 10}">
									<c:set var="startPage" value="${page-(page%10)+1 }" />
								</c:if>
								${startPage }
								/ endPage : <c:set var="endPage" value="${startPage + 9 }" />${endPage }
								<c:if test="${endPage gt totalPage }">

									<c:set var="endPage" value="${totalPage }" />
								</c:if>

								<!-- 파라미터에 있는 page 라는 애 가져오기 -->
								<%-- / page: ${param.page } --%>
								<!-- 그냥 board 로 들어가면 파라미터에 page 안나와서 못 씀 -->
								/ page : ${page }
								
								<!-- 01.16 페이징, 01.17 페이지 이동 버튼 추가 -->
								<div class="paging">
									<button onclick="paging(1)">🔚</button>
									<button
										<c:if test="${startPage eq 1}">disabled="disabled"</c:if>
										onclick="paging(${page - (page%10)})">🔙</button>

									<c:forEach begin="${startPage }" end="${endPage }" var="p">
										<!-- 1번 클릭하면 1페이지 가고 
								2번 클릭하면 2페이지 가고..
								'' 없어서 ${p} 는 숫자로 취급함-->
										<button <c:if test="${page eq p }"> class="currentBtn"</c:if>
											onclick="paging(${p})">${p }</button>
									</c:forEach>

									<button
										<c:if test="${page + 10 gt totalPage}">disabled="disabled"</c:if>
										onclick="paging(${(page/10)*10+11})">➡</button>

									<button onclick="paging(${totalPage})">⏩</button>
								</div>

							</c:when>
							<c:otherwise>
								<h1>no value to print out</h1>
							</c:otherwise>
						</c:choose>

						<!-- 로그인 한 사람만 글쓰기 할 수 있도록 -->
						<c:if test="${sessionScope.mname ne null }">
							<button onclick="url('./write')">글쓰기</button>
						</c:if>

						<%-- 				<button onclick="url('./write')">글쓰기</button>
						${sessionScope.mname }님 반갑습니다. --%>
						<!-- ${sessionScope.mname } 이건 외워야 함 -->

					</article>
					<article>
						<%-- <fmt:requestEncoding value="UTF-8" />
						<!-- 한글 인코딩 맞추기 -->
						<fmt:setLocale value="ko_kr" />
						<fmt:formatNumber value="3.14" type="currency" />
						통화, 시간 등 맞추기
						<fmt:parseNumber value="3.14" integerOnly="true" />
						<!-- 정수만 출력 -->

						<c:set var="nowDate" value="<%=new Date()%>" />
						${nowDate} <br>

						<fmt:formatDate type="time" value="${nowDate }" />
						<br>
						<fmt:formatDate type="date" value="${nowDate }" />
						<br>
						<fmt:formatDate type="both" value="${nowDate }" />
						<br>
						<fmt:formatDate type="both" dateStyle="short" timeStyle="short"
							value="${nowDate }" />
						<br>
						<fmt:formatDate type="both" dateStyle="medium" timeStyle="medium"
							value="${nowDate }" />
						<br>
						<fmt:formatDate type="both" dateStyle="long" timeStyle="long"
							value="${nowDate }" />
						<br>
						<fmt:formatDate type="both" pattern="yyyy-MM-dd"
							value="${nowDate }" />
						<br> --%>

					</article>
				</div>
			</div>
			<footer>
				java -> servlet -> jsp(jsp, jstl, el) -> thymeleaf <br>
				<c:import url="footer.jsp" />
			</footer>
		</div>
		<script type="text/javascript">
		function paging(no){
			location.href="./board?page="+no;
		}
		</script>
</body>
</html>