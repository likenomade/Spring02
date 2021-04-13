<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** ID 중복 확인 **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
<script src="resources/myLib/jquery-3.2.1.min.js"></script>
<script src="resources/myLib/inCheck.js"></script>
<script>
function idOK() {
	//opener.document.getElementById('id').value='${newID}';
	opener.$('#id').val('${newID}');
	// <script> 에서 EL은 문자열Type 내부에서 사용 가능함. 
	opener.document.getElementById('submit').disabled='';
	opener.document.getElementById('idDup').disabled='disabled';
	
	// JQ 방식으로 속성에 접근
	// => attr , prop 비교
	// => attr()는 HTML의 속성(Attribute) ,기능, 입력된 값을 취급 
	// => prop()는 JavaScript의 프로퍼티(Property), 실제값, property가 가지는 본연의 값
	//opener.$('#id').attr('readonly','readonly');
	opener.$('#id').prop('readonly',true);
	opener.$('#password').focus();
	self.close();
}
</script>
<style>
	body {
		background: #E6E6FA;
		font-family: 맑은고딕;
	}
	#wrap {
		margin-left: 0;
		text-align: center;
	}
	h3 {
		font-size: 30px;
		color: #00008B; 
		font-weight: normal;
	}
</style>

</head>
<body>
<div id="wrap">
	<h3>** ID 중복 확인 **</h3>
	<form action="idCheck" method="post">
	UserID :
	<input type="text" id="id" name="id" value="">
	<input type="submit" value="ID중복확인" onclick="return idCheck()">
				<!-- inCheck.js 의  idCheck() 의 결과에 따라 sumit 결정-->
	<span id="iMessage" class="message"></span>			
	</form>
	<br><br><hr><br>
	<div id="msgBlock">
	<c:if test="${idUse=='T'}">
		${newID} 는 사용 가능 합니다 ~~
		<input type="button" value="idOK" onclick="idOK()">
		<!-- 사용자가 입력한 id 를 사용가능하도록 해주고, 현재(this) 창은 close -->
	</c:if>
	<c:if test="${idUse=='F'}">
		${newID} 는 이미 사용중 입니다 ~~<br>
		다시 입력 하세요 ~~
		<!-- 부모창(joinForm) 에 남아있는 입력한 id를 지워주고,
		     현재(this) 창의 id 에 포커스를 주고 재입력 할 수 있도록 해준다. 
		     JS 코드가 필요함-->
		<script>
			$('#id').focus();
			opener.document.getElementById('id').value='';
		</script>     
	</c:if>
	</div>
</div>

</body>
</html>