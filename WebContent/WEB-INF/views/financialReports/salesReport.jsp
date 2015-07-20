<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<c:url value="/resources/js/global.js" var="globaljs" />
<script type="text/javascript" src="${globaljs}"></script>
<c:url value="/resources/css/global.css" var="stylecss" />
<link href="${stylecss}" rel="stylesheet" />
<c:url value="/resources/js/datepickr.js" var="datejs" />
<script type="text/javascript" src="${datejs}"></script>
<c:url value="/resources/js/Items.js" var="itemsjs" />
<script type="text/javascript" src="${itemsjs}"></script>
<c:url value="/resources/css/datepickr.css" var="datecss" />
<c:url value="/resources/reports/table.css" var="tablecss" />
<link href="${tablecss}" rel="stylesheet" />
<link href="${datecss}" rel="stylesheet" />
</head>
<body>
	<form:form action="salesReportAction" method="post"
		modelAttribute="reportForm">
		<table align="center">
			<tr>
				<td><form:select path="durationType">
						<form:option value="Daily"></form:option>
						<form:option value="Monthly"></form:option>
						<form:option value="Yearly"></form:option>
					</form:select></td>
				<td>From: <form:input path="fromDate" value="${formattedDate}" />
					<script type="text/javascript">
						new datepickr('fromDate', {
							'dateFormat' : 'y-m-d'
						});
					</script></td>
				<td>To: <form:input path="toDate" value="${formattedDate}" />
					<script type="text/javascript">
						new datepickr('toDate', {
							'dateFormat' : 'y-m-d'
						});
					</script></td>
				<td><input type="submit" value="Go" /></td>
			</tr>
		</table>
		<h3>
			Sales Report for
			<c:out value="${mode}" />
		</h3>
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
									<th><div label="Amount"></div></th>
									<!-- <th><div label="Date"></div></th> -->
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${products}" var="product">
									<tr>
										<c:choose>
											<c:when test="${product.quantity>0}">
												<td><c:out value="${product.productName}" /></td>
												<td><c:out value="${product.categoryName}" /></td>
												<td align="right"><c:if test="${product.quantity>0}">
														<c:out value="${product.quantity}" />
													</c:if></td>
												<td align="right"><c:if test="${product.rate>0 }">
														<c:out value="${product.rate}" />
													</c:if></td>
												<%-- <td><c:out value="${product.date}" /></td> --%>

											</c:when>
											<c:otherwise>
												<td colspan="5" align="center"><b><c:out
															value="${product.productName}" /></b></td>
											</c:otherwise>
										</c:choose>

									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</c:if>
	</form:form>
</body>
</html>