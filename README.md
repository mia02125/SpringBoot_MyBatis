- [202003020](#20200320) - controller + ajax 구현(한줄평 : 좀 더 다양하고 다른 방식의 코드로 구현 해보자)
- [202003022](#20200322) - controller + ajax 수정(한줄평 : JS만으로 ajax를 대체해보자. $.each를 사용하려면 json형태를 리스트
                                                          (?)형태로 리턴해야하나봄)
- [202003023](#20200323) - JS로 입력값 출력(한줄평 : controller값을 JS으로 가져올 방법이 없을까?)
- [202003026](#20200326) - MyBatis + DB
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

```
톰캣은 exe 파일로 다운로드 받지말고 알집파일로 받을것 
```


# 20200322
```java
@RequestMapping(value = "/inputRequest", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> insert(HttpServletRequest request) {
		//JSON형태로 데이터를 보내려면 HashMap형태로 리턴해야함
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		String bookName = request.getParameter("name");
		String bookPublisher = request.getParameter("publisher");
		String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		map.put("name", bookName);
		map.put("publisher", bookPublisher);
		map.put("updateDate", currentDate);
		list.add(map);
		return list;
	}
```

```javascript
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
					$('table').html("<tr><th>번호</th><th>도서명</th><th>출판사명</th><th>업데이트날짜</th></tr>");
					var url = "";
					//질문사항 : 입력값이 나오려면 JSON형태를 list형태로 리턴해야하는가??
					$.each(data, function(index, item) {
						url += "<tr><td>"+ index +"</td>";
						url += "<td>"+ item.name +"</td>";
						url += "<td>"+ item.publisher +"</td>";
						url += "<td>"+ item.updateDate +"</td></tr>";
										
					});
					$("table").append(url);
					// 출처 : http://blog.naver.com/PostView.nhn?blogId=duddnddl9&logNo=220568856214
				},
				error : function() {
					alert("error(포기하지마라)");
				}
			});
		});
	});

```
## 오늘의 정리
```
=> 바로 data를 뽑아내면 "Object object" 출력이 되기때문에 $.each를 사용해 출력하는데 JSON형태를 List형태로 리턴하여 출력을 하여 뽑아낸다
```

![HTML출력](https://raw.githubusercontent.com/mia02125/SpringBoot_MyBatis/master/Pic/ajax_20200322(Lode%20Data%20at%20HTML).PNG)


# 20200323
 ```js
	 // 방법 #3 
	 function btn3() { 
		alert("btn3 이벤트");		
		var today = new Date();
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
```

```html
<button id="btn3" onclick="btn3()">버튼3</button>
```
![JS만으로 HTML출력](https://raw.githubusercontent.com/mia02125/SpringBoot_MyBatis/master/Pic/JS_20200323_getElementById.PNG)



# 20200326

## Mybatis 설정 

### jdbc.properties
```
jdbc.driverClassName=org.postgresql.Driver
jdbc.url=jdbc:postgresql://localhost:5432/postgres?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=utf8
jdbc.username=postgres
jdbc.password=oralce
```

### root-context 설정
```
namespaces에서 beans / jdbc / context / mvc / mybatis 의 xsi:schemaLocation 항목 체크!!
```
```xml
	<!-- jdbc.properties에서 데이터를 가져옴 -->
	<bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        	<property name="locations" value="classpath:jdbc.properties" />
        	<property name="fileEncoding" value="UTF-8" />
    </bean>
	<!-- postgres datasource를 가져오지 못하면 root-context.xml에 직접 적어서 연결하기 -->
	<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
		<property name="driverClassName" value="org.postgresql.Driver" />
        	<property name="url" value="jdbc:postgresql://localhost:5432/postgres?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=utf8" />
        	<property name="username" value="postgres" />
        	<property name="password" value="oracle" />
	</bean>
	<!-- Mybatis와  spring을 연동 -->
	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="dataSource" ref="dataSource" />
		<!-- DB에서 실행할 sql 문장을 태그로 정의해 놓는다. -->
		<property name="mapperLocations" value="classpath:BookMapper.xml"/>
	</bean>
		
	<!-- 3. Mybatis DB 프로그램의 자동화 객체 -> template -->
	<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
		<constructor-arg index="0" name="sqlSessionFactory" ref="sqlSessionFactory"/>
	</bean>
	
```

### BookMapper(SQL쿼리를 입력)
```
<?xml version="1.0" encoding="UTF-8"?>
 <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <mapper namespace="com.example.mapper.BookMapper"><!-- 인터페이스 mapper의 경로 -->

     <select id="getBookList" resultType="com.example.model.Book">
      SELECT * FROM "Book" 
     </select>

 </mapper>
```
### mybatis-config.xml(Mybatis Mapper를 등록하는 xml)
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <mappers>
        <mapper resource="BookMapper.xml"/>
        <!--  -->
    </mappers>
</configuration>
```

```
base-package는 mapper의 경로를 찾는데 interface에는 객체로 사용할 수 없기 때문에
@annotation이 붙을 수 없음. 그래서 mapperImpl의 패키지를 등록
<context:component-scan base-package="@annotation된 mapper패키지">
</context:component-scan> 
ex)
<context:component-scan base-package="com.example.mapperImpl">
</context:component-scan> 

```

![JS만으로 HTML출력](https://github.com/mia02125/SpringBoot_MyBatis/blob/master/Pic/mybatis_20200320_DB%EC%97%B0%EA%B2%B0.PNG)
