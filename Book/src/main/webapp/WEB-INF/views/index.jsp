<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<!-- Jquery를 사용하기위해서 라이브러리 추가 -->
<!-- ajax를 사용하기위해  jackson 라이브러리 추가(pom.xml) --> 
<script type="text/javascript">
	/*
	 debugger;

	 var a= {a:1,b:2};

	 console.dir(a); //O
	 console.dir(a + '/ test '); //x

	 console.log(a + '/ test '); //O
	 console.log();
	 console.error();
	 */
	//ajax : 자바스크립트를 이용해 비동기적으로 서버와 브라우저 간 데이터를 교환하는 통신 방식
	// 방법 #1
	$(document).ready(function() {
	//document가 준비된 후 자바 스크립트 시작 	
		
		$("#btn").on('click', function() {
			// 읽어낼 document가 없으면 스크립트를 못 읽어냄 
			var dataList = {
					name : $('#name').val(), 
					// $('#name').val() => id = name의 값 
					publisher : $('#publisher').val(), 
					// $('#publisher').val() => id = publisher의 값
					updateDate : ""
				};
			alert("버튼이 클릭");
			$.ajax({
				url : "/inputRequest",
				type : "POST",
				data : dataList, 
				dataType : "json", // ajax 통신으로 받는 타입
				success : function(data) {
					console.log("success");
					console.info(data);
					$('#resultData').html(data);
				},
				error : function() {
					alert("error(포기하지마라)");
				}
			});
		});
	});
</script>
<body>
	<form id ="input" method="post">
		<input type="text" name="name" id="name" placeholder="name"> <br>
		<input type="text" name="publisher" id="publisher"
			placeholder="publisher">
	</form>
	<button id="btn">버튼1</button>
	<button id="btn2">버튼2</button>
	<div id="resultData"></div>

</body>
<!-- document 밑에는 가능 -->
<script type="text/javascript">
$("#btn2").on('click', function() {
	// 위에 읽어낼 document가 있기때문에   $(document).ready(function() {가 필요없음 
	var dataList = {
			//JSON형태이기떄문에 map형태로 받아야함(이름=값) 
			name : $('#name').val(), 
			// $('#name').val() => id = name의 값 
			publisher : $('#publisher').val(), 
			// $('#publisher').val() => id = publisher의 값
			updateDate : ""
			//컨트롤 값이 들어감 
		};
	alert("버튼이 클릭");
	$.ajax({
		url : "/inputRequest",
		type : "POST",
		data : dataList, 
		dataType : "json", // ajax 통신으로 받는 타입

		success : function(data) {
			console.log("success");
			console.info(data);
			$('#resultData').html(data);
		},
		error : function(error) {
			console.error(error)
			/*
			디버깅 시 뽑아낼 데이터부분를 클릭하고 F12 - source - console로 확인
						""				 - network - inputRequest - header - form data로 확인
			되도록 실행 시  디버깅으로 하고 디버깅 새로 고침은 ctrl + shift + R 
			*/
		}
	});
});
</script>
</html>