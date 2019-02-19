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
<link href="${pageContext.servletContext.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
<style>
#right{
float:right;
}
textarea{
width:100%;
}
</style>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${vo.title}</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${fn:replace(vo.contents,newline,"<br>")}
							</div>
						</td>
					</tr>
					
					<c:if test="${fileName ne ''}">
					<tr>
					<td colspan="2"><img src="${filePath}"></td>
					
					</tr>
					<tr>
					<td colspan="2"><a href="${pageContext.servletContext.contextPath}/board/download?fileName=${fileName}">다운로드</a></td>
					</tr>
					</c:if>
					
				</table>
				
				<!-- 댓글 생성  -->
				<form class="board-form" method="post" action="${pageContext.servletContext.contextPath}/board/comment?authUserNo=${authuser.no}&boardNo=${vo.no}">
				<ul>
					
					<li>
						<table class="tbl-ex">
							<tr>
								<th colspan="4">댓글</th>
							</tr>
							
							
							<!-- 입력 -->			
							<c:if test="${authuser ne null }">	
							
							<tr >
							<td colspan="3">${authuser.name}</td>
							<td colspan="1"><input type="submit" value="댓글 입력" id="right"></td>
							</tr>
							
							
							<tr>
							<td colspan="4"><textarea name="contents" ></textarea></td>
							</tr>
							</c:if>
							<!--  -->
			
						
						<!-- 리스트 -->		
						<c:set var="count" value="${fn:length(commentList)}"/>
						<c:forEach items="${commentList}" var="v" varStatus="status">
							<tr >
								<td>[${count-status.index}]</td>
								<td>${ v.name}</td>
								<td>${ v.regDate }</td>
								<c:if test="${authuser.no == v.userNo }">
								<td id="right"><a href="${pageContext.servletContext.contextPath }/board/commentremove?boardNo=${vo.no}&no=${v.no}"><img src="${pageContext.servletContext.contextPath }/assets/images/recycle.png"/></a></td>
								</c:if>
							</tr>
							<tr>
								<td colspan="4">
								${fn:replace(v.contents,newline,"<br>")}
								</td>
							</tr>
						</c:forEach>
						<!--  -->	
						
						
						
						</table>
						<br>
					</li>
					
				</ul>
				</form>
				<!-- 댓글 생성  -->
				
				<div class="bottom">
					<a href="${pageContext.servletContext.contextPath}/board">글목록</a>
					<c:if test='${vo.userNo eq authuser.no }'>
					<a href="${pageContext.servletContext.contextPath}/board/modify?no=${vo.no}">글수정</a> <!-- 자기글 판단 -->
					
					<a href="${pageContext.servletContext.contextPath}/board/reply?no=${vo.no}">답글</a><!--  답글 달기  -->
					</c:if>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>