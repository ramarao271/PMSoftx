<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ERP Software</title>

<c:url value="/resources/autocomplete/demos.css" var="democss" />
<link rel="stylesheet" href="${democss}">
<c:url value="/resources/js/global.js" var="globaljs" />
<script type="text/javascript" src="${globaljs}"></script>
<c:url value="/resources/css/global.css" var="stylecss" />
<link href="${stylecss}" rel="stylesheet" />
</head>
<body>
	<h3>
		<b><c:if test="${!empty operation}">
				<c:out value="${operation}" />
			</c:if> Worker Details</b>
	</h3>
	<form:form name="personForm" method="POST"
		action="/ERPSoftware/worker/saveWorker.html" modelAttribute="workerBean">
		<table>
			<tr>
				<td><form:label path="workerName">Worker Name</form:label></td>
				<td><form:input path="workerName"
						value="${workerBean.workerName}" /></td>

				<td>*<form:errors path="workerName"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="mobile">Mobile</form:label></td>
				<td><form:input path="mobile"
						value="${workerBean.mobile}"  type="number" /></td>
				<td>*<form:errors path="mobile"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="address">Address</form:label></td>
				<td><form:input path="address"
						value="${workerBean.address}" /></td>

				<td>*<form:errors path="address"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="openingBalance">Opening Balance</form:label></td>
				<td><form:input path="openingBalance"
						value="${workerBean.openingBalance}" /></td>

				<td><form:errors path="openingBalance"
						cssStyle="color: #ff0000;" /></td>
			</tr>
		</table>
		<input type="submit" value="Submit" />&nbsp;<input type="button"
			name="cancel" value="Cancel" onclick="loadIndex('worker')" />
		<form:input path="workerId" cssStyle="visibility:hidden"
			value="${workerBean.workerId}" />
	</form:form>
</body>
</html>