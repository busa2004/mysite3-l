
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("newline","\n");
%>
<!DOCTYPE html>

<html>
<head>
<c:import url="/WEB-INF/views/includes/favicon.jsp" />
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
$(function(){
	$("#guestbook-form").submit(function(){
		if($("#name").val() == ""){
			alert("이름은 필수 입력 항목입니다.");
			$("#name").focus();
			return false;
		}
		
		if($("#password").val() == ""){
			alert("비밀번호는 필수 입력 항목입니다.");
			$("#password").focus();
			return false;
		}
		
		if($("textarea").val() == ""){
			alert("내용은 필수 입력 항목입니다.");
			$("textarea").focus();
			return false;
		}
		
		return true;
	});
});
</script>
</head>
<body>
	<div id="container">
	<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="guestbook">
				<form  id="guestbook-form" action="${pageContext.servletContext.contextPath }/guestbook/add" method="post">
					<input type="hidden" name="a" value="add">
					<table>
						<tr>
							<td>이름</td><td><input type="text" name="name" id="name"></td>
							<td>비밀번호</td><td><input type="password" name="password" id="password"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="message" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<ul>
					<c:set var="count" value="${fn:length(list)}"/>
					<c:forEach items="${list}" var="vo" varStatus="status">
					<li>
						<table>
							<tr>
								<td>[${count-status.index}]</td>
								<td>${ vo.name}</td>
								<td>${ vo.regDate }</td>
								<td><a href="${pageContext.servletContext.contextPath }/guestbook/delete?no=${vo.no}">삭제</a></td>
							</tr>
							<tr>
								<td colspan=4>
								${fn:replace(vo.message,newline,"<br>")}
								</td>
							</tr>
						</table>
						<br>
					</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>