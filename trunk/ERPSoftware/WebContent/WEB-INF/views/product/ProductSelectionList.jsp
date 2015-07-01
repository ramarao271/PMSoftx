<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<c:url value="/resources/img/b_delete.png" var="deleteImg" />
<c:url value="/resources/img/b_edit.png" var="editImg" />
<c:url value="/resources/img/b_add.png" var="addImg" />
<c:url value="/resources/img/plus.png" var="variantImg" />
<c:url value="/resources/img/minus.png" var="variantmImg" />
<c:url value="/resources/overlay/jquery-1.11.2.min.js" var="jqueryjs" />
<script type="text/javascript" src="${jqueryjs}"></script>
<c:url value="/resources/js/variantShow.js" var="variantjs" />
<c:url value="/resources/js/global.js" var="confirmjs" />
<script type="text/javascript" src="${confirmjs}"></script>

<script type="text/javascript">
	var type = '<c:out value="${type}" />';
	$("a").click(function() {
		var selected = $(this).data('showpopup');
		$(".variant" + selected).toggle("slow");
	});
	function loadProducts(val) {
		document.productSelection.action = '/ERPSoftware/product/ProductSelectionListType/<c:out value="${type}" />/'
				+ val;
		document.productSelection.submit();
	}
	function sendSelected() {

		var productId = document.productSelection.productId.value;
		var productVariantId = productId.split("###")[1];
		var name = "";
		var code = "";
		var quantity = "";
		if (productVariantId == undefined) {
			name = "productName" + productId;
			code = "productCode" + productId;
			quantity = "quantity" + productId;
		} else {
			name = "productName" + productVariantId;
			code = "productCode" + productVariantId;
			quantity = "quantity" + productVariantId;
			productVariantId = "productVariantId" + productVariantId;
			productId = productId.split("###")[0];
		}
		var priceId = document.productSelection.priceId.value;
		var productName = document.getElementById(name).value;
		var productCode = document.getElementById(code).value;
		var quantityf = document.getElementById(quantity).value;
		var productVariantIdf = document.getElementById(productVariantId).value;
		parent.setProduct(type, productId, productName, productCode, quantityf,
				productVariantIdf, priceId);
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

<c:url value="/resources/js/variantShow.js" var="variantjs" />
<script type="text/javascript" src="${variantjs}"></script>
</head>
<body>
	<form:form method="POST" action="saveProduct.html"
		name="productSelection" modelAttribute="productBean">
		<table align="left" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="2"><a id="myButton"
					href="/ERPSoftware/product/addProduct.html">New Product</a></td>
			</tr>
			<tr>
				<td colspan="4"><form:select path="categoryBean.categoryId"
						onchange="loadProducts(this.value)">
						<option>Select Category</option>
						<c:forEach items="${categories}" var="category">
							<c:choose>
								<c:when test="${categoryId eq category.categoryId}">
									<form:option value="${category.categoryId}" selected="true">${category.categoryName}</form:option>
								</c:when>
								<c:otherwise>
									<form:option value="${category.categoryId}">${category.categoryName}</form:option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select></td>
				<td><input type="button" value="Select" name="selected"
					id="selected" onclick="sendSelected()" /></td>
			</tr>
		</table>
		<br />
		<table align="left" border="1" cellpadding="2%" cellspacing="0"
			style="left: 0px; top: 50px; position: absolute;">
			<c:if test="${!empty products}">
				<tr>
					<th>Variant</th>
					<th>Product Code</th>
					<th>Product Name</th>
					<th>Quantity</th>
					<th>Variants</th>
				</tr>

				<c:forEach items="${products}" var="product" varStatus="itemsRow">
					<tr>
						<td><c:choose>
								<c:when test="${fn:length(product.variantBeans) eq 0}">
									<input type="radio" name="productId" id="productId"
										value="${product.productId}" />
								</c:when>
								<c:otherwise>
									<a href="#" data-showpopup="${product.productId}"> <img
										class="arrowRotate" data-swap="${variantImg}"
										src="${variantImg}" data-src="${variantmImg}" height="25px"
										width="25px" />
									</a>
								</c:otherwise>
							</c:choose></td>
						<td><input type="hidden" id="productCode${product.productId}"
							value="${product.productCode}" /> <c:out
								value="${product.productCode}" /></td>
						<td><input type="hidden" id="productName${product.productId}"
							value="${product.productName}" /> <c:out
								value="${product.productName}" /></td>
						<td><input type="hidden" id="quantity${product.productId}"
							value="${product.measurementBean.measurementName}" /> <c:out
								value="${product.quantity}${product.measurementBean.measurementName}" /></td>
						<td><c:out value="${fn:length(product.variantBeans)}" /></td>
					</tr>
					<tr>
						<td colspan="6" align="center">
							<div style="display: none; padding: 10px;"
								class="variant${product.productId}">
								<table border="1" cellpadding="2%" cellspacing="0">
									<tr>
										<td>Select</td>
										<td>Product Code</td>
										<td>Variant Type</td>
										<td>Variant Name</td>
										<td>Quantity</td>

									</tr>
									<c:forEach items="${product.variantBeans}" var="variant"
										varStatus="itemsRow">
										<tr>
											<td><input type="radio" name="productId" id="productId"
												value="${product.productId}###${variant.productCode}" /> <input
												type="hidden" id="productCode${variant.productCode}"
												value="${variant.productCode}" /> <input type="hidden"
												id="productName${variant.productCode}"
												value="${product.productName}" /> <input type="hidden"
												id="quantity${variant.productCode}"
												value="${product.measurementBean.measurementName}" /> <input
												type="hidden" id="productVariantId${variant.productCode}"
												value="${variant.variantId}" /></td>
											<td><c:out value="${variant.productCode}" /></td>
											<td><c:out value="${variant.variantType}" /></td>
											<td><c:out value="${variant.variantName}" /></td>
											<td><c:out value="${variant.quantity}" /></td>
										</tr>
									</c:forEach>
								</table>
								<br />
								<table border="1" cellpadding="2%" cellspacing="0">
									<tr>
										<td>Cost</td>
										<td>Price</td>
										<td>Price1</td>
										<td>Price2</td>
										<td>Defective Price</td>
									</tr>
									<tr>
										<td><input type="radio" name="priceId" id="priceId"
											value="${product.cost}" /> <c:out value="${product.cost}" /></td>
										<td><input type="radio" name="priceId" id="priceId"
											value="${product.price}" /> <c:out value="${product.price}" /></td>
										<td><input type="radio" name="priceId" id="priceId"
											value="${product.price1}" /> <c:out
												value="${product.price1}" /></td>
										<td><input type="radio" name="priceId" id="priceId"
											value="${product.price2}" /> <c:out
												value="${product.price2}" /></td>
										<td><input type="radio" name="priceId" id="priceId"
											value="${product.price3}" /> <c:out
												value="${product.price3}" /></td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form:form>
</body>
</html>