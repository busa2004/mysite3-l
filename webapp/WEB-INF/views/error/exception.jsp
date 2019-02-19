<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>죄송합니다.</h1>
	<p>
		사용자 요청 증가로 인해 서비스에 일시적인 장애가 발생했습니다.<br>
		잠시후 다시 요청해 주세요<br>
	</p>
	
	<strong>예외 발생</strong>
	<p>
	===================================================<br>
	</p>
	
	<p style="color:#f00">
	${errors}
	</p>
		
</body>
</html>