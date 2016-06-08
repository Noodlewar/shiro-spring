<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h4>shiro login page!</h4>
	<form action="shiro-login" method="post">
		username:<input type="text" name="username">
		password:<input type="password" name="password">
		<input type="submit" value="Submit">
	</form>
</body>
</html>