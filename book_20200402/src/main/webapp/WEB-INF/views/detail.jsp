<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script><!-- Jquery를 사용하기위해서 라이브러리 추가 -->
<script type="text/javascript">
	function goBack() { 
		window.history.back();
	}
		// 세션을 이용해서 booId값을 저장하여 update할 때 bookId값을 controller에 요청할 예정
		sessionStorage.setItem("bookId", ${bookDetail.getBookId()}); // 세션에 value값을 저장 
		var Id = sessionStorage.getItem("bookId"); // key값을 이용해 value값을 가져옴 
		console.log("bookId값 : " + Id); //bookId 값 출력 	
	
</script>
</head>
<body>

	<table border="1">
		<tr>	
			<th>index</th>
			<th>도서명</th>
			<th>출판사명</th>
			<th>업데이트날짜</th>
		</tr>
			<tr>
				<td>${bookDetail.getBookId()}</td>
				<td>${bookDetail.getBookName()}</td>
				<td>${bookDetail.getBookPublisher()}</td>
				<td>${bookDetail.getBookUpdateDate()}</td>
			</tr>
	</table>
	
	<br>
	 
	<button onclick='location.href="/update/${bookDetail.getBookId()}"'>수정</button> 
 	<form action="/delete/${bookDetail.getBookId()}" method="post">
 		<button type="submit">삭제</button>
 	</form>
 	<br>
 	<button onclick="goBack()">뒤로</button>
</body>
</html>