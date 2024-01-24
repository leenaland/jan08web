<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- jstl 선언문 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- (01.23 내 방문기록 보기 중, 날짜 형식 바꾸기)
화면에 찍어주는 출력형식 바꿔주기 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>myInfo</title>
<link href="./css/menu.css" rel="stylesheet" />
<link href="./css/index.css" rel="stylesheet" />
<script type="text/javascript" src="./js/menu.js"></script>

</head>
<body>
	<%-- //01.16 로그인 검사하기
<%
if(session.getAttribute("mid") != null) {
	response.sendRedirect("./login");
}
%> --%>
	<div class="container">
		<header>
			<jsp:include page="menu.jsp"></jsp:include>
		</header>
		<div class="main">
			<div class="mainStyle">
				<article>
					내 정보 보기 ${myInfo.mname } / ${myInfo.mid } /

					<div>
						<form action="./myInfo" method="post" onsubmit="return check()">
							<input type="password" name="newPW" id="newPW"
								placeholder="변경할 암호를 입력하세요">
							<button type="submit">수정하기</button>
						</form>
					</div>
				</article>

				<!-- 01.23 내 방문기록 만들기 -->
				<article>
					<h2>방문흔적 만들기</h2>
					<table>
						<thead>
							<tr>
								<td>글제목</td>
								<td>읽은날짜</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${readData }" var="d">
								<tr>
									<td onclick="location.href='./detail?no=${d.board_no}'" >
									${d.board_title }
									</td>
									<td>
									<fmt:parseDate value="${d.vdate }" var="date" pattern="yyyy-MM-dd HH:mm:ss" />
									<fmt:formatDate value="${date }" pattern="yyyy년 MM월 dd일 HH시 mm분 ss초" />
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</article>

			</div>
		</div>
	</div>

	<footer>
		<c:import url="footer.jsp" />
	</footer>
	<script type="text/javascript">
		function check() {
			//var pw = document.getElementById("newPW");
			var pw = document.querySelector("#newPW");
			if (pw.value.length < 5) {
				alert("암호는 5글자 이상이어야 합니다.");
				return false;
			}
		}
	</script>
</body>
</html>