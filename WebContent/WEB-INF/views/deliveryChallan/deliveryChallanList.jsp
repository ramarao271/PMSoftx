<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	<h3>Delivery Challans List</h3>
	<c:if test="${!empty deliveryChallans}">
		<table align="left" border="1" cellpadding="0" cellspacing="0">
			<tr>
				<th>DC No</th>
				<th>Date</th>
				<th>Company</th>
				<th>Total Amount</th>
				<th>Actions</th>
			</tr>
			<c:forEach items="${deliveryChallans}" var="deliveryChallan">
				<tr>
					<td><c:out value="${deliveryChallan.deliveryChallanId}" /></td>
					<td><fmt:formatDate pattern="MM-dd-yyyy"
							value="${deliveryChallan.deliveryChallanDate}" /></td>
					<td><c:out value="${deliveryChallan.customerBean.companyName}" /></td>
					<td><c:out value="${deliveryChallan.totalCost}" /></td>
					<td align="center"><a
						href="editDeliveryChallan.html?deliveryChallanId=${deliveryChallan.deliveryChallanId}"><img src="${editImg}" /></a>
						| <a
						href="deleteDeliveryChallan.html?deliveryChallanId=${deliveryChallan.deliveryChallanId}"
						onclick="return checkDelete()"><img src="${deleteImg}" /></a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

</body>
</html>