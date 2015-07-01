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
<c:url value="/resources/img/b_delete.png" var="deleteImg" />
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
	function setWorkerId(value) {
		var workerId = "workerId" + value;
		document.getElementById("workerBean.workerId").value = document
				.getElementById(workerId).value;
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
		var count = parseInt(document.personForm.productioninvoice.value);
		var total = 0.0;
		for (var i = 0; i < count; i++) {
			var qty = "productionInvoiceItemBeans" + i + ".quantity";
			var rate = "productionInvoiceItemBeans" + i + ".rate";
			var totalCost = "productionInvoiceItemBeans" + i + ".totalCost";
			var qtyValue = parseInt(document.getElementById(qty).value);
			var rateValue = parseFloat(document.getElementById(rate).value);
			var totalRate = (qtyValue || 0) * (rateValue || 0);
			document.getElementById(totalCost).value = totalRate.toFixed(2);
			total = parseFloat(total) + parseFloat(totalRate);
		}
		document.getElementById("totalCost").value = parseFloat(total).toFixed(
				2);
	}
</script>
</head>
<body onload="calculateCost()">
	<h3>
		<b><c:if test="${!empty operation}">
				<c:out value="${operation}" />
			</c:if>Production Invoice</b>
	</h3>
	<br />
	<form:form name="personForm" method="POST"
		action="/ERPSoftware/productioninvoice/saveProductionInvoice.html"
		modelAttribute="productionInvoiceBean">
		<form:hidden path="workerBean.workerId" class="workerId" />
		<table cellpadding="0" cellspacing="5">
			<tr>
				<td>Production Order No</td>
				<td><form:input path="productionOrderBean.productionOrderId"
						readonly="true" href="#" class="show-popup" data-showpopup="2"
						value="${productionInvoiceBean.productionOrderBean.productionOrderId}" /></td>
			</tr>

			<tr>
				<td>Worker</td>
				<td colspan="3"><form:input path="workerBean.workerName"
						id="cityx" /></td>
			</tr>
			<tr>
				<td><form:label path="productionInvoiceDate">Date</form:label></td>
				<td><fmt:formatDate var="formattedDate" pattern="MM-dd-yyyy"
						value="${productionInvoiceBean.productionInvoiceDate}" /> <form:input
						path="productionInvoiceDate" value="${formattedDate}" /> <script
						type="text/javascript">
							new datepickr('productionInvoiceDate', {
								'dateFormat' : 'm-d-y'
							});
						</script> <form:errors path="productionInvoiceDate"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td>Select No. of Items</td>
				<td><form:select path="" name="productioninvoice"
						onchange="setItemsFields(this.value,this.name)">
						<c:forEach var="i" begin="1" end="25">
							<c:choose>
								<c:when
									test="${fn:length(productionInvoiceBean.productionInvoiceItemBeans) eq i}">
									<form:option selected="true" value="${i}"></form:option>
								</c:when>
								<c:otherwise>
									<form:option value="${i}"></form:option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select></td>
			</tr>
			<tr>
				<td colspan="2">
					<table border=1 cellpadding=0 cellspacing=2>
						<tr>
							<c:if test="${!(productionInvoiceBean.productionInvoiceId eq 0)}">
								<td>Del</td>
							</c:if>
							<td><form:label
									path="productionInvoiceItemBeans[0].productId.productName">Raw Material</form:label></td>
							<td><form:label
									path="productionInvoiceItemBeans[0].productId.productName">Code</form:label></td>
							<td><form:label
									path="productionInvoiceItemBeans[0].description">Description</form:label></td>
							<td><form:label
									path="productionInvoiceItemBeans[0].quantity">Quantity</form:label></td>
							<td><form:label
									path="productionInvoiceItemBeans[0].quantityType">Qty Type</form:label></td>
							<td><form:label path="productionInvoiceItemBeans[0].rate">Unit Rate</form:label></td>
							<td><form:label
									path="productionInvoiceItemBeans[0].totalCost">Total Cost</form:label></td>
						</tr>

						<c:forEach
							items="${productionInvoiceBean.productionOrderBean.productionOrderItemBeans}"
							var="i" varStatus="itemsRow">
							<tr>
								<td><c:out value="${i.rawMaterialBeanId.rawMaterialName}" /></td>
								<td><c:out value="${i.variantCode}" /></td>
								<td><c:out value="${i.description}" /></td>
								<td><c:out value="${i.quantity}" /></td>
								<td><c:out value="${i.quantityType}" /></td>
								<td><c:out value="${i.rate}" /></td>
								<td><c:out value="${i.totalCost}" /></td>
							</tr>
						</c:forEach>

					</table>
				</td>
			</tr>
			
		</table>

		<table border=1 cellpadding=0 cellspacing=2>
			<tr>
				<c:if test="${!(productionInvoiceBean.productionInvoiceId eq 0)}">
					<td>Del</td>
				</c:if>
				<td><form:label
						path="productionInvoiceItemBeans[0].productId.productName">Product</form:label></td>
				<td><form:label
						path="productionInvoiceItemBeans[0].productId.productName">Code</form:label></td>
				<td><form:label
						path="productionInvoiceItemBeans[0].description">Description</form:label></td>
				<td><form:label path="productionInvoiceItemBeans[0].quantity">Quantity</form:label></td>
				<td><form:label
						path="productionInvoiceItemBeans[0].quantityType">Qty Type</form:label></td>
				<td><form:label path="productionInvoiceItemBeans[0].rate">Unit Rate</form:label></td>
				<td><form:label path="productionInvoiceItemBeans[0].totalCost">Total Cost</form:label></td>
			</tr>
			<c:forEach
				items="${productionInvoiceBean.productionInvoiceItemBeans}" var="i"
				varStatus="itemsRow">
				<tr>
					<c:if test="${!(i.productionInvoiceId eq 0)}">
						<td><a href="#"
							onClick="deleteItemsFields(${i.srNo}, 'productioninvoice')">
								<img alt="" src="${deleteImg}">
						</a></td>
					</c:if>
					<td><form:hidden
							path="productionInvoiceItemBeans[${itemsRow.index}].productId.productId"
							value="${i.productId.productId }" /> <form:hidden
							path="productionInvoiceItemBeans[${itemsRow.index}].variantId"
							value="${i.variantId}" /> <form:input
							path="productionInvoiceItemBeans[${itemsRow.index}].productId.productName"
							value="${i.productId.productName}" class="show-popup" href="#"
							data-showpopup="1" onclick="setSerialNo(${itemsRow.index})"
							readonly="true" /> <form:errors
							path="productionInvoiceItemBeans[${itemsRow.index}].productId.productName"
							cssStyle="color: #ff0000;" /></td>
					<td><form:input
							path="productionInvoiceItemBeans[${itemsRow.index}].variantCode"
							value="${i.variantCode}" class="product_Code" readonly="true" />
						<form:errors
							path="productionInvoiceItemBeans[${itemsRow.index}].variantCode"
							cssStyle="color: #ff0000;" /></td>
					<td><form:input
							path="productionInvoiceItemBeans[${itemsRow.index}].description"
							value="${i.description}" class="product_Description" /> <form:errors
							path="productionInvoiceItemBeans[${itemsRow.index}].description"
							cssStyle="color: #ff0000;" /></td>
					<td><form:input
							path="productionInvoiceItemBeans[${itemsRow.index}].quantity"
							value="${i.quantity}" class="product_quantity" type="number"
							step="0.01" onkeyup="calculateCost()" onchange="calculateCost()" />
						<form:errors
							path="productionInvoiceItemBeans[${itemsRow.index}].quantity"
							cssStyle="color: #ff0000;" /></td>
					<td><form:input
							path="productionInvoiceItemBeans[${itemsRow.index}].quantityType"
							value="${i.quantityType}" class="product_quantityType" /> <form:errors
							path="productionInvoiceItemBeans[${itemsRow.index}].quantityType"
							cssStyle="color: #ff0000;" />
					<td><form:input
							path="productionInvoiceItemBeans[${itemsRow.index}].rate"
							value="${i.rate}" class="product_Unit_Rate" type="number"
							step="0.01" onkeyup="calculateCost()" onchange="calculateCost()" />
						<form:errors
							path="productionInvoiceItemBeans[${itemsRow.index}].rate"
							cssStyle="color: #ff0000;" />
					<td><form:input
							path="productionInvoiceItemBeans[${itemsRow.index}].totalCost"
							value="${i.totalCost}" class="product_Total_Rate" type="number"
							step="0.01" readonly="true" /> <form:errors
							path="productionInvoiceItemBeans[${itemsRow.index}].totalCost"
							cssStyle="color: #ff0000;" /></td>
					<form:hidden
						path="productionInvoiceItemBeans[${itemsRow.index}].srNo"
						value="${i.srNo}" class="product_Srno" />
					<form:hidden
						path="productionInvoiceItemBeans[${itemsRow.index}].productionInvoiceId"
						value="${i.productionInvoiceId}" class="product_Srno" />
				</tr>
			</c:forEach>
			<tr>
				<td><form:label path="totalCost">Total Cost:</form:label></td>
				<td align="right" colspan="7"><form:input
							path="totalCost"
							value="${productionInvoiceBean.totalCost}" class="product_Total_Rate" type="number"
							step="0.01" readonly="true" /> <form:errors
							path="totalCost"
							cssStyle="color: #ff0000;" /></td>
			</tr>
		</table>
		


		<form:hidden path="productionInvoiceId"
			value="${productionInvoiceBean.productionInvoiceId}" />
		<input type="submit" value="Submit" />&nbsp;<input type="button"
			name="cancel" value="Cancel" onclick="loadIndex('productioninvoice')" />
	</form:form>
	<div class="overlay-bg"></div>
	<div class="overlay-content popup2">
		<iframe
			src="/ERPSoftware/productionorder/ProductionOrderSelectionList/"
			frameborder="0" scrolling="auto" width="95%" height="500px"></iframe>
	</div>
	<div class="overlay-content popup1">
		<iframe
			src="/ERPSoftware/product/ProductSelectionList/productionInvoiceItemBeans"
			frameborder="0" scrolling="auto" width="95%" height="500px"></iframe>
	</div>

</body>
</html>