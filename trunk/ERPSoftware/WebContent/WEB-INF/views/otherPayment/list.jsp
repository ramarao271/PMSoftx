<%-- <%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach items="${workers}" var="sup">
<c:out value="${sup}" />	
</c:forEach>
	<%-- 
<%
	String countries[] = { "Afghanistan", "Albania", "Algeria",
			"Andorra", "Angola", "Antigua and Barbuda", "Argentina",
			"Armenia", "Yemen", "Zambia", "Zimbabwe" };

	String query = (String) request.getParameter("q");
	//System.out.println("1"+request.getParameterNames().nextElement());
	response.setHeader("Content-Type", "text/html");
	int cnt = 1;
	for (int i = 0; i < countries.length; i++) {
		if (countries[i].toUpperCase().startsWith(query.toUpperCase())
				|| countries[i].toUpperCase().contains(
						query.toUpperCase())) {
			out.print(countries[i] + "\n");
			if (cnt >= 10)
				break;
			cnt++;
		}
	}
%> --%>