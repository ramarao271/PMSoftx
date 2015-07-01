<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:url value="/resources/img/b_delete.png" var="deleteImg" />
<c:url value="/resources/img/b_edit.png" var="editImg" />
<c:url value="/resources/img/b_add.png" var="addImg" />
<c:url value="/resources/img/add-small.png" var="variantImg" />

<c:url value="/resources/css/global.css" var="stylecss" />
<link href="${stylecss}" rel="stylesheet" />
<c:url value="/resources/js/global.js" var="confirmjs" />
<script type="text/javascript" src="${confirmjs}"></script>
<title>All Products</title>
<script type="text/javascript">
	function load() {
		var msg = '<c:if test="${!empty message}"><c:out value="${message}" /></c:if>';
		if (msg != "")
			alert(msg);
	}
</script>

</head>
<body onload="load()">
	<h3>Suppliers List</h3>
	<c:if test="${!empty suppliers}">
		<table align="left" border="1" cellpadding="0" cellspacing="0">
			<tr>
				<th>Supplier Name</th>
				<th>Company Name</th>
				<th>Company Branch</th>
				<th>Company Telephone1</th>
				<!-- <th>Company Telephone2</th> -->
				<th>Company Mobile1</th>
				<!-- <th>Company Mobile2</th> -->
				<th>Company Email</th>
				<!-- 
			<th>Company TIN No</th>
			<th>Company CST No</th> -->
				<!-- <th colspan="3">Address</th> -->
				<th>Actions</th>
			</tr>
			<c:forEach items="${suppliers}" var="supplier">
				<tr>
					<td><c:out value="${supplier.supplierName}" /></td>
					<td><c:out value="${supplier.companyName}" /></td>
					<td><c:out value="${supplier.companyBranch}" /></td>
					<td><c:out value="${supplier.companyTelephone1}" /></td>
					<td><c:out value="${supplier.companyMobile1}" /></td>
					<td><c:out value="${supplier.companyEmail}" /></td>
					<td align="center"><a
						href="editSupplier.html?supplierId=${supplier.supplierId}"><img src="${editImg}" /></a>
						| <a href="deleteSupplier.html?supplierId=${supplier.supplierId}"
						onclick="return checkDelete()"><img src="${deleteImg}" /></a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

</body>
</html>