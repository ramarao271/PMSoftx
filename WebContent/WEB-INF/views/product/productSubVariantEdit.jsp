<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ERP Software</title>
<c:url value="/resources/img/b_delete.png" var="deleteImg" />
<c:url value="/resources/img/b_edit.png" var="editImg" />
<c:url value="/resources/img/b_add.png" var="addImg" />
<c:url value="/resources/css/global.css" var="stylecss" />
<link href="${stylecss}" rel="stylesheet" />
<c:url value="/resources/js/global.js" var="globaljs" />
<c:url value="/resources/autocomplete/demos.css" var="democss" />
<link rel="stylesheet" href="${democss}">
<script type="text/javascript" src="${globaljs}"></script>
<script type="text/javascript">
	function setId(id) {
		document.productForm.action = "/ERPSoftware/product/saveProductStages/"
				+ id;
		document.productForm.submit();
	}
</script>
</head>
<body>
	<form:form method="POST"
		action="/ERPSoftware/product/saveProductSubVariant.html"
		modelAttribute="subVariantBean" name="productForm" id="productForm"
		enctype="multipart/form-data">
		<form:input path="productBean.productId" cssStyle="visibility:hidden"
			value="${productBean.productId}" />
		<table border=0 cellpadding="0" cellspacing="2">
			<tr>
				<th>Variant Name</th>
				<td><form:select path="variantBean.variantId" class="branch">
						<c:forEach items="${subVariantBean.productBean.variantBeans}"
							var="vProperties">
							<c:if test="${vProperties.variantId ne 0 }">
								<form:option value="${vProperties.variantId }">${vProperties.variantName }</form:option>
							</c:if>
						</c:forEach>
					</form:select></td>
			</tr>
			<tr>
				<th>Sub-Variant Category</th>
				<td><form:select
						path="subVariantPropertiesBean.subVariantPropertiesId"
						class="branch">
						<c:forEach items="${vProperties}" var="vProperties">
							<form:option value="${vProperties.subVariantPropertiesId}">${vProperties.subVariantPropertiesName }</form:option>
						</c:forEach>
					</form:select></td>
			</tr>
			<tr>
				<th>Sub-Variant Name</th>
				<td><form:input path="newVariantName" /></td>
			</tr>
			<tr>
				<th>Quantity</th>
				<td><form:input path="quantity" /></td>
			</tr>
			<tr>
				<th>Cost</th>
				<td><form:input path="cost" /></td>
			</tr>
			<tr>
				<th>Worker</th>
				<td><form:select path="workerBean.workerId" class="branch">
						<c:forEach items="${workers}" var="worker">
							<form:option value="${worker.workerId }">${worker.workerName}</form:option>
						</c:forEach>
					</form:select></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="create" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>