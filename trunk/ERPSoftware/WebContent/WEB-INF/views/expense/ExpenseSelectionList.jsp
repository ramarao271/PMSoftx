<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<c:url value="/resources/img/b_delete.png" var="deleteImg" />
<c:url value="/resources/img/b_edit.png" var="editImg" />
<c:url value="/resources/img/b_add.png" var="addImg" />
<c:url value="/resources/img/plus.png" var="variantImg" />
<c:url value="/resources/img/minus.png" var="variantmImg" />
<c:url value="/resources/overlay/jquery-1.11.2.min.js" var="jqueryjs" />
<script type="text/javascript" src="${jqueryjs}"></script>
<c:url value="/resources/js/variantShow.js" var="variantjs" />
<c:url value="/resources/js/global.js" var="confirmjs" />
<script type="text/javascript" src="${confirmjs}"></script>

<script type="text/javascript">
	var type = '<c:out value="${type}" />';
	$("a").click(function() {
		var selected = $(this).data('showpopup');
		$(".variant" + selected).toggle("slow");
	});
	function loadExpenses(val) {
		document.expenseSelection.action = '/ERPSoftware/expense/ExpenseSelectionListType/<c:out value="${type}" />/'
				+ val;
		document.expenseSelection.submit();
	}
	function sendSelected() {

		var expenseId = document.expenseSelection.expenseId.value;
		var expenseVariantId = expenseId.split("###")[1];
		var name = "";
		var code = "";
		var quantity = "";
		if (expenseVariantId == undefined) {
			name = "expenseName" + expenseId;
			code = "expenseCode" + expenseId;
			quantity = "quantity" + expenseId;
		} else {
			name = "expenseName" + expenseVariantId;
			code = "expenseCode" + expenseVariantId;
			quantity = "quantity" + expenseVariantId;
			expenseVariantId = "expenseVariantId" + expenseVariantId;
			expenseId = expenseId.split("###")[0];
		}
		var priceId = document.expenseSelection.priceId.value;
		var expenseName = document.getElementById(name).value;
		var expenseCode = document.getElementById(code).value;
		var quantityf = document.getElementById(quantity).value;
		var expenseVariantIdf = document.getElementById(expenseVariantId).value;
		parent.setExpense(type, expenseId, expenseName, expenseCode, quantityf,
				expenseVariantIdf, priceId);
	}
</script>
<style type="text/css">
body {
	color: #444444;
	font-family: Tahoma, Arial, Helvetica, sans-serif;
	font-size: 11px;
	margin: 0;
}
</style>

<c:url value="/resources/js/variantShow.js" var="variantjs" />
<script type="text/javascript" src="${variantjs}"></script>
</head>
<body>
	<form:form method="POST" action="saveExpense.html"
		name="expenseSelection" modelAttribute="expenseBean">
		<table align="left" cellpadding="0" cellspacing="0">
			<tr>

				<td><input type="button" value="Select" name="selected"
					id="selected" onclick="sendSelected()" /></td>
			</tr>
		</table>
		<br />
		<table align="left" border="1" cellpadding="2%" cellspacing="0"
			style="left: 0px; top: 23px; position: absolute;">
			<c:if test="${!empty expenses}">
				<tr>
					<th>Select</th>
					<th>Expense Name</th>
				</tr>

				<c:forEach items="${expenses}" var="expense" varStatus="itemsRow">
					<tr>
						<td><c:choose>
								<c:when test="${fn:length(expense.variantBeans) eq 0}">
									<input type="radio" name="expenseId" id="expenseId"
										value="${expense.expenseId}" />
								</c:when>
								<c:otherwise>
									<a href="#" data-showpopup="${expense.expenseId}"> <img
										class="arrowRotate" data-swap="${variantImg}"
										src="${variantImg}" data-src="${variantmImg}" height="25px"
										width="25px" />
									</a>
								</c:otherwise>
							</c:choose></td>
						<td><input type="hidden" id="expenseCode${expense.expenseId}"
							value="${expense.expenseCode}" /> <c:out
								value="${expense.expenseCode}" /></td>
						<td><input type="hidden" id="expenseName${expense.expenseId}"
							value="${expense.expenseName}" /> <c:out
								value="${expense.expenseName}" /></td>

					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form:form>
</body>
</html>