<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring_MemberList **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
<script src="resources/myLib/jquery-3.2.1.min.js"></script>	
 <script src="resources/myLib/axTest01.js"></script> 
<script src="resources/myLib/axTest02.js"></script>	  
</head>
<body>
<h2>** Spring MVC2 AjaxMemberList **</h2>
<c:if test="${message!=null}">
=> ${message}
</c:if>
<hr>
<table width=800 border="1" >
	<tr align="center" height="30" bgcolor="pink">
	<td>I D</td><td>Password</td><td>Name</td><td>Level</td><td>Birthday</td>
	<td>Point</td><td>Weight</td><td>추천인</td><td>Image</td><td>Delete</td>
	</tr>
	
 <!-- 반복문에 이벤트 적용하기 
  => Ajax 또는 아닌  경우 모두 적용   
  1) JS : Tag 의  onclick 이벤트 이용 (a 포함 모든태그)
  	-> id 전달 : function의 매개변수 이용  , aidbList('banana') 
  	-> a Tag 이용 : href="javascript:;" 또는  href="#"  비교
			<a href="#"            .... scroll 위치 이동 
				href에 #id를 주게되면 id에 해당하는 element에 포커스를 맞추게 되는데,
     			만약 #만 있고 id가 없을 경우에는 포커스가 top으로 올라감.
			<a href="javascript:;" .... 사용하면 해결
	<td><a href="javascript:;" onclick="aidblist('${list.id}')">${list.id}</a></td>
  		<!-- => id 가 banana 인 글 목록 출력 : aidbList('banana') -->		
  
<!--
  2) JQ : class 와 id 를 이용
  => id의 value를 직접 지정하면 이벤트 핸들러로 id를 사용할 때 유일성 보장이 안됨
     <td><span id="iii" class="textLink">${row.id}</span></td>
  => 모든 row 에 이벤트를 적용하면서 (class 사용)
     해당 row 의 값을 인식할 수 있어야 함 (id 활용)
  -->	
	
  <c:forEach var="row" items="${Banana}">
	<tr>
	<!-- test1) JS function -->
	
	<%-- aidbList(${row.id}) : aidbList(banana) -> xxxx --%>
	  <td><a href="#" onclick="aidbList('${row.id}')">${row.id}</a></td>
	
	<!-- test2) JQ , id ,class -->
	<%-- <td><span id="${row.id}" class="ccc textLink">${row.id}</span></td> --%>
	<td>${row.password}</td><td>${row.name}</td><td>${row.lev}</td>
	<td>${row.birthd}</td><td>${row.point}</td><td>${row.weight}</td>
	<td>${row.rid}</td>
	
	<!-- Image Download 추가 
      1) class="dnload" 를 이용하여  jQuery Ajax 로 처리 
      => 안됨 (java 클래스인  서버의 response가  웹브러우져로 전달되지 못하고 resultArea에 담겨질 뿐 )  
      <img src="${list.uploadfile}" width="50" height="60" class="dnload textLink">
      2) aTag 로  직접 요청함 
      => java 클래스인  서버의 response가  웹브러우져로 전달되어 download 잘됨. 
   -->
	
	<td><a href = "dnload?dnfile=${row.uploadfile}">
	<img src="${row.uploadfile}" width="70" height="70"></a></td>
	<!-- Delete 기능 실습 -->
<!-- 	=> 위 1) 또는 2) 방법중 선택하여 기능  완성 : axTest02.js
	=> Ajax , JsonView 를 이용하여  
	->  삭제성공 :  alert "삭제 성공" , 해당 Tag를 Deleted 로 표시하고 작동하지 않도록
	->  삭제실패 :  alert "삭제 실패" ,
	=> MemberController 에 요청명 jsdelete 추가
 -->
	<!-- <td><span class=" textLink">Delete</span></td> -->
	 <td><span id="${row.id}"  class="ddd textLink">Delete</span></td> 
	</tr>
  </c:forEach>
</table>
</body>
</html>