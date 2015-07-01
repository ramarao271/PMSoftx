<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="jst" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ERP Software</title>
<c:url value="/resources/css/global.css" var="stylecss" />
<link href="${stylecss}" rel="stylesheet" />
<c:url value="/resources/img/b_delete.png" var="deleteImg" />
<c:url value="/resources/img/b_edit.png" var="editImg" />
<c:url value="/resources/img/b_add.png" var="addImg" />
<c:url value="/resources/img/plus.png" var="variantImg" />
<c:url value="/resources/img/minus.png" var="variantmImg" />
<c:url value="/resources/overlay/jquery-1.11.2.min.js" var="jqueryjs" />
<script type="text/javascript" src="${jqueryjs}"></script>
<c:url value="/resources/js/variantShow.js" var="variantjs" />
<script type="text/javascript" src="${variantjs}"></script>
<jst:url value="/resources/js/global.js" var="confirmjs" />
<script type="text/javascript" src="${confirmjs}"></script>
<script type="text/javascript">
	function load() {
		var x = '<jst:out value="${category}" />';
		updateLinks(x);
	}
	
</script>
</head>
<body>
	<c:if test="${!empty rawMaterials}">
		<h3>RawMaterials List</h3>
		<table align="left" border="1" cellpadding="2px" cellspacing="0">
			<tr>
				<th>Variant</th>
				<th>PCode</th>
				<th>RawMaterial Name</th>
				<th>Category</th>
				<th>Quantity</th>
				<th>Cost</th>
				<th>Price</th>
				<!-- <th>Price1</th>
				<th>Price2</th>
				<th>Price3</th>
				 --><th>Image</th>
				<th>Actions</th>
			</tr>
			<c:forEach items="${rawMaterials}" var="rawMaterial">
				<tr>
					<td><c:if test="${! empty rawMaterial.variantBeans }">
							<a href="#" data-showpopup="${rawMaterial.rawMaterialId}"><img class="arrowRotate" data-swap="${variantImg}"
								src="${variantImg}" data-src="${variantmImg}" height="25px" width="25px" /></a>
						</c:if></td>

					<td><c:out value="${rawMaterial.rawMaterialCode}" /></td>
					<td><c:out value="${rawMaterial.rawMaterialName}" /></td>
					<td><c:out value="${rawMaterial.categoryBean.categoryName}" /></td>
					<td><c:out value="${rawMaterial.quantity}" /> <c:out
							value="${rawMaterial.measurementBean.measurementName}" /></td>
					<td><c:out value="${rawMaterial.cost}" /></td>
					<td><c:out value="${rawMaterial.price}" /></td>
					<%-- <td><c:out value="${rawMaterial.price1}" /></td>
					<td><c:out value="${rawMaterial.price2}" /></td>
					<td><c:out value="${rawMaterial.price3}" /></td>
					 --%><td><img src="${rawMaterial.imagePath}" height="50px" width="50px" /></td>
					<td align="center"><a
						href="editRawMaterial.html?rawMaterialId=${rawMaterial.rawMaterialId}"><img
							src="${editImg}" /></a> | <a
						href="deleteRawMaterial.html?rawMaterialId=${rawMaterial.rawMaterialId}"
						onclick="return checkDelete()"><img src="${deleteImg}" /></a></td>
				</tr>
				<tr>
					<td colspan="12" align="center">
						<div style="display: none; padding: 10px;"
							class="variant${rawMaterial.rawMaterialId}">
							<table border="1" cellpadding="3px" cellspacing="0">
								<tr>
									<td>RawMaterial Code</td>
									<td>Variant Type</td>
									<td>Variant Name</td>
									<td>Quantity</td>
									<td>Required</td>
									<td>Ordered</td>
									<td>Allocated</td>
									<td>Sold</td>

								</tr>
								<c:forEach items="${rawMaterial.variantBeans}" var="variant">
									<tr>
										<td><c:out value="${variant.productCode}" /></td>
										<td><c:out value="${variant.variantType}" /></td>
										<td><c:out value="${variant.variantName}" /></td>
										<td><c:out value="${variant.quantity}" /></td>
										<td><c:out value="${variant.required}" /></td>
										<td><c:out value="${variant.ordered}" /></td>
										<td><c:out value="${variant.allocated}" /></td>
										<td><c:out value="${variant.sold}" /></td>
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