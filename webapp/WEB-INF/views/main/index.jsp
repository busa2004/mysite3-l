<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<c:import url="/WEB-INF/views/includes/favicon.jsp" />

<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.servletContext.contextPath }/assets/css/main.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		
		<div id="wrapper">
			<div id="content">
				<!--  <div id="site-introduction">  -->
					<img id="profile"
						src="${pageContext.servletContext.contextPath }/assets/images/gom.jpg"><br/>
				<!-- 	<h2>안녕하세요. 안대혁의 mysite에 오신 것을 환영합니다.</h2> -->
				 	<p>
					
						<br> <a href="${pageContext.servletContext.contextPath }/guestbook">방명록</a>에 글 남기기<br><br>
					</p> 
				<!--</div> -->
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" >
			<c:param name="menu" value="main"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>