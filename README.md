- [202003020](#20200320) - controller + ajax 구현(한줄평 : 좀 더 다양하고 다른 방식의 코드로 구현 해보자)

# 20200320 

### HomeController 
```java
@Controller
public class HomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "index";
	}
	@RequestMapping(value = "/inputRequest", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insert(HttpServletRequest request) {
		//JSON형태로 데이터를 보내려면 HashMap형태로 리턴해야함
		Map<String, Object> map = new HashMap<String, Object>();
		String bookName = request.getParameter("name");
		String bookPublisher = request.getParameter("publisher");
		String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		map.put("name", bookName);
		map.put("publisher", bookPublisher);
		map.put("updateDate", currentDate);
		return map;
	}	
}


```

### index.jsp
```html
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
	$(document).ready(function() { // <= document가 준비된 후 자바 스크립트 시작한다
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
	<form id="input" method="post">
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

```

### pom.xml 
##### [ ajax를 사용하기위해  jackson 라이브러리 추가(pom.xml) ] 
```xml
		<!-- jackson -->
		<dependency>
		    <groupId>com.fasterxml.jackson.core</groupId>
		    <artifactId>jackson-core</artifactId>
		    <version>2.4.1</version>
		</dependency>
		<dependency>
		    <groupId>com.fasterxml.jackson.core</groupId>
		    <artifactId>jackson-databind</artifactId>
		    <version>2.4.1.1</version>
		</dependency>
		<dependency>
		    <groupId>org.codehaus.jackson</groupId>
		    <artifactId>jackson-core-asl</artifactId>
		    <version>1.9.13</version>
		</dependency>
		<dependency>
		    <groupId>org.codehaus.jackson</groupId>
		    <artifactId>jackson-mapper-asl</artifactId>
		    <version>1.9.13</version>
		</dependency>
```



## 오늘의 정리
```
- class는 모든 인덱스를 가져온다 
- Id는 인덱스[0]밖에 가져오지못한다
<input Id = "">  JQuery : $('#Id값')
<input class = ""> JQuery : $('.class값')
키워드 JQuery : $('키워드값')
```

```
- $('#btn').click( function() { }); 
	1. 고유성 있음
	2. 해당 document가 있어야 스크립트 실행 가능 
	3. 최초에 선언된 element에만 동작 
	4. 추가 함수에 있어서 실행이 안됨 ex) $('#btn').append().... 실행 x
- $(document).on('click', function() { }); 
	1. 고유성 없음
	2. 동적으로 가능
```

```
- 일반 함수 
	1. 코드 전체를 파싱하는 단계에 생성 
	ex) function test() { } 
- 인라인(inline) 함수 
	1. 변수에 대입되는 방식이므로 런타임 시 생성
	2. 이 변수를 이용해 다른 코드에서 함수를 재사용
	ex) var test1 = function () { } 
```

```
- 디버깅 시 console을 이용해서 데이터값 확인하고 
  form을 이용해 값을 보낼 때는 network에서 form Data 확인 
- 디버깅 새로 고침은 ctrl + shift + R 
- JSON형태로 데이터를 보내려면 HashMap형태로 리턴해야함
- 읽어낼 document가 있어야 $("#btn2").on('click', function() { 와 같은 스트립트 처리가 가능
- var test = {
	a : 1,
	b : 2
  };
  console.Info(test) => 출력 가능 
  console.Info(test + "") => 출력 불가능 
  console.div(test) => 출력 가능 
  console.div(test + "") => 출력 불가능 
  console.log(test + "") => 출력 가능
```