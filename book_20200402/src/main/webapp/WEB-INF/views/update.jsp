<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script><!-- Jquery를 사용하기위해서 라이브러리 추가 -->
<script type="text/javascript">
	
	
	function goBack() { 
		window.history.back();
	}
	
	// update ajax 
	$(document).ready(function() {
		//document가 준비된 후 자바 스크립트 시작 	
			$("#btn1").on('click', function() {
				// 읽어낼 document가 없으면 스크립트를 못 읽어냄 
				var bookIdValue = sessionStorage.getItem("bookId");	 // 세션에 저장된 값을 가져옴 
				var dataList = {
						bookId : bookIdValue,
						bookName : $('#bookName').val(), 
						// $('#name').val() => id = name의 값 
						bookPublisher : $('#bookPublisher').val() 
						// $('#publisher').val() => id = publisher의 값
					};
				console.log("버튼 클릭");
				$.ajax({
					url : "/update", // 전송페이지(action url)
					type : "POST", // 전송방식
					data : JSON.stringify(dataList), //전송할 데이터
//	 				dataType : "json", // ajax 통신으로 받는 타입
					contentType : "application/json; charset=utf-8",
					success : function(data) { 
						console.log("success");
						console.info(data);
						// 출처 : http://blog.naver.com/PostView.nhn?blogId=duddnddl9&logNo=220568856214
					},
					error : function(e) {
						console.log("error(포기하지마라)");
						console.log(e);
					}
				});
			});
		});
</script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<input type="text" name="bookName" id="bookName" placeholder="updateName"> <br>
	<input type="text" name="bookPublisher" id="bookPublisher" placeholder="updatePublisher">
	<br>
	<button id="btn1" type="button">수정</button>
	
	<button onclick="goBack()">뒤로</button>
</body>

</html>