<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>All Products</title>
<c:url value="/resources/overlay/jquery-1.11.2.min.js" var="jqueryjs" />
<script type="text/javascript" src="${jqueryjs}"></script>
<c:url value="/resources/js/variantShow.js" var="variantjs" />
<script type="text/javascript" src="${variantjs}"></script>
<c:url value="/resources/css/global.css" var="stylecss" />
<link href="${stylecss}" rel="stylesheet" />
<c:url value="/resources/js/global.js" var="confirmjs" />
<c:url value="/resources/img/b_delete.png" var="deleteImg" />
<c:url value="/resources/img/b_edit.png" var="editImg" />
<c:url value="/resources/img/b_add.png" var="addImg" />
<c:url value="/resources/img/plus.png" var="variantImg" />
<c:url value="/resources/img/minus.png" var="variantmImg" />
<script type="text/javascript" src="${confirmjs}"></script>
<%-- <c:url value="/resources/reports/table.css" var="tablecss" />
<link href="${tablecss}" rel="stylesheet" />
 --%>
</head>
<body>
	<c:if test="${!empty products}">
		<!-- <div class="scrollingtable">
			<div>
				<div> -->
					<table height="10%" border="1" cellpadding=0 cellspacing=0>
						<thead>
							<tr>
								<th>Product Code</th>
								<th>Product Name</th>
								<th>Total Quantity</th>
								<th>Total Amount</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${products}" var="product">
								<tr>
									<td><c:out value="${product.productCode}" /></td>
									<td><c:out value="${product.productName}" /></td>
									<td><c:out value="${product.qty}" /></td>
									<td><c:out value="${product.totalCost}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
		<!-- 		</div>
			</div>
		</div> -->
	</c:if>
</body>
</html>