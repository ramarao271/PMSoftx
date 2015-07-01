<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="pl" xml:lang="pl">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>ERP Software</title>
<c:url value="/resources/css/login.css" var="logincss"/> 
<link href="${logincss}" rel="stylesheet"/>
<script type="text/javascript">
function checkFrame()
{
	var isInIframe = window.frameElement && window.frameElement.nodeName == "IFRAME";
	if(isInIframe)
		{
			parent.location.href="/ERPSoftware/";
		}
}

</script>
</head>
<body onload="checkFrame()">
<div class="wrap">
	<div id="content">
		<div id="main">
			<div class="full_w">
				<spring:form action="/ERPSoftware/main/authenticate" method="post" commandName="userBean">
					<label for="login">Username:</label>
					<spring:input path="username" class="text" id="login" />
					<label for="pass">Password:</label>
					<spring:password id="pass" path="password" class="text" />
					<div class="sep"></div>
					<button type="submit" class="ok">Login</button> <a class="button" href="">Forgotten password?</a>
				</spring:form>
			</div>
			<div class="footer"><p style="color: red;font-size: larger;"><c:if test="${!empty errorMessage}"><c:out value="${errorMessage}" /></c:if></p></div>
		</div>
	</div>
</div>
</body>
</html>
