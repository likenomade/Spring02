<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spring MVC2 UpdateForm</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
<script src="resources/myLib/jquery-3.2.1.min.js"></script>
<script src="resources/myLib/inCheck.js"></script>
<script>
//** 입력 오류 확인 ( inCheck )
//1. 개별적 오류 확인을 위한 switch 변수 정의
var pCheck=false;
var nCheck=false;
var bCheck=false;
var poCheck=false;
var wCheck=false;

//2. 개별적 focusout 이벤트리스너 function 작성 : JQuery,
$(function(){

	$('#password').focusout(function() {
		pCheck=pwCheck();
	}); //password
	
	$('#name').focusout(function() {
		nCheck=nmCheck();
	}); //name
	
	$('#birthd').focusout(function() {
		bCheck=bdCheck();
	}); //birthd
	
	$('#point').focusout(function() {
		//poCheck=poCheck();
		//=> 로직오류 : 변수명과 함수명이 같은경우 변수우선
		poCheck=potCheck();
	}); //point
	
	$('#weight').focusout(function() {
		wCheck=weCheck();
	}); //weight
	
}); //ready

//3. 전체적으로 입력 오류 가 없음을 확인하고 submit 여부를 판단
function inCheck() {
	
	if (pCheck==false) {
		$('#pMessage').html(' Password 를 확인 하세요 ~~');
		$('#password').focus();
	}
	if (nCheck==false) {
		$('#nMessage').html(' Name 을 확인 하세요 ~~');
		$('#name').focus();
	}
	if (bCheck==false) {
		$('#bMessage').html(' BirthDay 를 확인 하세요 ~~');
		$('#birthd').focus();
	}

	if (poCheck==false) {
		$('#poMessage').html(' Point 를 확인 하세요 ~~');
		$('#point').focus();
	}
	if (wCheck==false) {
		$('#wMessage').html(' Weight 를 확인 하세요 ~~');
		$('#weight').focus();
	}
	if (pCheck==true && nCheck==true && bCheck==true 
		&& poCheck==true && wCheck==true ) {
		if(confirm('~~ 수정 완료, 전송하시겠습니까?')==true){
			return true;  // submit 실행
		}else{
	        $('#name').focus();
	        return false;
		} //confirm  
	}else {
        return false;   // submit 무효화 
	}
} //inCheck

</script>

</head>
<body>
<h2>** Spring MVC2 UpdateForm **</h2>
<form action="mupdate" method="post" enctype="multipart/form-data"><table>
  <tr height="40"><td bgcolor="aqua">I  D</td>
  	  <td><input type="text" name="id" id="id" readonly value="${memberVO.id}"></td>
  	  <!-- disabled: value가 서버로 전송되지않음 , readonly: value가 서버로 전송됨 -->
  </tr>
  <tr height="40"><td bgcolor="aqua">Password</td>
  	  <td><input type="password" name="password" id="password" size="10" value="${memberVO.password}"><br>
		<span  id="pMessage" class="message"></span></td>
  </tr>
  <tr height="40"><td bgcolor="aqua">Name</td>
	<td><input type="text" name="name" id="name" value="${memberVO.name}" size="10"><br>
		<span  id="nMessage" class="message"></span></td>
	</tr>
  </tr>
  <tr height="40"><td bgcolor="aqua">Level</td>
  <!-- ** 참고
  		-> 단, select에 value와 동일하게 A,B,C,D 로 표시됨
  	<c:set var="ch" value="selected"/>
	<select name="lev">
		<c:forTokens var="list" items="A,B,C,D" delims=",">
		<c:if test="${list eq info.lev}">
			<option value="${list}" ${ch}>${list}</option>
		</c:if>
		<c:if test="${list ne info.lev}">
			<option value="${list}">${list}</option>
		</c:if>
		</c:forTokens>
	</select>
   -->
  	<!-- lev 의 값에 따라 selected 설정하기 -->
  	  <td><c:choose>
  	  	<c:when test="${memberVO.lev=='A' }">
  	  	<select name="lev" id="lev">
				<option value="A" selected>관리자</option>
				<option value="B">나무</option>
				<option value="C">잎새</option>
				<option value="D">새싹</option>
		</select></c:when>  
		<c:when test="${memberVO.lev=='B' }">
		<select name="lev" id="lev">
				<option value="A">관리자</option>
				<option value="B" selected>나무</option>
				<option value="C">잎새</option>
				<option value="D">새싹</option>
		</select></c:when>
		<c:when test="${memberVO.lev=='C' }">
		<select name="lev" id="lev">
				<option value="A">관리자</option>
				<option value="B">나무</option>
				<option value="C" selected>잎새</option>
				<option value="D">새싹</option>
		</select></c:when>
		<c:otherwise>
		<select name="lev" id="lev">
				<option value="A">관리자</option>
				<option value="B">나무</option>
				<option value="C">잎새</option>
				<option value="D" selected>새싹</option>
		</select></c:otherwise>
	  </c:choose></td>
  </tr>
  <tr height="40"><td bgcolor="aqua">BirthDay</td>
	<td><input type="date" name="birthd" id="birthd" value="${memberVO.birthd}"><br>
		<span id="bMessage" class="message"></span></td>
  </tr>
  <tr height="40"><td bgcolor="aqua">Point</td>
  	  <td><input type="text" name="point" id="point" value="${memberVO.point}" size="10"><br>
		<span id="poMessage" class="message"></span></td>
  </tr>
  <tr height="40"><td bgcolor="aqua">Weight</td>
  	  <td><input type="text" name="weight" id="weight" value="${memberVO.weight}" size="10"><br>
		  <span id="wMessage" class="message"></span></td>
  </tr>
  <tr height="40"><td bgcolor="aqua">추천인</td>
  	  <td><input type="text" name="rid" id="rid" value="${memberVO.rid}"></td>
  </tr>
  
   <tr height="40"><td bgcolor="pink">Image</td>
  	<td><img src="${memberVO.uploadfile}" class="select_img" width="70" height="70"/>
  		<input type="hidden" name="uploadfile" value="${memberVO.uploadfile}"><br>
  		<input type="file" name="uploadfilef" id="uploadfilef">
  	<script>
		$('#uploadfilef').change(function(){
			if(this.files && this.files[0]) {
				var reader = new FileReader;
		 			reader.onload = function(e) {
	 				$(".select_img").attr("src", e.target.result)
	 					.width(70).height(70); 
	 				} // onload_function
	 				reader.readAsDataURL(this.files[0]);
	 		} // if
		}); // change	
  		</script>
    </td>
  </tr>
  
  <tr height="40"><td></td>
  	  <td><input type="submit" value="전송" onclick="return inCheck()">&nbsp;&nbsp;
  	      <input type="reset" value="취소">&nbsp;&nbsp;
  </tr>
</table></form>
<c:if test="${message != null}">
	<hr><br>
	=> ${message}
</c:if>
<hr>
<a href="home">[Home]</a>
</body>
</html>