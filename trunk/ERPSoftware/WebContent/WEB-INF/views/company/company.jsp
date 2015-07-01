<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ERP Software</title>
<c:url value="/resources/js/global.js" var="confirmjs" />
<script type="text/javascript" src="${confirmjs}"></script>
<c:url value="/resources/img/b_delete.png" var="deleteImg" />
<c:url value="/resources/img/b_edit.png" var="editImg" />
<c:url value="/resources/img/b_add.png" var="addImg" />
<c:url value="/resources/img/add-small.png" var="variantImg" />

<c:url value="/resources/css/global.css" var="stylecss" />
<link href="${stylecss}" rel="stylesheet" />
<title>All Products</title>
<script type="text/javascript">
	function load() {
		var msg = '<c:if test="${!empty message}"><c:out value="${message}" /></c:if>';
		if (msg != "") {
			alert(msg);
			document.getElementById("companyName").value="";
			document.getElementById("companyId").value=0;
		}
	}
</script>

</head>
<body onload="load()">
	<h4>
		Company
	</h4>
	<form:form method="POST" action="/ERPSoftware/company/saveCompany.html"
		name="cat">
		<table>
			<tr>
				<td><form:label path="companyName">Company</form:label></td>
				<td><form:input path="companyName"
						value="${company.companyName}" /><form:errors path="companyName" /></td>
			</tr>
			<tr>
				<td><form:label path="balance">Cash Available</form:label></td>
				<td><form:input path="balance"
						value="${company.balance}" /><form:errors path="balance" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit" /></td>
				<td><form:input path="companyId" cssStyle="visibility:hidden"
						value="${company.companyId}" /></td>
			</tr>
		</table>
		<form:hidden path="companyCode" value="${company.companyCode}" />
	</form:form>
	<c:if test="${!empty companies}">
		<h4>Companies List</h4>
		<table align="left" border="1" cellpadding="0" cellspacing="0">
			<tr>
				<th>Sno</th>
				<th>Code</th>
				<th>Company</th>
				<th>Available Cash</th>
				<th>Actions</th>
			</tr>
			<c:forEach items="${companies}" var="company" varStatus="id">
				<tr>
					<td><c:out value="${id.index+1}" /></td>
					<td><c:out value="${company.companyCode}" /></td>
					<td><c:out value="${company.companyName}" /></td>
					<td><c:out value="${company.balance}" /></td>
					<td align="center"><a
						href="/ERPSoftware/company/editCompany.html?companyId=${company.companyId}"><img src="${editImg}" /></a>
						| <a
						href="/ERPSoftware/company/deleteCompany.html?companyId=${company.companyId}"
						onclick="return checkDelete()"><img src="${deleteImg}" /></a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>