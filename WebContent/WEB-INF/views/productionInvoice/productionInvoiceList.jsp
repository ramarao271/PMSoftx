<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:url value="/resources/css/global.css" var="stylecss" />
<c:url value="/resources/img/b_delete.png" var="deleteImg" />
<c:url value="/resources/img/b_edit.png" var="editImg" />
<c:url value="/resources/img/b_add.png" var="addImg" />
<c:url value="/resources/img/add-small.png" var="variantImg" />

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
	<h3>Production Invoice List</h3>
	<c:if test="${!empty productionInvoices}">
		<table align="left" border="1" cellpadding="0" cellspacing="0">
			<tr>
				<th>PO No</th>
				<th>Date</th>
				<th>Company</th>
				<th>Total Amount</th>
				<!-- <th>Amount Paid</th> -->
				<th>Actions</th>
			</tr>
			<c:forEach items="${productionInvoices}" var="productionInvoice">
				<tr>
					<td><c:out value="${productionInvoice.productionInvoiceId}" /></td>
					<td><fmt:formatDate pattern="MM-dd-yyyy"
							value="${productionInvoice.productionInvoiceDate}" /></td>
					<td><c:out value="${productionInvoice.workerBean.workerName}" /></td>
					<td><c:out value="${productionInvoice.totalCost}" /></td>
					<%-- <td><c:out value="${productionInvoice.paidAmount}" /></td> --%>
					<td align="center"><a
						href="editProductionInvoice.html?productionInvoiceId=${productionInvoice.productionInvoiceId}"><img src="${editImg}" /></a>
						| <a
						href="deleteProductionInvoice.html?productionInvoiceId=${productionInvoice.productionInvoiceId}"
						onclick="return checkDelete()"><img src="${deleteImg}" /></a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

</body>
</html>