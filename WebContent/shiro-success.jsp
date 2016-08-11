<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
	<h4>Success Page!</h4>
	Welcome:<shiro:principal/>
	<br><br>
	<shiro:hasAnyRoles name="user">
		<a href="user.jsp">User Page</a>
	</shiro:hasAnyRoles>
	<br><br>
	<shiro:hasAnyRoles name="admin">	
		<a href="admin.jsp">Admin Page</a>
	</shiro:hasAnyRoles>
	<br><br>
	<a href="shiro-logout">Logout</a>
</body>
</html>