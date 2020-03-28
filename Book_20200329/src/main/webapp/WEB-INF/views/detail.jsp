<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<table border="1">
		<tr>
			<th>도서명</th>
			<th>출판사명</th>
			<th>업데이트날짜</th>
		</tr>
			<tr>
				<td>${bookDetail.getName()}</td>
				<td>${bookDetail.getPublisher()}</td>
				<td>${bookDetail.getUpdateDate()}</td>
			</tr>
	</table>
	<br>
	<!-- 
	<button onclick="/update/${bookDetail.getId()}"></button> 
 -->
</body>
</html>