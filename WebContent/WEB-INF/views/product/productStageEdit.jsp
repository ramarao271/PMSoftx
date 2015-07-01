<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
function setId(id)
{
	document.productForm.action="/ERPSoftware/product/saveProductStages/"+id;
	document.productForm.submit();
}
</script>
</head>
<body>
	<form:form method="POST"
		action="/ERPSoftware/product/saveProductStages.html"
		modelAttribute="productBean" name="productForm" id="productForm"
		enctype="multipart/form-data">

		<form:input path="productId" cssStyle="visibility:hidden"
			value="${productBean.productId}" />
		<table border=1 cellpadding="0" cellspacing="2">
			<tr>
				<th>Stage Name</th>
				<th>Quantity</th>
				<th>Qty to be Updated</th>
				<th>Move to next stage</th>
			</tr>
			<c:forEach items="${productBean.stageBeans}" var="i"
				varStatus="itemsRow">
				<%-- <c:choose>
					<c:when test="${i.stageName eq 'Stock'}">
				 --%>		<tr>
							<form:hidden path="stageBeans[${itemsRow.index}].stageId"
								value="${i.stageId}" />
							<td><c:out value="${i.stageName}" /></td>
							<td><c:out value="${i.quantity}" /></td>
							<td><form:input
									path="stageBeans[${itemsRow.index}].quantity" /></td>
							<td><input type="button" value="Go!"
								onclick="setId(${i.stageId})" /></td>
						</tr>
				<%-- 	</c:when>
					<c:otherwise>
						<tr>
							<td><form:hidden
									path="stageBeans[${itemsRow.index}].stageId"
									value="${i.stageId}" /> <form:select
									path="stageBeans[${itemsRow.index}].stageName" class="branch">
									<c:forEach items="${stages}" var="sProperties">
										<c:choose>
											<c:when
												test="${i.stageName eq sProperties.stagePropertiesName}">
												<form:option selected="true"
													value="${sProperties.stagePropertiesName }" />
											</c:when>
											<c:otherwise>
												<form:option value="${sProperties.stagePropertiesName }" />
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select></td>
							<td><c:out value="${i.quantity}" /></td>
							<td><form:input
									path="stageBeans[${itemsRow.index}].quantity" /></td>
							<td><input type="button" value="Go!"
								onclick="setId(${i.stageId})" /></td>
						</tr>
					</c:otherwise>
				</c:choose> --%>
			</c:forEach>
		</table>
	</form:form>
</body>
</html>