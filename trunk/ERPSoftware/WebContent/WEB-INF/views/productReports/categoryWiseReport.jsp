<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>All Products</title>
<c:url value="/resources/reports/jquery-1.11.3.js" var="jqueryjs" />
<script type="text/javascript" src="${jqueryjs}"></script>
<c:url value="/resources/reports/reports.js" var="variantjs" />
<script type="text/javascript" src="${variantjs}"></script>
<c:url value="/resources/js/global.js" var="confirmjs" />
<c:url value="/resources/img/b_delete.png" var="deleteImg" />
<c:url value="/resources/img/b_edit.png" var="editImg" />
<c:url value="/resources/img/b_add.png" var="addImg" />
<c:url value="/resources/img/plus.png" var="variantImg" />
<c:url value="/resources/img/minus.png" var="variantmImg" />
<script type="text/javascript" src="${confirmjs}"></script>
<c:url value="/resources/reports/table.css" var="tablecss" />
<link href="${tablecss}" rel="stylesheet" />
<c:url value="/resources/css/global.css" var="stylecss" />
<link href="${stylecss}" rel="stylesheet" />
<script type="text/javascript">
	function load() {
		var msg = '<c:if test="${!empty message}"><c:out value="${message}" /></c:if>';
		if (msg != "")
			alert(msg);
	}
</script>
</head>
<body onload="load()">
	<h3>
		Products Sales by Category
	</h3>
	<c:if test="${!empty cats}">
		<div class="scrollingtable">
			<div>
				<div>

					<table>
						<thead>
							<tr>
								<th><div label="Products"></div></th>
								<th><div label="Category"></div></th>
								<th><div label="Category Code"></div></th>
								<th><div label="Quantity"></div></th>
								<th><div label="Amount"></div></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${cats}" var="cat">
								<tr>
									<td><a href="#" data-showpopup="${cat.categoryId}"
										data-url="/ERPSoftware/product/productWiseSales"><img
											class="arrowRotate" data-swap="${variantImg}"
											src="${variantImg}" data-src="${variantmImg}" /></a></td>
									<td><c:out value="${cat.category}" /></td>
									<td><c:out value="${cat.categoryCode}" /></td>
									<td><c:out value="${cat.quantity}" /></td>
									<td><c:out value="${cat.amount}" /></td>
								</tr>
								<tr>
									<td colspan="5" style="display: none; padding: 10px;"
										class="drill${cat.categoryId}"></td>
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