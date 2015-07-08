<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:url value="/resources/reports/table.css" var="tablecss" />
<link href="${tablecss}" rel="stylesheet" />
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

</head>
<body onload="load()">
	<h3>Frequently Purchased Product</h3>
	<c:if test="${!empty products}">
		<div class="scrollingtable">
			<div>
				<div>

					<table>
						<thead>
							<tr>
								<th><div label="Product"></div></th>
								<th><div label="Category"></div></th>
								<th><div label="Quantity"></div></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${products}" var="product">
								<tr>
									<td><c:out value="${product.productName}" /></td>
									<td><c:out value="${product.categoryBean.categoryName}" /></td>
									<td><c:out value="${product.soldVariants}" /></td>
									<td><c:out value="${sold}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</c:if>
</body>
</html>
