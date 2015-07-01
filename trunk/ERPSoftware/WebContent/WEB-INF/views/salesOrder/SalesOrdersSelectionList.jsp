<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
	function loadProducts(val) {
		document.productSelection.action = "/ERPSoftware/product/ProductSelectionList/"
				+ val;
		document.productSelection.submit();
	}
	function sendSelected() {

		var salesOrderId = document.salesOrderSelection.salesOrderId.value;
		parent.setOrderDetails(salesOrderId,"deliverychallan");

	}
</script>
<style type="text/css">
body {
	color: #444444;
	font-family: Tahoma, Arial, Helvetica, sans-serif;
	font-size: 11px;
	margin: 0;
}
</style>
</head>
<body>
	<form:form method="POST" action="saveProduct.html"
		name="salesOrderSelection">
		<h3>Sales Orders List</h3>
		<input type="button" value="Select" name="selected" id="selected"
			onclick="sendSelected()" />
		<c:if test="${!empty salesOrders}">
			<table align="left" border="1" cellpadding="0" cellspacing="0">
				<tr>
					<th>Select</th>
					<th>SO No</th>
					<th>Date</th>
					<th>Company</th>
					<th>Total Amount</th>
				</tr>
				<c:forEach items="${salesOrders}" var="salesOrder">
					<tr>
						<td><input type="radio" name="salesOrderId"
							id="salesOrderId" value="${salesOrder.salesOrderId}" />
						<td><c:out value="${salesOrder.salesOrderId}" /></td>
						<td><fmt:formatDate pattern="MM-dd-yyyy"
								value="${salesOrder.salesOrderDate}" /></td>
						<td><c:out value="${salesOrder.customerBean.companyName}" /></td>
						<td><c:out value="${salesOrder.totalCost}" /></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>

	</form:form>
</body>
</html>