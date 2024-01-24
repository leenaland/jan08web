<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- jstl 선언문 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>톺아보기</title>

<link href="./css/index.css" rel="stylesheet" />
<link href="./css/menu.css" rel="stylesheet" />
<link href="./css/detail.css" rel="stylesheet" />
<script type="text/javascript" src="./js/menu.js"></script>

<!-- 01.22 댓글창, jquery 가져오기 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"
	integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g=="
	crossorigin="anonymous" referrerpolicy="no-referrer"></script>

<!-- 01.24 xeicon 에서 복붙함. 이거 있으면 여기 아이콘 쓸 수 있음 -->
<link rel="stylesheet"
	href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">

<!-- 01.22 jquery 로 댓글창 만들기 -->
<script type="text/javascript">
	$(document).ready(function() {

		//01.24 댓글 수정하기
		$(".commentUpdate").click(function() {
			alert("???");
			if (confirm("수정하시겠습니까?")){
				// cno값 가져오기, ccomment 값 가져오기
				let cno = $(this).siblings("cno").val();
				let comment = $(this).parent(".chead").next.text();
				alert(cno+":"+comment);
			}
		});

		//01.24 댓글 삭제버튼 눌렀을때 현페이지 그대로 유지
		$(".commentDelete").click(function() {//id로 지정하면 댓글 하나에만 적용됨,,
			alert("댓글 삭제버튼을 눌렀습니다.");
			//문서구조 : 부모객체 찾아가기 : this = 나 자신, this() : 내 생성자, super(): 부모 생성자
			//			$(this).parent(".cname").css('color','green');//나의 부모(cname)의 글자색을 초록으로 바꾼다
			//			$(this).parent(".cname").text("변경가능");//글씨를 변경
			//			let text = $(this).parent(".cname").text();//부모 글자 내용 가져오기. 비교 : val(),text(),html()
			//			alert(text);
			//부모요소 아래 자식요소 찾기 children()
			//			let cno = &(this).parent(".cname").children(".cno").val();//나의 부모를 찾고, 그 부모의 자식을 찾아간다
			//부모 이름 없어도 잡힘
			//			let cno = &(this).parent().children(".cno").val();//나의 부모를 찾고, 그 부모의 자식을 찾아간다
			//형제요소 찾기 .siblings() 
			//.prev() 바로 이전 형제(태그 하나에 있는 애들은 모두 형제)
			//.next() 바로 다음 형제(태그 하나에 있는 애들은 모두 형제)
			//			let cno = $(this).siblings("cno").val();//내 형제 요소 중에 이 이름 가지고 있는애 찾아
			//			let cno = $(this).prev().val();//나 바로 앞에 있는 형제 찾아가기
			//alert(cno);

			if (confirm("삭제하시겠습니까?")) {

				// 01.24 ajax
				let cno = $(this).prev().val();
				//$(this).closest(".comment").hide();
				let point = $(this).closest(".comment");
				$.ajax({
					url : './commentDel', //주소
					type : 'post', //get, post
					dataType : 'text', //수신타입 json
					data : {
						no : cno
					}, //보낼 값
					success : function(result) { // 0, 1 받아옴
						alert("서버에서 온 값 :" + result);
						if (result == 1) {
							//정상 삭제 : this의 부모(.comment)를 찾아서 remove 하겠다.
							point.remove(); // 영구 삭제
						} else {
							alert("삭제할 수 없습니다. 과리자에게 문의")
						}
					},
					error : function(request, status, error) {//통신오류 : 위에 url, type, datatype,.. 작성한 것 중 오류 있어서
						elrt("문제가 발생");
					}
				});//end ajax

			}
		});

		//01.24 댓글쓰기 버튼을 누르면 댓글창 나오게 하기
		$(".comment-write").hide();//하이드 : 처음 로딩 때는 숨겨
		//	$(".comment-write").show(3000);//3초, 지연시간(천천히 보여준다)
		$(".xi-comment-o").click(function() { //클래스를 틀릭하면 작동
			$(".xi-comment-o").hide();//댓글쓰기창 클릭하면 버튼은 사라지게
			//$(".comment-write").show();
			$(".comment-write").slideToggle('slow');
		});

		//	alert("준비되었습니다.")
		/* id 로 만든 애 구동시키기 */
		$("#comment-btn").click(function() {
			let content = $("#commentcontent").val();
			let bno = $
			{
				detail.no
			}
			; // 글 번호 추적
			alert("content : " + content + " no : " + bno);
			//가상 form 만들기(동적생성) : 가짜의 html 코드를 만들어서 전송 시킨다
			//전송 -> content 가 5글자 이상인 경우 실행하겠다
			if (content.length < 5) {
				alert("댓글은 다섯글자 이상 적어줘");
				$("#commentcontent").focus();
				//return false;
			} else {
				let form = $('<form></form>');
				form.attr('name', 'form');
				form.attr('method', 'post');
				form.attr('action', './comment');
				// {type:'hidden', name:'commentcontent', value:content}) : json 방식
				// json 방식 : 2종 시스템(서로 다른 시스템)

				form.append($('<input/>', {
					type : 'hidden',
					name : 'commentcontent',
					value : content
				}));
				form.append($('<input/>', {
					type : 'hidden',
					name : 'bno',
					value : bno
				}));

				form.appendTo("body");
				form.submit();

				/* 			//<form action="./comment" method="post" name="form"> 이라고 작성하는 것을 아래로 변형 한 것
				 // html 에 form 태그 직접 안쓰려고
				 let form = document.createElement('form');//여기서 'form' 은 태그명
				 form.name='form'; //여기서 'form' 은 form 태그 이름
				 form.method='post';
				 form.action='./comment';
				 //붙일거 만들기
				 let text = document.createElement('input');
				 text.setAttribute("type", "hidden");
				 text.setAttribute("name", "commentcontent");
				 text.setAttribute("value", content);
				 //붙일거 만들기
				 let no = document.createElement('input');
				 no.setAttribute("type", "hidden");
				 no.setAttribute("name", "bno");
				 no.setAttribute("value", ${detail.no }); //변수명 no 라고 써도 됨
				 //form 에다가 붙이기
				 form.appendChild(text);
				 form.appendChild(no);
				 //전송하기
				 document.body.appendChild(form);
				 form.submit(); */
			}
		});
	});
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
					<div class="datailDIV">
						<div class="detailTITLE">${detail.title }</div>
						<div class="detailWRITECOUNT">
							<div class="detailWRITE">
								${detail.write } / ${detail.mid } / ${sessionScope.mid }

								<c:if
									test="${sessionScope.mname ne null && detail.mid eq sessionScope.mid }">
									<!-- 이미지 넣기, alt 는 이미지 안떴을 때 나오는 text -->
									<img onclick="del()" alt="삭제" src="./image/delete.png">
									<img onclick="edit()" alt="수정" src="./image/edit.png">
								</c:if>

							</div>
							<div class="detailCOUNT">${detail.ip }/${detail.count }</div>
						</div>
						<div class="detailCONTENT">${detail.content }</div>
					</div>

					<!-- 01.22 로그인했다면 댓글창 보여줘 -->
					<c:if test="${sessionScope.mid ne null }">

						<!-- 01.24 댓글창 숨긴거 버튼으로 만들기, xecion 라이브러리 2.3.3 에서 아이콘 복붙함 -->
						<button class="xi-comment-o">댓글쓰기</button>

						<!-- 01.22 댓글쓰기:댓글내용, 누가, 어느글에 -->
						<div class="comment-write">
							<div class="comment-form">
								<textarea id="commentcontent"></textarea>
								<button id="comment-btn">댓글쓰기</button>
							</div>
						</div>
					</c:if>

					<!-- 01.22 댓글 출력창 -->
					<div class="comments">
						<c:forEach items="${commentList }" var="co">
							<div class="comment">
								<div class="chead">
									<div class="cname">${co.mname}님

										<c:if
											test="${sessionScope.mname ne null && co.mid eq sessionScope.mid }">
											<!-- 01.24 나의 부모를 찾아가서 부모 자식중 어떤 것을 찾아가기-->
											<input type="hidden" class="cno" value="${co.cno }">

											<!-- 01.24 댓글 삭제와 수정  -->
											<%-- <img class="commentDel(${co.cno})" width=18 src="./image/delete.png">
											<img onclick="commentEdit(${co.cno})" width=18 src="./image/edit.png"> --%>
											<img class="commentDelete" width=18 src="./image/delete.png">
											<img class="commentUpdate" width=18 src="./image/edit.png">

										</c:if>

									</div>
									<div class="cdate">${co.ip }/${co.cdate}</div>
								</div>
								<div class="ccomment">${co.ccomment }</div>
							</div>
						</c:forEach>
					</div>
			</div>

			<!-- 		<button onclick="url('./board')">게시판으로</button> -->
			<!-- 01.23 게시판으로 클릭시 1페이지로 가는 오류 수정 -->
			<button onclick="url('./board?page=${param.page }')">게시판으로</button>
			</article>
		</div>
	</div>
	<footer>
		<c:import url="footer.jsp" />
	</footer>
	</div>

	<script type="text/javascript">
		function del() {
			var ch = confirm("글을 삭제하시겠습니까?");
			if (ch) {
				location.href = "./delete?no=${detail.no }";
				/* 			${detail.no } 얘가 지금 스트링으로 되어 있는데, 어떻게 적용이 되지?
				 실행순서를 따져야 함. */
			}
		}
		function edit() {
			if (confirm("글을 수정하시겠습니까?")) {
				location.href = "./edit?no=${detail.no }";
			}
		}
		/* 01.24 이거 작동될 때 현페이지에서 댓글만 삭제되는 것이 
		아니고 댓글이 삭제된 새로운 페이지가 로딩되는 거여서
		이것 현페이지에서 댓글만 삭제되게 바꿔주겠다*/
		/* 	function commentDel(cno) {
				if (confirm("댓글을 삭제하시겠습니까?")) {
					location.href="./commentDel?no=${detail.no}&cno="+cno;
				} 
			}*/
	</script>
	<footer>
		<c:import url="footer.jsp" />
	</footer>
</body>
</html>