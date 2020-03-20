<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
var dataList = {
		name : "bookName",
		publisher : "publisherName"
};



//ajax : 자바스크립트를 이용해 비동기적으로 서버와 브라우저 간 데이터를 교환하는 통신 방식
function onClick() { 
	debugger;
	$.ajax({
// 		url : "http://localhost:8080/inputRequest",
		url : "inputRequest2",
// 		url : "/inputRequest2",
		type : "POST",
		data : $("#inputAction").serialize(), //  serialize() : form의 파라미터를 넘기기위해 상ㅇ
// 		dataType : 'json', // ajax 통신으로 받는 타입
		success : function(data) { 
// 			alert("success");
// 			$('#resultData').text(data);
			console.log('success');
			console.dir(data);
		},
		error : function(event) {
			console.log('error');
			console.dir(event);
		},
		complete : function(event){
			console.log('complete');
			console.dir(event);
		}
	});
}
</script>
<body>
	<form action="inputAction">
		<input type="text" name="name" id="name" placeholder="name"> <br>
		<input type="text" name="publisher" id="publisher"
			placeholder="publisher">
	</form>

	<button onclick="onClick()">입력</button>
	<div class="resultData"></div>
</body>
</html>
