<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** joinForm **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
<script src="resources/myLib/jquery-3.2.1.min.js"></script>
<script src="resources/myLib/inCheck.js"></script>
<script src="resources/myLib/axTest01.js"></script>

<script>
//** 입력 오류 확인 ( inCheck )
//1. 개별적 오류 확인을 위한 switch 변수 정의
var iCheck=false;
var pCheck=false;
var nCheck=false;
var bCheck=false;
var poCheck=false;
var wCheck=false;

//2. 개별적 focusout 이벤트리스너 function 작성 : JQuery,
$(function(){
	$('#id').focus();
	$('#id').focusout(function() {
		iCheck=idCheck();
	}); //id
	
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
	if (iCheck==false) {
		$('#iMessage').html(' ID 를 확인 하세요 ~~');
		$('#id').focus();
	}
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
	if (iCheck==true && pCheck==true && nCheck==true &&
		bCheck==true && poCheck==true && wCheck==true	) {
		alert('~~ 입력 성공, 전송하시겠습니까?');
	}else {
        return false;   // submit 무효화 
	}
} //inCheck

//** 4. ID 중복 확인하기 
function idDupCheck() {
	// => id 입력값의 무결성 확인
	if (iCheck==false) {
		iCheck=idCheck();
	}else {
		// => 서버로 요청보내어 중복확인 , 결과 처리 
		// => window.open()
		//    url 요청을 서버로 전달하고 그 결과를 open 해줌.
		var url="idCheck?id="+$('#id').val();
		window.open(url,"_blank",
			"toolbar=no,menubar=yes,scrollbars=yes,resizable=yes,width=500,height=400");
	}
} //idDupCheck

</script>

</head>
<body>
<!-- <h2>** Spring MVC2 JoinForm **</h2> -->
<h2>** Spring MVC2 JoinForm **</h2>
<form action="mjoin" method="post" id="myForm" enctype="multipart/form-data" ><table>
  <tr height="40"><td bgcolor="pink">I  D</td>
  	  <td><input type="text" name="id" id="id">&nbsp;&nbsp;
  	  	  <input type="button" value="ID중복확인" id="idDup" onclick="idDupCheck()">	
  	  	  <br><span id=iMessage class="message"></span></td>
  </tr>
  <tr height="40"><td bgcolor="pink">Password</td>
  	  <td><input type="password" name="password" id="password" size="10"><br>
		<span  id="pMessage" class="message"></span></td>
  </tr>
  <tr height="40"><td bgcolor="pink">Name</td>
	<td><input type="text" name="name" id="name" value="" size="10"><br>
		<span  id="nMessage" class="message"></span></td>
	</tr>
  </tr>
  <tr height="40"><td bgcolor="pink">Level</td>
  	  <td><select name="lev" id="lev">
				<option value="A">관리자</option>
				<option value="B">나무</option>
				<option value="C">잎새</option>
				<option value="D" selected="selected">새싹</option>
		  </select></td>
  </tr>
  <tr height="40"><td bgcolor="pink">BirthDay</td>
	<td><input type="date" name="birthd" id="birthd" ><br>
		<span id="bMessage" class="message"></span></td>
  </tr>
  <tr height="40"><td bgcolor="pink">Point</td>
  	  <td><input type="text" name="point" id="point" value="" size="10"><br>
		<span id="poMessage" class="message"></span></td>
  </tr>
  <tr height="40"><td bgcolor="pink">Weight</td>
  	  <td><input type="text" name="weight" id="weight" value="" size="10"><br>
		  <span id="wMessage" class="message"></span></td>
  </tr>
  <tr height="40"><td bgcolor="pink">추천인</td>
  	  <td><input type="text" name="rid" id="rid"></td>
  </tr>
  <tr height="40"><td bgcolor="pink">Image</td>
  	<td><img src="" class="select_img" /><br>
  		<input type="file" name="uploadfilef" id="uploadfilef">
  	<script>
		// 해당 파일의 서버상의 경로를 src로 지정하는것으로는 클라이언트 영역에서 이미지는 표시될 수 없기 때문에
		// 이를 해결하기 위해 FileReader이라는 Web API 를 사용
		// => 이 를 통해 url data를 얻을 수 있음.
		//    ( https://developer.mozilla.org/ko/docs/Web/API/FileReader)
		// ** FileReader
		// => 웹 애플리케이션이 비동기적으로 데이터를 읽기 위하여 읽을 파일을 가리키는File
		//    혹은 Blob 객체를 이용해 파일의 내용을(혹은 raw data버퍼로) 읽고 
		//    사용자의 컴퓨터에 저장하는 것을 가능하게 해줌.	
		// => FileReader.onload 이벤트의 핸들러.
		//    읽기 동작이 성공적으로 완료 되었을 때마다 발생.
		// => e.target : 이벤트를 유발시킨 DOM 객체
  		
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
  	  <td><!-- <input type="submit" value="전송" disabled >&nbsp;&nbsp; -->
  	  	  <input type="submit" id="submit" value="전송" disabled onclick="return inCheck()">
  	      <input type="reset" value="취소">&nbsp;&nbsp;
  	      <span id="ajoin" class="textLink">AxJoin</span>
  	  </td>
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