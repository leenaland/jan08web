<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- jstl 선언문 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EDIT</title>

<!-- summernote.org 에서 받아옴 -->
<link href="./css/index.css" rel="stylesheet" />
<link href="./css/menu.css" rel="stylesheet" />
<script type="text/javascript" src="./js/menu.js"></script>

<!-- include libraries(jQuery, bootstrap) -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>

<style type="text/css">
#title{
	width: 100%;
	height: 30px;
	margin-bottom: 10px;
}





</style>

</head>
<body>
	<div class="container">
		<header>
			<%@ include file="menu.jsp"%>
		</header>
		<div class="main">
			<div class="mainStyle">
				<article>
					<h1>수정하기</h1>
					<div class= "writeFORM">
						<form action="./write" method="post">
							<input type="text" id="title" name="title"> <!--  -->
							<textarea id="summernote" name="content"></textarea>
							<textarea name="content"></textarea>
							<button type="submit">저장하기</button>
							<!-- id는 html,css,js 가 가져가고
							name 은 java 가 가져가는 것
							자가가 "title" 이라는 이름의 것을 가져가서 
							글 쓰면 저장하도록 만들어줘-->
						</form>
					</div>
				</article>
			</div>
		</div>
	</div>
	<!-- summernote.org 에서 받아옴 
	id = "summernote"를 받아서 "summernote"라는 모드로 만들고, 높이는 500으로 해줘-->
	  <script>
    $(document).ready(function() {
        $('#summernote').summernote({
        	height: 500
        });
    });
  </script>
  			<footer>
			<c:import url="footer.jsp"/>
			</footer>
  
</body>
</html>