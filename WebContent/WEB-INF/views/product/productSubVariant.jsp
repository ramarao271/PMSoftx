<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
<script type="text/javascript">
	function load() {
		var msg = '<c:if test="${!empty message}"><c:out value="${message}" /></c:if>';
		if (msg != "")
			alert(msg);
	}
</script>
</head>
<body onload="load()">

	<h3>Products List</h3>
	<c:if test="${!empty products}">
		<table align="left" border="1" cellpadding="2px" cellspacing="0">
			<tr>
				<th>Stages</th>
				<th>Product Code</th>
				<th>Product Name</th>
				<th>Category</th>
				<th>Quantity</th>
				<th>Image</th>
				<th>Actions</th>
			</tr>
			<c:forEach items="${products}" var="product">
				<tr>
					<td><c:if test="${! empty product.variantBeans }">
							<a href="#" data-showpopup="${product.productId}"><img
								class="arrowRotate" data-swap="${variantImg}"
								src="${variantImg}" data-src="${variantmImg}" /></a>
						</c:if></td>
					<td><c:out value="${product.productCode}" /></td>
					<td><c:out value="${product.productName}" /></td>
					<td><c:out value="${product.categoryBean.categoryName}" /></td>
					<td><c:out value="${product.quantity}" /> <c:out
							value="${product.measurementBean.measurementName}" /></td>
					<td><img src="${product.imagePath}" height="50px" width="50px" /></td>
					<td align="center"><a
						href="editProduct.html?productId=${product.productId}"><img
							src="${editImg}" /></a> | <a
						href="deleteProduct.html?productId=${product.productId}"
						onclick="return checkDelete()"><img src="${deleteImg}" /></a> | <a
						href="changeProductSubVariantAction/${product.productId}" > Sub-Variant</a>
						</td>
				</tr>
						<tr>
					<td colspan="7" align="center">
						<div style="display: none;padding: 10px;" class="variant${product.productId}">
							<table border="1" cellpadding="2px" cellspacing="0">
								<tr>
									<td>Product Code</td>
									<td>Variant Type</td>
									<td>Variant Name</td>
									<td>Quantity</td>
								</tr>
								<c:forEach items="${product.variantBeans}" var="variant">
									<tr>
										<td><c:out value="${variant.productCode}" /></td>
										<td><c:out value="${variant.variantType}" /></td>
										<td><c:out value="${variant.variantName}" /></td>
										<td><c:out value="${variant.quantity}" /></td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>