<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="pl" xml:lang="pl">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>ERP Software</title>
<c:url value="/resources/css/login.css" var="logincss"/> 
<link href="${logincss}" rel="stylesheet"/>
</head>
<body>
<div class="wrap">
	<div id="content">
		<div id="main">
			<div class="full_w">
				<spring:form action="createUserDb" method="post">
					<label for="login">Username:</label>
					<spring:input path="username" class="text" id="login"/>
					<label for="pass">Password:</label>
					<spring:password id="pass" path="password" class="text" />
					<label for="login">Category</label>
					<spring:select path="category" >
					<spring:option value="Admin"></spring:option>
					<spring:option value="End User"></spring:option>
					</spring:select>
					<div class="sep"></div>
					<button type="submit" class="ok">Create User</button> 
				</spring:form>
			</div>
			<div class="footer"></div>
		</div>
	</div>
</div>
</body>
</html>
