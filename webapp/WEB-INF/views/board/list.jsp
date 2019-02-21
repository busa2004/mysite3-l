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

</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.servletContext.contextPath}/board" method="post">
					<input type="text" id="kwd" name="kwd" value="${kwd}">
					<input type="submit" value="찾기">
				</form>
				<!-- search action 구현 x -->
				<!--  list action으로 같이 표현 -->
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
				<!--<c:set var="count" value="${fn:length(list)}"/>-->
				
				<c:forEach items="${list}" var="vo"  varStatus="status">				
					<tr>
						<td>[${totalSize-status.index-page*10}]</td>
						<c:choose>
						<c:when test='${vo.removeCheck eq "n" }'>
						<td style="padding-left:${15*vo.depth}px">
						<c:if test='${vo.depth ne 0 }'>
						<img src="assets/images/reply.png"/>
						</c:if>
						<a href="${pageContext.servletContext.contextPath}/board/view?no=${vo.no}">${vo.title}</a></td>
						<td>${ vo.userName}</td>
						<td>${ vo.hit}</td>
						<td>${ vo.writeDate}</td>
						<td>
						<c:if test='${vo.userNo eq authuser.no }'>
						<a href="${pageContext.servletContext.contextPath}/board/remove?no=${vo.no}" class="del"><img src="assets/images/recycle.png"/></a>
						</c:if>
						</td> <!-- 자기글 판단 -->
						</c:when>
						<c:otherwise>
						<td colspan="5">삭제된 글입니다.</td>
						</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
				</table>
				<!-- 시작 부분 서블릿에서 계산에서 보내줌 -->
				<!--  페이지 카운트도 필요  -->
				<!-- pager 추가 -->
				
						
				<div class="pager">
					<ul>
					<li><a href="${pageContext.servletContext.contextPath}/board?page=${page+1}&movePage=${movePage}&move=-1&kwd=${kwd}">◀</a></li>
						<c:forEach var="i" begin="${1+movePage}" end="${5+movePage}" step="1"> 
						<c:choose>
						<c:when test = "${i<=totalPage}">
						<c:choose>
						<c:when test = "${i==page+1}">
						<li class="selected">${i}</li>
						</c:when>
						<c:otherwise>
						<li><a href="${pageContext.servletContext.contextPath}/board?page=${i}&movePage=${movePage}&kwd=${kwd}">${i}</a></li>
						</c:otherwise>
						</c:choose>
						</c:when>
						<c:otherwise>
						<li>${i}<li>
						</c:otherwise>
						</c:choose>
						</c:forEach>
					<li><a href="${pageContext.servletContext.contextPath}/board?page=${page+1}&movePage=${movePage}&move=1&kwd=${kwd}">▶</a></li>
					</ul>
				</div>				
						
						
											
				<!-- pager 추가 -->
				<c:if test='${null ne authuser }'>
				<div class="bottom">
					<a href="${pageContext.servletContext.contextPath}/board/write" id="new-book">글쓰기</a> <!-- 글쓰기 접근 제어, 및 버튼 로그인 여부 판단 -->
				</div>	
				</c:if>			
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>