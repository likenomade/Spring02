<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring MemberDetail **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>
<h2>** Spring MVC2 MemberDetail **</h2>
<h2><pre>
=> memberVO 적용

* I     D : ${memberVO.id}
* Password: ${memberVO.password}
* N a m e : ${memberVO.name}
* Level   : ${memberVO.lev}
* BirthDay: ${memberVO.birthd}
* Point   : ${memberVO.point}
* Weight  : ${memberVO.weight}
* 추천인   : ${memberVO.rid}
* Image  : <img src="${memberVO.uploadfile}" width="70" height="70" />       
</pre></h2>
<c:if test="${message!=null}">
<hr>
=> ${message}
</c:if>
<hr>
<c:if test="${memberVO.id==loginID}">
	<a href="mdetail?id=${memberVO.id}&jcode=U">Updatef</a>&nbsp;
	<a href="mdelete">Delete</a>&nbsp;
	<a href="mlogout">Logout</a>&nbsp;
</c:if>
<br><hr>
<a href="mlist">mList</a>&nbsp;
<a href="home">Home</a>&nbsp;
</body>
</html>