<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ERP Software</title>
<c:url value="/resources/js/global.js" var="globaljs" />
<script type="text/javascript" src="${globaljs}"></script>
<c:url value="/resources/js/Items.js" var="itemsjs" />
<script type="text/javascript" src="${itemsjs}"></script>
<c:url value="/resources/css/global.css" var="stylecss" />
<link href="${stylecss}" rel="stylesheet" />
<c:url value="/resources/js/datepickr.js" var="datejs" />
<script type="text/javascript" src="${datejs}"></script>
<c:url value="/resources/css/datepickr.css" var="datecss" />
<link href="${datecss}" rel="stylesheet" />
<c:url value="/resources/overlay/jquery-1.11.2.min.js" var="jqueryjs" />
<script type="text/javascript" src="${jqueryjs}"></script>
<c:url value="/resources/overlay/custom.js" var="customjs" />
<script type="text/javascript" src="${customjs}"></script>
<c:url value="/resources/overlay/overlaypopup.css" var="popcss" />
<link type="text/css" rel="stylesheet" href="${popcss}" />
<c:url value="/resources/autocomplete/jquery.ui.all.css" var="jquerycss" />
<link rel="stylesheet" href="${jquerycss}">
<c:url value="/resources/autocomplete/jquery.ui.core.js"
	var="jquerycorejs" />
<script src="${jquerycorejs}"></script>
<c:url value="/resources/autocomplete/jquery.ui.widget.js"
	var="jquerywidgetjs" />
<script src="${jquerywidgetjs}"></script>
<c:url value="/resources/autocomplete/jquery.ui.position.js"
	var="positionjs" />
<script src="${positionjs}"></script>
<c:url value="/resources/autocomplete/jquery.ui.menu.js" var="menujs" />
<script src="${menujs}"></script>
<c:url value="/resources/autocomplete/jquery.ui.autocomplete.js"
	var="autojs" />
<script src="${autojs}"></script>
<c:url value="/resources/autocomplete/demos.css" var="democss" />
<link rel="stylesheet" href="${democss}">
<c:url value="/resources/autocomplete/images/ui-anim_basic_16x16.gif"
	var="imag" />
<style>
.ui-autocomplete-loading {
	background: white url('${imag}') right center no-repeat;
}
</style>
<script>
	var serial = 0;
	function setSerialNo(val) {
		serial = val;
	}
	function listInvoices(val) {
		document.paymentForm.action = "/ERPSoftware/otherPayment/listproductionInvoices/" + val;
		document.paymentForm.submit();

	}
	function setCustomerId(value) {
		var workerId = "workerId" + value;
		document.getElementById("workerBean.workerId").value = document
				.getElementById(workerId).value;
		//listInvoices(workerId,"other");
	}

	$(function() {
		function log(message) {
			$("<div>").text(message).prependTo("#log");
			$("#log").scrollTop(0);
		}

		$("#cityx").autocomplete({
			source : function(request, response) {
				$.ajax({
					url : "/ERPSoftware/productioninvoice/listSOWorkers.jsp",
					dataType : "json",
					data : {
						style : "full",
						maxRows : 12,
						name_startsWith : request.term
					},
					success : function(data) {
						response($.map(data, function(item) {
							return {
								value : item.workerName,
								id : item.workerId,
							};
						}));
					}
				});
			},
			minLength : 1,
			select : function(event, ui) {
				$(".workerId").val(ui.item.id);
				listInvoices(ui.item.id)
			},
			open : function() {
				$(this).removeClass("ui-corner-all").addClass("ui-corner-top");
			},
			close : function() {
				$(this).removeClass("ui-corner-top").addClass("ui-corner-all");
			}
		});
	});
	function calculateCost() {
		var total = 0.0;
		for (var i = 0;; i++) {
			var totalCost = "otherPaymentItemBeans" + i + ".paid";
			if (document.getElementById(totalCost) == null
					|| document.getElementById(totalCost) == undefined) {
				break;
			}
			totalCost = (parseFloat(document.getElementById(totalCost).value) || 0);
			total += parseFloat(totalCost);
		}
		var advance = document.getElementById("advance").value;
		document.getElementById("totalCost").value = ((parseFloat(total) || 0) + parseFloat(advance || 0))
				.toFixed(2);
	}
</script>
</head>
<body onload="loadBranches()">
	<h3>
		<b><c:if test="${!empty operation}">
				<c:out value="${operation}" />
			</c:if> Other Payment</b>
	</h3>
	<br />
	<form:form name="paymentForm" method="POST"
		action="/ERPSoftware/otherPayment/saveOtherPayment.html"
		modelAttribute="otherPaymentBean">
		<form:hidden path="workerBean.workerId" class="workerId" />
		<table cellpadding="0" cellspacing="5">
			<tr>
				<td>Pay to</td>
				<td><form:select path="payTo">
						<form:option value="Worker"></form:option>
						<form:option value="Shipper"></form:option>
					</form:select></td>
			</tr>
			<tr>
				<td>Worker</td>
				<td colspan="3"><form:input path="workerBean.workerName"
						id="cityx" /></td>
			</tr>
			<tr>
				<td><form:label path="otherPaymentDate">Other Payment Date:</form:label></td>
				<td><fmt:formatDate var="formattedDate" pattern="MM-dd-yyyy"
						value="${otherPaymentBean.otherPaymentDate}" /> <form:input
						path="otherPaymentDate" value="${formattedDate}" /> <script
						type="text/javascript">
							new datepickr('otherPaymentDate', {
								'dateFormat' : 'm-d-y'
							});
						</script> <form:errors path="otherPaymentDate" cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td>Payment Mode</td>
				<td><form:select path="paymentMode">
						<c:forEach items="${pTypes}" var="vTypes">
							<c:choose>
								<c:when test="${otherPaymentBean.paymentMode eq vTypes}">
									<form:option selected="true" value="${vTypes}" />
								</c:when>
								<c:otherwise>
									<form:option value="${vTypes}" />
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select></td>
			</tr>
			<tr>
				<td>Advance</td>
				<td><form:input path="advance" value="${advance}"
						onkeyup="calculateCost()" onchange="calculateCost()" /></td>
			</tr>
			<tr>
				<td colspan="2">
					<table border=1 cellpadding=0 cellspacing=2>
						<tr>
							<td>Invoice No</td>
							<td>Date</td>
							<td>Bill Amount</td>
							<td>Adjusted Amount</td>
							<td>Paid Amount</td>
							<td>Amount</td>
							<td>Processed</td>
						</tr>
						<c:forEach items="${productionInvoiceBeans}" var="productionInvoiceBean"
							varStatus="itemsRow">
							<tr>
								<td><form:hidden
										path="otherPaymentItemBeans[${itemsRow.index}].productionInvoiceId"
										value="${productionInvoiceBean.productionInvoiceId}" /> <form:hidden
										path="otherPaymentItemBeans[${itemsRow.index}].productionInvoiceFinYear"
										value="${productionInvoiceBean.finYear}" /> <c:out
										value="${productionInvoiceBean.productionInvoiceId }" /></td>
								<td><c:out value="${productionInvoiceBean.productionInvoiceDate }" /></td>
								<td><c:out value="${productionInvoiceBean.totalCost }" /></td>
								<td><c:out value="${productionInvoiceBean.returnAmount }" /></td>
								<td><c:out value="${productionInvoiceBean.paidAmount }" /></td>
								<td><form:input
										path="otherPaymentItemBeans[${itemsRow.index}].paid"
										value="${i.paid}" class="product_quantity" type="number"
										step="0.01" onkeyup="calculateCost()"
										onchange="calculateCost()" /> <form:errors
										path="otherPaymentItemBeans[${itemsRow.index}].paid"
										cssStyle="color: #ff0000;" /></td>
								<td><form:checkbox
										path="otherPaymentItemBeans[${itemsRow.index}].processed"></form:checkbox></td>
								<form:hidden
									path="otherPaymentItemBeans[${itemsRow.index}].srNo"
									value="${itemsRow.index}" class="product_Srno" />
								<form:hidden
									path="otherPaymentItemBeans[${itemsRow.index}].otherPaymentId"
									value="${i.otherPaymentId}" class="product_Srno" />
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
			<tr>
				<td><form:label path="totalCost">Total Cost:</form:label></td>
				<td align="right"><form:input path="totalCost"
						value="${otherPaymentBean.totalCost}" class="totalCost"
						type="number" readonly="true" /></td>
			</tr>
		</table>
		<form:hidden path="otherPaymentId"
			value="${otherPaymentBean.otherPaymentId}" />
		<input type="submit" value="Submit" />&nbsp;<input type="button"
			name="cancel" value="Cancel" onclick="loadIndex('otherPayment')" />
	</form:form>

	<div class="overlay-bg"></div>
	<div class="overlay-content popup1">
		<iframe
			src="/ERPSoftware/product/ProductSelectionList/otherPaymentItemBeans"
			frameborder="0" scrolling="auto" width="95%" height="350px"></iframe>
	</div>
</body>
</html>