<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
<c:url value="/resources/img/b_delete.png" var="deleteImg" />
<c:url value="/resources/img/b_edit.png" var="editImg" />
<c:url value="/resources/img/b_add.png" var="addImg" />
<c:url value="/resources/img/add-small.png" var="variantImg" />

</head>
<body onload="load()">
	<h3>Shippers List</h3>
	<c:if test="${!empty shippers}">
		<table align="left" border="1" cellpadding="0" cellspacing="0">
			<tr>
				<th>Shipper Name</th>
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
			<c:forEach items="${shippers}" var="shipper">
				<tr>
					<td><c:out value="${shipper.shipperName}" /></td>
					<td><c:out value="${shipper.companyName}" /></td>
					<td><c:out value="${shipper.companyBranch}" /></td>
					<td><c:out value="${shipper.companyTelephone1}" /></td>
					<td><c:out value="${shipper.companyMobile1}" /></td>
					<td><c:out value="${shipper.companyEmail}" /></td>
					<td align="center"><a
						href="editShipper.html?shipperId=${shipper.shipperId}"><img src="${editImg}" /></a>
						| <a href="deleteShipper.html?shipperId=${shipper.shipperId}"
						onclick="return checkDelete()"><img src="${deleteImg}" /></a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

</body>
</html>