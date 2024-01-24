<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- jstl 선언문 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>INDEX</title>

<link href="./css/index.css" rel="stylesheet" />
<link href="./css/menu.css" rel="stylesheet" />
<script type="text/javascript" src="./js/menu.js"></script>

</head>
<body>
	<%-- 	<h1>index 입니다</h1>
	<%
	//DBConnection db = new DBConnection() // 안됨 : 내부 다 private 이야
	DBConnection db = DBConnection.getInstance();
	db.getConnection(); //으로 써야함
	
	DBConnection.getInstance().getConnection(); //으로 써도 됨
	%>
	<a href="./board">board</a> --%>

	<div class="container">
		<header>
		<%-- # <%@ include file="" %> <jsp:include> <c:import>  차이 참고 사이트 확인 --%>
			<!-- 메뉴.jsp 가 처리 된 상태에서 붙여넣기 -->	
			<jsp:include page="menu.jsp"></jsp:include>
			<!-- jsp:은 출력 결과만 화면에 나온다 -->
		</header>
		<div class="main">
			<div>
				<article>
					<c:set var="number" value="105"></c:set>
					<!-- 변수 선언, 변수 값 변화 -->
					<!-- number 라는 변수 만들고 105라는 값을 넣었어 -->
					${number}
					<!-- 출력해 -->
					<c:out value="${number}" />

					<c:forEach begin="1" end="9" var="row" step="2">
					 2 x ${row} = ${2 * row}<br>
					</c:forEach>

					<c:if test="${number eq 105 }">
					number 는 105입니다<br>
						<%-- 				<c:if test="${number > 105 }"> 부등호가 꺽쇠랑 비슷해서 에러 가능성 있음
					따라서 기호로 안쓰로 축약어로 쓰자
					eq : ==
					ne : !=
					lt : 〈 
					gt : 〉
					le : 〈=
					ge : 〉=
					&&
					\\
					empty
					not empty --%>
					</c:if>

					<hr>
					1~45까지 짝수만 출력하세요.
					<c:forEach begin="1" end="45" var="row">
						<c:if test="${row % 2 eq 0}">
					${row }
					</c:if>
					</c:forEach>
				</article>

				<article>
					<%-- 	<c:import url="menu.jsp"/><br> --%>
					<c:forEach begin="1" end="10" var="row" varStatus="s">
					${s.begin }/${s.end }/스텝				
					
					</c:forEach>
				</article>

			</div>
		<footer>
			<c:import url="footer.jsp"/>
		</footer>
		</div>
		
</body>
</html>