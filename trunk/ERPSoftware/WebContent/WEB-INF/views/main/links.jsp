<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="jst" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jst:url value="/resources/css/global.css" var="stylecss" />
<link rel="stylesheet" href="${stylecss}">
</head>
<body bgcolor="#1b1b1b">
	<jst:if test="${!empty entries}">

		<ul id='cssmenu'>
			<jst:forEach items="${entries}" var="entry">
				<li><a class="link" href='<jst:out value="${entry.key}" />'
					target="mainFrame"><span><jst:out value="${entry.value}" /></span></a></li>
			</jst:forEach>
		</ul>

	</jst:if>
</body>
</html>