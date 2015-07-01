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
			document.getElementById("expenseName").value="";
			document.getElementById("expenseId").value=0;
		}
	}
</script>

</head>
<body onload="load()">
	<h4>
		Expense
	</h4>
	<form:form method="POST" action="/ERPSoftware/expense/saveExpense.html"
		name="cat">
		<table>
			<tr>
				<td><form:label path="expenseName">Expense</form:label></td>
				<td><form:input path="expenseName"
						value="${expense.expenseName}" /><form:errors path="expenseName" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit" /></td>
				<td><form:input path="expenseId" cssStyle="visibility:hidden"
						value="${expense.expenseId}" /></td>
			</tr>
		</table>
		<form:hidden path="expenseCode" value="${expense.expenseCode}" />
	</form:form>
	<c:if test="${!empty expenses}">
		<h4>Categories List</h4>
		<table align="left" border="1" cellpadding="0" cellspacing="0">
			<tr>
				<th>Sno</th>
				<th>Expense</th>
				<th>Actions</th>
			</tr>
			<c:forEach items="${expenses}" var="expense" varStatus="id">
				<tr>
					<td><c:out value="${id.index+1}" /></td>
					<td><c:out value="${expense.expenseName}" /></td>
					<td align="center"><a
						href="/ERPSoftware/expense/editExpense.html?expenseId=${expense.expenseId}"><img src="${editImg}" /></a>
						| <a
						href="/ERPSoftware/expense/deleteExpense.html?expenseId=${expense.expenseId}"
						onclick="return checkDelete()"><img src="${deleteImg}" /></a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>