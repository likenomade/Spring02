<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** binsertForm **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
<script src="resources/myLib/jquery-3.2.1.min.js"></script>
<script src="resources/myLib/inCheck.js"></script>

</head>
<body>
<h2>** Spring binsertForm **</h2>
<form action="binsert" method="post"><table>
  <tr height="40"><td bgcolor="aqua">I D</td>
       <td><input type ="text" name="id" value="${loginID}" readonly="readonly"></td>
  </tr>
  <tr height="40"><td bgcolor="aqua">Title</td>
   <td><input type="text" name="title" ></td>
  </tr>
  <tr height="40"><td bgcolor="aqua">Content</td>
   <td><textarea rows="10" cols="40" name="content">}</textarea></td>
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