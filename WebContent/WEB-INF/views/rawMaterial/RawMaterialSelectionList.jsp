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
	function loadRawMaterials(val) {
		document.rawMaterialSelection.action = '/ERPSoftware/rawMaterial/RawMaterialSelectionListType/<c:out value="${type}" />/'
				+ val;
		document.rawMaterialSelection.submit();
	}
	function sendSelected() {

		var rawMaterialId = document.rawMaterialSelection.rawMaterialId.value;
		var rawMaterialVariantId = rawMaterialId.split("###")[1];
		var name = "";
		var code = "";
		var quantity = "";
		if (rawMaterialVariantId == undefined) {
			name = "rawMaterialName" + rawMaterialId;
			code = "rawMaterialCode" + rawMaterialId;
			quantity = "quantity" + rawMaterialId;
		} else {
			name = "rawMaterialName" + rawMaterialVariantId;
			code = "rawMaterialCode" + rawMaterialVariantId;
			quantity = "quantity" + rawMaterialVariantId;
			rawMaterialVariantId = "rawMaterialVariantId" + rawMaterialVariantId;
			rawMaterialId = rawMaterialId.split("###")[0];
		}
		var priceId = document.rawMaterialSelection.priceId.value;
		var rawMaterialName = document.getElementById(name).value;
		var rawMaterialCode = document.getElementById(code).value;
		var quantityf = document.getElementById(quantity).value;
		var rawMaterialVariantIdf = document.getElementById(rawMaterialVariantId).value;
		parent.setRawMaterial(type, rawMaterialId, rawMaterialName, rawMaterialCode, quantityf,
				rawMaterialVariantIdf, priceId);
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
	<form:form method="POST" action="saveRawMaterial.html"
		name="rawMaterialSelection" modelAttribute="rawMaterialBean">
		<table align="left" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="4"><form:select path="categoryBean.categoryId"
						onchange="loadRawMaterials(this.value)">
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
			style="left: 0px; top: 23px; position: absolute;">
			<c:if test="${!empty rawMaterials}">
				<tr>
					<th>Variant</th>
					<th>RawMaterial Code</th>
					<th>RawMaterial Name</th>
					<th>Quantity</th>
					<th>Variants</th>
				</tr>

				<c:forEach items="${rawMaterials}" var="rawMaterial" varStatus="itemsRow">
					<tr>
						<td><c:choose>
								<c:when test="${fn:length(rawMaterial.variantBeans) eq 0}">
									<input type="radio" name="rawMaterialId" id="rawMaterialId"
										value="${rawMaterial.rawMaterialId}" />
								</c:when>
								<c:otherwise>
									<a href="#" data-showpopup="${rawMaterial.rawMaterialId}">
										<img class="arrowRotate" data-swap="${variantImg}" src="${variantImg}" data-src="${variantmImg}" height="25px" width="25px" />
									</a>
								</c:otherwise>
							</c:choose></td>
						<td><input type="hidden" id="rawMaterialCode${rawMaterial.rawMaterialId}"
							value="${rawMaterial.rawMaterialCode}" /> <c:out
								value="${rawMaterial.rawMaterialCode}" /></td>
						<td><input type="hidden" id="rawMaterialName${rawMaterial.rawMaterialId}"
							value="${rawMaterial.rawMaterialName}" /> <c:out
								value="${rawMaterial.rawMaterialName}" /></td>
						<td><input type="hidden" id="quantity${rawMaterial.rawMaterialId}"
							value="${rawMaterial.measurementBean.measurementName}" /> <c:out
								value="${rawMaterial.quantity}${rawMaterial.measurementBean.measurementName}" /></td>
								<td><c:out value="${fn:length(rawMaterial.variantBeans)}" /></td>
					</tr>
					<tr>
						<td colspan="6" align="center">
							<div style="display: none; padding: 10px;"
								class="variant${rawMaterial.rawMaterialId}">
								<table border="1" cellpadding="3%" cellspacing="0">
									<tr>
										<td>Select</td>
										<td>RawMaterial Code</td>
										<td>Variant Type</td>
										<td>Variant Name</td>
										<td>Quantity</td>

									</tr>
									<c:forEach items="${rawMaterial.variantBeans}" var="variant"
										varStatus="itemsRow">
										<tr>
											<td><input type="radio" name="rawMaterialId" id="rawMaterialId"
												value="${rawMaterial.rawMaterialId}###${variant.productCode}" /> <input
												type="hidden" id="rawMaterialCode${variant.productCode}"
												value="${variant.productCode}" /> <input type="hidden"
												id="rawMaterialName${variant.productCode}"
												value="${rawMaterial.rawMaterialName}" /> <input type="hidden"
												id="quantity${variant.productCode}"
												value="${rawMaterial.measurementBean.measurementName}" /> <input
												type="hidden" id="rawMaterialVariantId${variant.productCode}"
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
									</tr>
									<tr>
										<td><input type="radio" name="priceId" id="priceId"
											value="${rawMaterial.cost}" />
										<c:out value="${rawMaterial.cost}" /></td>
										<td><input type="radio" name="priceId" id="priceId"
											value="${rawMaterial.price}" />
										<c:out value="${rawMaterial.price}" /></td>
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