<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
	
</script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form id="update/{Id}" method="post">
		<input type="text" name="updateName" id="updateName" placeholder="updateName"> <br>
		<input type="text" name="updatePublisher" id="updatePublisher" placeholder="updatePublisher">
	</form>
	<button id="updateBtn">버튼1</button>
</body>
</html>