<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ERP Software</title>
<c:url value="/resources/js/global.js" var="confirmjs" />
<script type="text/javascript" src="${confirmjs}"></script>
<c:url value="/resources/img/b_delete.png" var="deleteImg" />
<c:url value="/resources/img/b_edit.png" var="editImg" />
<c:url value="/resources/img/b_add.png" var="addImg" />
<c:url value="/resources/img/add-small.png" var="variantImg" />

<c:url value="/resources/css/global.css" var="stylecss" />
<link href="${stylecss}" rel="stylesheet" />
<title>All Products</title>
<script type="text/javascript">
	function load() {
		var msg = '<c:if test="${!empty message}"><c:out value="${message}" /></c:if>';
		if (msg != "") {
			alert(msg);
			document.getElementById("variantName").value="";
			document.getElementById("variantId").value=0;
		}
	}
</script>

</head>
<body onload="load()">
	<h4>
		Variant
	</h4>
	<form:form method="POST" action="/ERPSoftware/variant/saveVariant.html"
		name="cat">
		<table>
			<tr>
				<td><form:label path="variantType">Select Variant type</form:label></td>
				<td><form:select path="variantType"
						class="branch">
						<form:option value="Color" />
						<form:option value="Size" />
						<form:option value="Material" />
					</form:select></td>
			</tr>
			<tr>
				<td><form:label path="variantName">Variant</form:label></td>
				<td><form:input path="variantName"
						value="${variant.variantName}" /><form:errors path="variantName" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit" /></td>
				<td><form:input path="variantId" cssStyle="visibility:hidden"
						value="${variant.variantId}" /></td>
			</tr>
		</table>
	</form:form>
	<c:if test="${!empty variants}">
		<h4>Categories List</h4>
		<table align="left" border="1" cellpadding="0" cellspacing="0">
			<tr>
				<th>Sno</th>
				<th>Variant type</th>
				<th>Variant Name</th>
				<th>Actions</th>
			</tr>
			<c:forEach items="${variants}" var="variant" varStatus="id">
				<tr>
					<td><c:out value="${id.index+1}" /></td>
					<td><c:out value="${variant.variantType}" /></td>
					<td><c:out value="${variant.variantName}" /></td>
					<td align="center"><a
						href="/ERPSoftware/variant/editVariant.html?variantId=${variant.variantId}"><img src="${editImg}" /></a>
						| <a
						href="/ERPSoftware/variant/deleteVariant.html?variantId=${variant.variantId}"
						onclick="return checkDelete()"><img src="${deleteImg}" /></a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>