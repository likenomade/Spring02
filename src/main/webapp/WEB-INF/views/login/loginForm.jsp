<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>**Spring Member Login **</title>
<link rel="stylesheet" type="text/css" href="../myLib/myStyle.css">
<script src="resources/myLib/jquery-3.2.1.min.js"></script>
<script src="resources/myLib/inCheck.js"></script>
<script src="resources/myLib/axTest01.js"></script>
<!-- <script>
$(function(){
	$('#alogin').click(function(){
		$.ajax({
			type:'Get',
			url:'mlogin',
			data:{
				id:$('#id').val(),
				password:$('#password').val()
			},
			success:function(){
				//console.log('test r=>'+r);
				$('#resultArea').html('');
				location.reload(); // home 새로고침
				},
			error:function(){
				$('#resultArea').html("~~ AxLogin 오류발생 ~~");
			}	
		}); //ajax
	}); //click
}); //ready
</script> -->

<script>
	// 1. 전역변수
	// => 오류확인을 위한 switch 변수들
	var iCheck = false;
	var pCheck = false;
	// 2. 각 Tag의 focusout 을 이용한 무결성점검
	$(function() {
		$('#id').focus();
		$('#id').focusout(function() {
			iCheck = idCheck();
		}); //id

		$('#password').focusout(function() {
			pCheck = pwCheck();
		}); //password
	}); //ready

	// 3. submit 을 위한 function inCheck() 
	function inCheck() {
		if (iCheck == false) {
			$('#iMessage').html(' ID 를 확인 하세요 ~~');
			$('#id').focus();
		}
		if (pCheck == false) {
			$('#pMessage').html(' Password 를 확인 하세요 ~~');
			$('#password').focus();
		}
		if (iCheck == true && pCheck == true) {
			return true; // submit 실행
		} else {
			$('#id').focus();
			return false; // submit 무효화 
		}
	} //inCheck
</script>
</head>
<body>
	<h2>** Spring Member Login **</h2>
	<form action="mlogin" method="get">
		<table>
			<tr>
				<td><label for="id">I D :</label></td>
				<td><input type="text" name="id" id="id"><br> <span
					id="iMessage" class="message"></span></td>
			</tr>
			<tr>
				<td><label for="pw">Password:</label></td>
				<td><input type="password" name="password" id="password"><br>
					<span id="pMessage" class="message"></span></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Login" onclick="return inCheck()"> 
				<input type="reset"value="Reset">&nbsp;
					 <span id="alogin" class="textLink">axLogin</span>&nbsp;
					 <span id="jlogin" class="textLink">jsLogin</span></td>
			</tr>
		</table>
	</form>
	<c:if test="${message != null}">
		<hr><br>
	=> ${message}
</c:if>
	<hr>
	<a href="home">[Home]</a>
</body>
</html>