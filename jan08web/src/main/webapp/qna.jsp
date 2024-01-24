<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- jstl 선언문 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>QNA</title>

<link href="./css/index.css" rel="stylesheet" />
<link href="./css/menu.css" rel="stylesheet" />
<script type="text/javascript" src="./js/menu.js"></script>

</head>
<body>

	<div class="container">
		<header>
			<jsp:include page="menu.jsp"></jsp:include>
			<!-- jsp:은 출력 결과만 화면에 나온다 -->
		</header>
		<div class="main">
			<div>
				<article>
					<h1>문의게시판</h1>
					<ul>
						<li>ㅖ인가 ㅔ 인가<li>	
						<li>각각 게시판 서블릿, jsp</li>
					</ul>
				</article>
			</div>
		</div>
					<footer>
			<c:import url="footer.jsp"/>
			</footer>
</body>
</html>