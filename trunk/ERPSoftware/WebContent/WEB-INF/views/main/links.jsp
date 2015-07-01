<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="jst" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jst:url value="/resources/css/global.css" var="stylecss" />
<style type="text/css">
a {
	font-size: 12px;
	text-decoration: none;
	color: black
}
</style>
</head>
<body>
	<jst:if test="${!empty entries}">
		<jst:forEach items="${entries}" var="entry">
			<div>
				<a href='<jst:out value="${entry.key}" />' target="mainFrame"><jst:out
						value="${entry.value}" /></a>
			</div>
			<br />
		</jst:forEach>
	</jst:if>
</body>
</html>