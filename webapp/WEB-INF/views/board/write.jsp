<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<c:import url="/WEB-INF/views/includes/favicon.jsp" />
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
$(function(){
	$("#board-form").submit(function(){
		if($("#title").val() == ""){
			alert("제목은 필수 입력 항목입니다.");
			$("#title").focus();
			return false;
		}
		
		if($("#contents").val() == ""){
			alert("내용 필수 입력 항목입니다.");
			$("#contents").focus();
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
			<div id="board">
				<form class="board-form" method="post" action="${pageContext.servletContext.contextPath}/board/write" enctype="multipart/form-data">
					<c:choose>
					<c:when test='${null eq no}'>
					<input type = "hidden" name = "a" value="write">
					</c:when>
					<c:otherwise>
					<input type = "hidden" name = "a" value="reply">
					<input type = "hidden" name = "no" value="${no}">					
					</c:otherwise>
					</c:choose>
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글쓰기</th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td><input type="text" name="title" id="title" value=""></td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<textarea id="contents" name="contents" ></textarea>
							</td>
							
						</tr>
						<tr>
							<td colspan="2"><input type="file" value="파일 선택" name="file"/></td>
						</tr>
						
					</table>
					<div class="bottom">
						<a href="${pageContext.servletContext.contextPath}/board">취소</a>
						<input type="submit" value="등록">
					</div>
				</form>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>