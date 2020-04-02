<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
 


</style>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script><!-- Jquery를 사용하기위해서 라이브러리 추가 -->
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
	 
	// ajax : 자바스크립트를 이용해 비동기적으로 서버와 브라우저 간 데이터를 교환하는 통신 방식
	// 방법 #1
	$(document).ready(function() {
	//document가 준비된 후 자바 스크립트 시작 	
		$("#btn1").on('click', function() {
			// 읽어낼 document가 없으면 스크립트를 못 읽어냄 
			var dataList = {
					bookName : $('#bookName').val(), 
					// $('#name').val() => id = name의 값 
					bookPublisher : $('#bookPublisher').val(), 
					// $('#publisher').val() => id = publisher의 값
					bookUpdateDate : ""
				};
			console.log("버튼 클릭");
			$.ajax({
				url : "/input", // 전송페이지(action url)
				type : "POST", // 전송방식
				data : JSON.stringify(dataList), //전송할 데이터
// 				dataType : "json", // ajax 통신으로 받는 타입
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
				 
				 
/**
	 // 방법 #3 
	 function btn3() { 
		alert("btn3 이벤트");		
		var today = new Date();
		var str = "";
		var book = {		
			name : document.getElementById("name").value,
			publisher : document.getElementById("publisher").value,
			updateDate : today.toLocaleString(),
			info : function() {
				console.info(book);
				str += "<table border=1 width=500>";
				str += "<tr align=center>";
				str += 		"<th>도서명</th>"
				str += 		"<th>출판사명</th>";
				str += 		"<th>업데이트 날짜</th></tr>"; 
				str += "</tr>";
				str += "<tr align=center>";
				str += 		"<td>" + this.name + "</td>";
				str += 		"<td>" + this.publisher + "</td>";
				str += 		"<td>" + this.updateDate + "</td>";
				str += "</tr>";
				document.write(str);
				
				
			}
		};
		document.write("<h1>서적관리시스템</h1>")
		book.info();
	}
*/


</script>
<body>

		<input type="text" name="bookName" id="bookName" placeholder="name"> <br>
		<input type="text" name="bookPublisher" id="bookPublisher"
			placeholder="publisher">
			<button id="btn1" type="button">버튼1</button>

	<br>
	<form action="delete" method="post">
		<button type="submit">전체삭제</button>
	</form>
	<!-- 
	<button id="btn3" onclick="btn3()">버튼3</button>
	 -->
	<table>
	<!-- 자바 스크립트 -->
	</table>

	<table border="1" style="width: 500px">
		<tr>
			<th align="center">도서명</th>
			<th>출판사명</th>
			<th>업데이트날짜</th>
		</tr>

		<c:forEach var="item" items="${bookList}">
			<tr>
				<td><a href="/detail/${item.getBookId()}">${item.getBookName()}</a></td>
				<td>${item.getBookPublisher()}</td>
				<td>${item.getBookUpdateDate()}</td>
			</tr>
		</c:forEach>

	</table>

</body>
<!-- document 밑에는 가능 -->
<!-- 
<script type="text/javascript">
$("#btn2").click( function() {
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
 -->
</html>