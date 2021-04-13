<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring_Ajax_BoardList **</title>
<link rel="stylesheet" type="text/css" 
		  href="resources/myLib/myStyle.css">
<script src="resources/myLib/jquery-3.2.1.min.js"></script>		  
<script src="resources/myLib/axTest01.js"></script>	
<script src="resources/myLib/axTest03.js"></script>
</head>
<body>
<h2>** Spring MVC2 Ajax_BoardList **</h2>
<c:if test="${message!=null}">
=> ${message}
</c:if>
<hr>
<table width=800>
	<tr align="center" height="30" bgcolor="aqua">
	<td>Seq</td><td>Title</td><td>ID</td><td>Regdate</td><td>Count</td>
	</tr>
<!--  반복문에 이벤트 적용 
=> 매개변수처리에 varStatus 활용, ajax, json Test  
=> Login 여부에 무관하게 처리함.
// Test 1. 타이틀 클릭하면, 하단(resultArea2)에 글 내용 출력하기  => => aTag, JS, jsBDetail1(  ) 
// Test 2. 타이틀 클릭하면, 글목록의 아랫쪽(span result)에 글 내용 출력하기 => JS, aTag, jsBDetail2( , ) 
// Test 3. seq 에 마우스 오버시에 별도의 DIV에 글내용 표시 되도록 하기 => jQuery, this
// 			=> seq 에 마우스 오버 이벤트
//			=> content 를 표시할 div (table 바깥쪽에) : 표시/사라짐  
//			=> 마우스 포인터의 위치 를 이용해서   div 의 표시위치 지정
-->	
  <c:forEach var="row" items="${Banana}" varStatus="vs">
	<tr >
	<!-- sss: Test 3.1 , sss2 : Test 3.2 -->
	<td id="${row.seq}" class="sss2">${row.seq}</td>
	<td>
	<!-- 답글등록 후 indent 값에 따른 들여쓰기 -->
	<c:if test="${row.indent>0}">
		<c:forEach begin="1" end="${row.indent}">
			<span>&nbsp;&nbsp;</span>
		</c:forEach>
		<span style="color:orange">re..</span>
	</c:if>
	<!-- Test 1. 타이틀 클릭하면, 하단(resultArea2)에 글 내용 출력하기 
	<a href="javascript:;" onclick="jsBDetail1(${row.seq})">${row.title}</a>-->
	
	<!-- Test 2. 타이틀 클릭하면, 글목록의 아랫쪽(span result)에 글 내용 출력하기 
	  => function 에 이벤트 객체 전달
	  => 이벤트 리스너 함수의 첫번쨰 매개변수에 event 라는 이름으로 전달
	-->
	<a href="javascript:;" onclick="jsBDetail2(event,${row.seq},${vs.index})">${row.title}</a>
	</td>
	<td>${row.id}</td><td>${row.regdate}</td><td>${row.cnt}</td>
	</tr>
	<tr><td></td>
		<td colspan="4">
		<span id="content${vs.index}" class="content" style="background:LightCyan"></span></td>
	</tr>
  </c:forEach>
</table>
<div id="content"></div>
<hr>
</body>
</html>