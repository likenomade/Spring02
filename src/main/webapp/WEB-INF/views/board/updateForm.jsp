<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spring MVC2 Board_UpdateForm</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
<script src="resources/myLib/jquery-3.2.1.min.js"></script>
</head>
<body>
<h2>** Spring MVC2  Board_UpdateForm **</h2>
<form action="bupdate" method="post"><table>
  <tr height="40"><td bgcolor="aqua">Seq</td>
       <td><input type="text" name="seq" id="seq" readonly value="${boardVO.seq}"></td>
       <!-- disabled: value가 서버로 전송되지않음 , readonly: value가 서버로 전송됨 -->
  </tr>
  <tr height="40"><td bgcolor="aqua">I D</td>
       <td>${boardVO.id}</td>
  </tr>
  <tr height="40"><td bgcolor="aqua">Title</td>
   <td><input type="text" name="title" value="${boardVO.title}"></td>
  </tr>
  <tr height="40"><td bgcolor="aqua">Content</td>
   <td><textarea rows="10" cols="40" name="content">${boardVO.content}</textarea></td>
  </tr>
  <tr height="40"><td bgcolor="aqua">RegDate</td>
       <td>${boardVO.regdate}</td>
  </tr>
  <tr height="40"><td bgcolor="aqua">Count</td>
       <td>${boardVO.cnt}</td>
  </tr>
  <tr height="40"><td></td>
       <td><input type="submit" value="전송">&nbsp;&nbsp;
           <input type="reset" value="취소">&nbsp;&nbsp;
  </tr>
</table></form>
<c:if test="${message != null}">
   <hr><br>
   => ${message}
</c:if>
<hr>
<a href="mlist">mList</a>&nbsp;
<a href="blist">bList</a>&nbsp;
<a href="home">Home</a>&nbsp;
</body>
</html>