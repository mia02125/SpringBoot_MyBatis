- [20203020](#20200320) - controller + ajax 구현(한줄평 : 좀 더 다양하고 다른 방식의 코드로 구현 해보자)
- [20203022](#20200322) - controller + ajax 수정(한줄평 : JS만으로 ajax를 대체해보자. $.each를 사용하려면 json형태를 리스트
                                                          (?)형태로 리턴해야하나봄)
- [20203023](#20200323) - JS로 입력값 출력(한줄평 : controller값을 JS으로 가져올 방법이 없을까?)
- [20203026](#20200326) - MyBatis + DB연결(한줄평 : interface는 객체로 쓸수 없는 껍데기이기 떄문에 annotation이 붙을 수 없다)
- [20203029](#20200329) - detatil(상세보기) 추가하기(한줄평 : selectOne()을 쓰면 Id의 result값이 많아서 오류가 생기네..)
- [20203031](#20200331) - 특정 데이터 삭제 로직 추가(한줄평 : update 로직에 대해 좀더 공부하자.. @RequestBody..)
- [20200401](#20200401) -  ajax를 이용해서 데이터를 insert


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

## index.jsp
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


### index.JSP
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
 -src
	-main 
  		-resources
  			BookMapper.xml
			jdbc.propertis
			log4j.xml
			mybatis-config.xml

## Mybatis 설정 

### jdbc.properties 생성 
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
		
	<!-- SQLSessionTemplate 설정 : DAO인터페이스를 만들었기 때문에 Mybatis에서 DAO인터페이스를 구현하기 위해 필요한 작업 -->
	<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
		<constructor-arg index="0" name="sqlSessionFactory" ref="sqlSessionFactory"/>
	</bean>
	<context:component-scan base-package="com.example.mapperImpl">
	</context:component-scan>
	
```


### BookMapper.xml(SQL쿼리를 입력) 생성
```xml
<?xml version="1.0" encoding="UTF-8"?>
 <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <mapper namespace="com.example.mapper.BookMapper"><!-- 인터페이스 mapper의 경로 -->

     <select id="getBookList" resultType="com.example.model.Book">
      SELECT * FROM "Book" 
     </select>

 </mapper>
```


### mybatis-config.xml(Mybatis Mapper를 등록하는 xml) 생성
```xml
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


## 오늘의 정리

```
1. base-package는 mapper의 경로를 찾는데 interface에는 객체로 사용할 수 없기 때문에
@annotation이 붙을 수 없음. 그래서 mapperImpl의 패키지를 등록
2. @Repository 를 사용하여 DAO라고 명시해도 스프링에서 해당하는 패키지를 스캔하지않으면 제대로 스프링의 빈으로 등록되지 않기 때문에 
이를 방지하고자 아래 <context:component-scan ~ 작성 
<context:component-scan base-package="@annotation된 mapper패키지">
</context:component-scan> 
ex)
<context:component-scan base-package="com.example.mapperImpl">
</context:component-scan> 

```

![JS만으로 HTML출력](https://github.com/mia02125/SpringBoot_MyBatis/blob/master/Pic/mybatis_20200320_DB%EC%97%B0%EA%B2%B0.PNG)


# 20200329

### 상세보기 + 삭제하기 추가하기 
### detail 메서드
```java

	@RequestMapping(value = "/detail/{Id}", method = RequestMethod.GET)
	public String detail(@PathVariable int Id ,Model model) throws Exception {
		Book book = new Book();
		book.setId(Id);
		Book books = bookMapper.getBook(book);
		model.addAttribute("bookDetail", books);
		return "detail";
	}
	@RequestMapping(value = "/update/{Id}", method = RequestMethod.GET)
	public String update(@PathVariable int Id) throws Exception { 
		return "update";
	}
	
```

### delete 메서드 
```java
@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteBook() throws Exception {
		bookMapper.deleteBook();
		return "redirect:/";
	}
```

## 오늘의 정리
```
* 오류내용
1. 테이블 내 여러 데이터가 있으면 오류가 생김 
심각: org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.exceptions.TooManyResultsException: Expected one result (or null) to be returned by selectOne(), but found: 5] with root cause
=> BookMapper.xml에서 Id값에 ""로 씌어줌
2. 오류: "bookname" 칼럼은 "Book" 릴레이션(relation)에 없음
=> postgreSQL은 컬럼을 
INSERT INTO "Book"(bookName, bookPublisher, updateDate) VALUES (#{bookName}, #{bookPublisher}, #{bookUpdateDate});
이렇게 쓰면 모두 소문자로 판단하여 오류가 생김
INSERT INTO "Book"("bookName", "bookPublisher", "updateDate") VALUES (#{bookName}, #{bookPublisher}, #{bookUpdateDate});
그래서 이와 같이 대소문자가 같이 쓰였을 때 컬럼마다 ""를 붙여 줘야함 
```


# 20200331


### delete/{bookId} 추가 
```java
@RequestMapping(value = "/delete/{bookId}", method = RequestMethod.POST)
	public String deleteBookOne(@PathVariable(value = "bookId") int bookId, Book book) throws Exception {
		book.setBookId(bookId);
		bookDAO.deleteBookOne(book);
		return "redirect:/";
	}
```

## 오늘의 정리
```
1. @RequestBody
	- @RequestMapping에 의해 POST방식으로 전송된 HTTP요청 데이터(body)를 자바 객체로 전달받음
	- @RequestBody가 적용된 경우, 리턴 객체를 JSON이나 XML과 같은 알맞은 응답으로 변환
2. @ResponseBody
	- @ResponseBody가 @RequestMapping에 적용되면 해당 메서드의 리턴값을 HTTP 응답 데이터로 사용 
	- 직접 JSON데이터 형식으로 저장하여 리턴할 필요없이 자동으로 JSON데이터 형식으로 리턴
	
ex) 
@RequestMapping(method = RequestMethod.POST)	
@ResponseBody
public String Test(@RequestBody String body) { 
	return body; // String값을 HTTP응답데이터로 전송
}
ajax를 사용하여 파라미터 값을 읽어냄  
```


# 20200401

### BookController
```java
@Autowired
	private BookDAO bookDAO;
	
	@RequestMapping(value = "/input", method = RequestMethod.POST)
	@ResponseBody
	public String insertBook(@RequestBody Book book) throws Exception { 
		// ajax에서 Post방식으로 Book이라는 객체의 Body에 데이터를 받아온다
		String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		book.setBookUpdateDate(currentDate);
		bookDAO.insertBook(book);
		return "redirect:/";
```

### index.JSP
```javascript
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
			alert("버튼 클릭");
			$.ajax({
				url : "/input", // 전송페이지(action url)
				type : "POST", // 전송방식
				data : JSON.stringify(dataList), // 전송할 데이터(JSON.stringify() 을 사용하여 데이터를 문자열화)
// 				dataType : "json", // return 타입에 따라 data타입 결정 
				contentType : "application/json; charset=utf-8",
				success : function(data) { 
					console.log("success");
					console.info(data);
				},
				error : function(e) {
					alert("error(포기하지마라)");
					console.log(e);
				}
			});
		});
	});
```


```html
<body>
<input type="text" name="bookName" id="bookName" placeholder="name"> 
<br>
<input type="text" name="bookPublisher" id="bookPublisher" placeholder="publisher">
<button id="btn1" type="button">버튼1</button>
</body>
```

### 오늘의 정리
```
1. @RequestBody & @ModelAttribute 
	1-1. @RequestBody : POST방식을 통해 요청된 Body에 데이터를 담는다.
	1-2. @ModelAttribute : 데이터를 받아오되 JSON형식에서 파라미터값만 가져온다.
	ex) { name : "최재민" } => "최재민"
2. ajax ( https://seypark.tistory.com/65 참고1, https://cofs.tistory.com/404 참고2 ) 
$.ajax({ 
	url : // 전송페이지 	=> "/"
	type : // 전송방식 	 => post / get
	data : // 전송할 데이터(데이터를 문자열화 하지않기때문에 JSON.stringfy()로 감싸야함 => { 보낼데이터 }
	dataType : // 서버에서 받을 return된 데이터타입(전송할 데이터의 타입이 아님!!) => xml / html / json / jsonp / script / text
	contentType : // 새로운 데이터 유형을 정희할 때 사용 
	
3. redirect & forward 핵심 
	3-1. redirect : 요청정보를 새롭게 요청!!
	3-2. forward  : 요청정보를 재활용!!
4. 테이블 컬럼을 찾지못하는 오류가 생기면 ajax로직이 틀리진 않았는지 확인하고, Book이라는 객체 @RequestBody를 통해 가져올 수 있는지 확인하기!!
5. @어노테이션을 사용하지않고 XML에 등록하여 따로 선언하지않아도 사용할 수 있다.
```
