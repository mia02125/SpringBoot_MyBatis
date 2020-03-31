<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
	function goBack() { 
		window.history.back();
	}
</script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<form action="/update" method="post">
		<input type="text" name="updateName" id="updateName" placeholder="updateName"> <br>
		<input type="text" name="updatePublisher" id="updatePublisher" placeholder="updatePublisher">
	<br>
	<button id="updateBtn" type="submit">수정</button>
	</form>
	<button onclick="goBack()">뒤로</button>
</body>
</html>