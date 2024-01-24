<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- jstl 선언문 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login</title>
<link href="./css/menu.css" rel="stylesheet" />
<link href="./css/index.css" rel="stylesheet" />
<script type="text/javascript" src="./js/menu.js"></script>

<style type="text/css">
.login {
	margin : 0 auto;
	width: 300px;
	height: 500px;
	background-color: pink;
	text-align: center;
	padding : 10px;
}
.login input {
	width: 100%;
	height : 30px;
	text-align : center;
	box-sizing : border-box;
	margin-bottom : 8px;
}
</style>

<script type="text/javascript">
function err(){
	let errBox = document.querySelector("#errorMSG"); /* #errorMSG얘를 찾아서 아래 문구로 바꿔줘 */
	errBox.innerHTML = "<marquee>올바른 id 와 pw 를 입력하세요.</marquee>"; 
	//errBox.innerText = "올바른 id 와 pw 를 입력하세요";
	errBox.style.color = 'red';
	errBox.style.fontSize = "10pt";
}


</script>

</head>
<body>
	<div class="container">
		<header>
			<jsp:include page="menu.jsp"></jsp:include>
		</header>
		<div class="main">
			<div class="mainStyle">
				<article>

					<div class="login">
					<h1>로그인</h1>
					
					<!-- 에러에 파라미터 값 잡아줘 -->
					<%-- ${param.error } --%>
					<c:if test="${param.error ne null }"><!-- 만약 에러문구가 있다면 -->
						<script type = "text/javascript">
						alert("올바른 암호와 아이디를 입력하세요");
						</script>
						</c:if>
						
						<form action="./login" method="post">
							<input type="text" name="id" placeholder="아이디를 입력하세요"> <br>
							<input type="password" name="pw" placeholder="암호를 입력하세요"> <br>
							<button type="reset">지우기</button>
							<button type="submit">로그인</button>
							
							<div id="errorMSG"></div>
							
							<!-- 01.17 회원가입 -->
							<a href="./join">회원가입</a>
							
						</form>
					</div>
				</article>
			</div>
		</div>
	</div>
	
	<c:if test = "${param.error ne null }">
		<script type = "text/javascript">
			err();
		</script>
		</c:if>
					<footer>
			<c:import url="footer.jsp"/>
			</footer>
</body>
</html>