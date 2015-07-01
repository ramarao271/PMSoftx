<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<table>
		<tr>
			<td>ID</td>
			<td><c:out value="${balanceSheetBean.balanceSheetId }" /></td>
		</tr>
		<tr>
			<td>Date</td>
			<td><c:out value="${balanceSheetBean.balanceSheetDate }" /></td>
		</tr>
		<tr>
			<td colspan="2">
				<table border=1 cellpadding="2%" cellspacing="0">
					<tr>
						<td>Expense</td>
						<td>Description</td>
						<td>Cost</td>
					</tr>
					<c:forEach items="${balanceSheetBean.balanceSheetItemBeans}"
						var="i" varStatus="itemsRow">
						<tr>
							<td><c:out value="${i.expenseBeanId.expenseName}" /></td>
							<td><c:out value="${i.description}" /></td>
							<td align="right"><c:out value="${i.rate}" /></td>
						</tr>
					</c:forEach>
					<tr>
					<td></td><td>Total</td><td><c:out value="${balanceSheetBean.totalCost}"></c:out></td></tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>