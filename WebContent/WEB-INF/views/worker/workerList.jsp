<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:url value="/resources/css/global.css" var="stylecss" />
<link href="${stylecss}" rel="stylesheet" />
<c:url value="/resources/js/global.js" var="confirmjs" />
<script type="text/javascript" src="${confirmjs}"></script>
<title>All Products</title>
<script type="text/javascript">
	function load() {
		var msg = '<c:if test="${!empty message}"><c:out value="${message}" /></c:if>';
		if (msg != "")
			alert(msg);
	}
</script>
<c:url value="/resources/img/b_delete.png" var="deleteImg" />
<c:url value="/resources/img/b_edit.png" var="editImg" />
<c:url value="/resources/img/b_add.png" var="addImg" />
<c:url value="/resources/img/add-small.png" var="variantImg" />

</head>
<body onload="load()">
	<h3>Workers List</h3>
	<c:if test="${!empty workers}">
		<table align="left" border="1" cellpadding="0" cellspacing="0">
			<tr>
				<th>Worker Name</th>
				<th>Mobile</th>
				<th>Address</th>
				<th>Balance</th>
				<th>Actions</th>
			</tr>
			<c:forEach items="${workers}" var="worker">
				<tr>
					<td><c:out value="${worker.workerName}" /></td>
					<td><c:out value="${worker.mobile}" /></td>
					<td><c:out value="${worker.address}" /></td>
					<td><c:out value="${worker.openingBalance}" /></td>
					<td align="center"><a
						href="editWorker.html?workerId=${worker.workerId}"><img src="${editImg}" /></a>
						| <a href="deleteWorker.html?workerId=${worker.workerId}"
						onclick="return checkDelete()"><img src="${deleteImg}" /></a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

</body>
</html>