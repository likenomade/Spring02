<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Home</title>
<link rel="stylesheet" type="text/css" 
		  href="resources/myLib/myStyle.css">
<script src="resources/myLib/jquery-3.2.1.min.js"></script>
<script src="resources/myLib/axTest01.js"></script>
<!-- <script>
$(function(){
	$('#aloginf').click(function(){
		$.ajax({
			type:'Get',
			url:'mloginf',
			success:function(resultPage){
				$('#resultArea').html(resultPage);
				},
			error:function(){
				$('#resultArea').html("~~ 오류발생 ~~");
			}	
		}); //ajax
	}); //click
}); //ready
</script>	 -->
<script>
	function setClock() {
		var now = new Date();
		var t='* Now: '+
			now.getFullYear()+'년 '+(now.getMonth()+1)+'월 '+now.getDate()+'일 '
			+ now.getHours()+':'+now.getMinutes()+':'+now.getSeconds();
		document.getElementById('clock').innerHTML=t;
		//setTimeout('setClock()',1000); // 단위  1/1000 초 
		//setInterval('setClock()',1000); 
		// 위 둘은 다른 메서드 이지만 function 에 적용했을때 동일한 결과
	}
</script>
</head>
<body onload="setClock()">
<h1>
	Hello UI Test !!!  
</h1>
<span>* Start: ${serverTime}. </span><br>
<span id="clock"></span><br>
<c:if test="${message!=null}">
	<script>
		alert('${message}');
	</script>
</c:if>
<c:if test="${loginID!=null}">
<br>
=> ${loginID} 님 안녕하세요 !!! ~~<br>
</c:if>
<hr>
<img src="resources/image/summersea.jpg" width="300" height="200">
<hr>
<c:if test="${loginID!=null}">
	<a href="mdetail?id=${loginID}">MyInfo</a>&nbsp;
	<a href="mlogout">Logout</a>&nbsp;
</c:if>
<c:if test="${empty loginID}">
	<a href="mjoinf">Join</a>&nbsp;
	<a href="mloginf">Login</a>&nbsp;
</c:if>
<br><hr>
<a href="mlist">mList</a>&nbsp;
<a href="blist">bList</a>&nbsp;
<span id="aloginf" class="textLink">axLoginf</span>&nbsp;
<a href="atestf">axTest</a>&nbsp;<br>
<hr>
<div id="resultArea">
</div>
</body>
</html>