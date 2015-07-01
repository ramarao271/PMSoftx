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
			document.getElementById("stageName").value="";
			document.getElementById("stageId").value=0;
		}
	}
</script>

</head>
<body onload="load()">
	<h4>
		Stage
	</h4>
	<form:form method="POST" action="/ERPSoftware/stage/saveStage.html"
		name="cat">
		<table>
			<tr>
				<td><form:label path="stageName">Stage</form:label></td>
				<td><form:input path="stageName"
						value="${stage.stageName}" /><form:errors path="stageName" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit" /></td>
				<td><form:input path="stageId" cssStyle="visibility:hidden"
						value="${stage.stageId}" /></td>
			</tr>
		</table>
		<form:hidden path="stageCode" value="${stage.stageCode}" />
	</form:form>
	<c:if test="${!empty categories}">
		<h4>Categories List</h4>
		<table align="left" border="1" cellpadding="0" cellspacing="0">
			<tr>
				<th>Sno</th>
				<th>Code</th>
				<th>Stage</th>
				<th>Actions</th>
			</tr>
			<c:forEach items="${categories}" var="stage" varStatus="id">
				<tr>
					<td><c:out value="${id.index+1}" /></td>
					<td><c:out value="${stage.stageCode}" /></td>
					<td><c:out value="${stage.stageName}" /></td>
					<td align="center"><a
						href="/ERPSoftware/stage/editStage.html?stageId=${stage.stageId}"><img src="${editImg}" /></a>
						| <a
						href="/ERPSoftware/stage/deleteStage.html?stageId=${stage.stageId}"
						onclick="return checkDelete()"><img src="${deleteImg}" /></a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>