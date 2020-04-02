<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
	function goBack() { 
		window.history.back();
	}
</script>
</head>
<body>

	<table border="1">
		<tr>
			<th>도서명</th>
			<th>출판사명</th>
			<th>업데이트날짜</th>
		</tr>
			<tr>
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