<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:url value="/resources/overlay/jquery-1.11.2.min.js" var="jqueryjs" />
<script type="text/javascript" src="${jqueryjs}"></script>
<c:url value="/resources/js/variantShow.js" var="variantjs" />
<script type="text/javascript" src="${variantjs}"></script>
<c:url value="/resources/reports/table.css" var="tablecss" />
<link href="${tablecss}" rel="stylesheet" />
<c:url value="/resources/css/global.css" var="stylecss" />
<link href="${stylecss}" rel="stylesheet" />
<c:url value="/resources/js/global.js" var="confirmjs" />
<script type="text/javascript" src="${confirmjs}"></script>
<c:url value="/resources/img/plus.png" var="variantImg" />
<c:url value="/resources/img/minus.png" var="variantmImg" />
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
	<h3>Frequently Purchased Products Category Wise</h3>
	<c:if test="${!empty customers}">
		<div class="scrollingtable">
			<div>
				<div>
					<table cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th><div label="Products"></div></th>
								<th><div label="Customer Name"></div></th>
								<th><div label="Company Name"></div></th>
								<th><div label="Company Branch"></div></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${customers}" var="customer">
								<tr>
									<td><a href="#" data-showpopup="${customer.customerId}"><img class="arrowRotate" data-swap="${variantImg}"
								src="${variantImg}" data-src="${variantmImg}" /></td>
									<td><c:out value="${customer.customerName}" /></td>
									<td><c:out value="${customer.companyName}" /></td>
									<td><c:out value="${customer.companyBranch}" /></td>
								</tr>
								<tr>
									<td colspan="4" style="display: none;padding: 10px;" class="variant${customer.customerId}">
										<%-- <div style="display: none;padding: 10px;" class="variant${customer.customerId}"> --%>
											<table border="1" cellpadding="2px" cellspacing="0" align="center">
												<tr>
													<td>Category Name</td>
													<td>Category Code</td>
													<td>Quantity</td>
												</tr>
												<c:forEach items="${customer.categoryBeans}" var="category">
													<tr>
														<td><c:out value="${category.categoryName}" /></td>
														<td><c:out value="${category.categoryCode}" /></td>
														<td><c:out value="${category.quantity}" /></td>
													</tr>
												</c:forEach>
											</table>
										<!-- </div> -->
									</td>								
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</c:if>
</body>
</html>
