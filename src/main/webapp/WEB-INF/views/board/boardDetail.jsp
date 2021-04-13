<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring MVC2 BoardDetail **</title>
<link rel="stylesheet" type="text/css" href="resouces/myLib/myStyle.css">
</head>
<body>
<h2>** Spring MVC2 BoardDetail **</h2>
<table>
   <tr height="40"><td bgcolor="yellow">Seq</td>
      <td>${boardVO.seq}</td></tr>
         <tr height="40"><td bgcolor="yellow">Id</td>
      <td>${boardVO.id}</td></tr>
   <tr height="40"><td bgcolor="yellow">Title</td>
      <td>${boardVO.title}</td></tr>

   <tr height="40"><td bgcolor="yellow" >Content</td>
      <td><textarea rows="10" cols="40" readonly="readonly">${boardVO.content}</textarea></td>
   </tr>
   <tr height="40"><td bgcolor="yellow">Regdate</td>
      <td>${boardVO.regdate}</td></tr>
   <tr height="40"><td bgcolor="yellow">Count</td>
      <td>${boardVO.cnt}</td></tr>
</table>

<c:if test="${message!=null}">
<hr>
=> ${message}
</c:if>
<hr>

<c:if test="${loginID!=null}">
	<a href="binsertf">[새글등록form]</a>&nbsp;
	<a href="rinsertf?root=${boardVO.root}&step=${boardVO.step}&indent=${boardVO.indent}">[답글등록form]</a>&nbsp;
</c:if>

<c:if test="${boardVO.id==loginID}">
   <a href="bdetail?seq=${boardVO.seq}&jcode=U">글수정</a>&nbsp;
   <a href="bdelete?seq=${boardVO.seq}&root=${boardVO.root}">글삭제</a>&nbsp;
  		 												<!-- 삭제시에 원글 or 댓글 구분을 위해 root 추가-->
   <a href="mlogout">Logout</a>&nbsp;
</c:if>
<br><hr>
<a href="mlist">mList</a>&nbsp;
<a href="blist">bList</a>&nbsp;
<%-- <a href="bdetail?seq=${boardVO.seq}&jcode=U">글수정</a>&nbsp; --%>
<a href="home">Home</a>&nbsp;
</body>
</html>
</html>