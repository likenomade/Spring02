<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring_BoardList **</title>
<link rel="stylesheet" type="text/css"
	href="resources/myLib/myStyle.css">
</head>
<body>
	<h2>** Spring MVC2 BoardList **</h2>
	<c:if test="${message!=null}">
=> ${message}
</c:if>
	<hr>
	<table width=800 border="1">
		<tr align="center" height="30" bgcolor="aqua">
			<td>Seq</td>
			<td>Title</td>
			<td>ID</td>
			<td>Regdate</td>
			<td>Count</td>
		</tr>
		<c:forEach var="row" items="${Banana}">
			<tr>
				<td>${row.seq}</td>
				<td>
					<!-- 답글 등록 후 indent 값에 따른 들여쓰기 --> <c:if test="${row.indent>0}">
						<c:forEach begin="1" end="${row.indent}">
							<span>&nbsp;&nbsp;</span>
						</c:forEach>
						<span style="color: orange">re..</span>
					</c:if> <a href="bdetail?seq=${row.seq }">${row.title}</a>
				</td>
				<td>${row.id }</td>
				<td>${row.regdate}</td>
				<td>${row.cnt}</td>
			</tr>
		</c:forEach>
	</table>
	<hr>
	<c:if test="${loginID!=null}">
		<a href="binsertf">[새글등록form]</a>&nbsp;
</c:if>
	<a href="home">[Home]</a>
</body>
</html>