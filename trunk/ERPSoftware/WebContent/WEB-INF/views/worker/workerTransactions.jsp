<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:url value="/resources/reports/table.css" var="tablecss" />
<link href="${tablecss}" rel="stylesheet" />
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
	<h3>
		Transactions for
		<c:out value="${workerBean.workerName}" />
	</h3>
	<c:if test="${!empty transactions}">
		<div class="scrollingtable">
			<div>
				<div>
					<table>
						<thead>
							<tr>
								<th><div label="Id"></div></th>
								<th><div label="Date"></div></th>
								<th><div label="Production Order Cost"></div></th>
								<th><div label="Production Invoice Cost"></div></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${transactions}" var="transaction">
								<tr>
									<td><c:out value="${transaction.transactionId}" /></td>
									<td><c:out value="${transaction.date}" /></td>
									<td><c:if test="${transaction.orderCost >0 }">
											<c:out value="${transaction.orderCost}" />
										</c:if></td>
									<td><c:if test="${transaction.invoiceCost > 0}">
											<c:out value="${transaction.invoiceCost}" />
										</c:if></td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan="3" align="right">Balance</td>
								<td><c:out value="${workerBean.balance}" /></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</c:if>
</body>
</html>