<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
	function setProduct(id, name, code, quantity) {
		var productName = "purchaseInvoiceItemBeans" + serial
				+ ".productId.productName";
		var productCode = "purchaseInvoiceItemBeans" + serial
				+ ".productId.productCode";
		var productId = "purchaseInvoiceItemBeans" + serial
				+ ".productId.productId";
		var quantityf = "purchaseInvoiceItemBeans" + serial + ".quantityType";
		document.getElementById(productName).value = name;
		document.getElementById(productCode).value = code;
		document.getElementById(productId).value = id;
		document.getElementById(quantityf).value = quantity;
		$('.overlay-bg, .overlay-content').hide();
	}
	function setSupplierId(value) {
		var supplierId = "supplierId" + value;
		document.getElementById("supplierBean.supplierId").value = document
				.getElementById(supplierId).value;
	}

	$(function() {
		function log(message) {
			$("<div>").text(message).prependTo("#log");
			$("#log").scrollTop(0);
		}

		$("#cityx").autocomplete({
			source : function(request, response) {
				$.ajax({
					url : "/ERPSoftware/purchaseinvoice/listSISuppliers.jsp",
					dataType : "json",
					data : {
						style : "full",
						maxRows : 12,
						name_startsWith : request.term
					},
					success : function(data) {
						response($.map(data, function(item) {
							return {
								value : item.companyName,
								id : item.supplierId,
							};
						}));
					}
				});
			},
			minLength : 1,
			select : function(event, ui) {
				loadBranches(ui.item.value);
				$(".supplierId").val(ui.item.id);
			},
			open : function() {
				$(this).removeClass("ui-corner-all").addClass("ui-corner-top");
			},
			close : function() {
				$(this).removeClass("ui-corner-top").addClass("ui-corner-all");
			}
		});
	});
	function loadBranches(branch) {

		var company = branch;
		if (company == undefined) {
			company = $("#cityx").val();
		}
		var items = [];
		$
				.ajax({
					url : "/ERPSoftware/purchaseinvoice/listSISuppliers.jsp",
					dataType : "json",
					data : {
						style : "full",
						maxRows : 12,
						company : company
					},
					success : function(data) {
						var items = [];
						var sids = [];
						var i = 0;
						$
								.each(
										data,
										function(key, val) {
											var sid = $(".supplierId").val();
											if (i == 0) {
												if (sid == undefined) {
													$(".supplierId").val(
															val.supplierId);
												}
												i++;
											}
											if (val.companyBranch != undefined) {

												if (sid == val.supplierId) {

													items
															.push("<option selected='true' value='" + val.companyBranch + "'>"
																	+ val.companyBranch
																	+ "</option>");

												} else {
													items
															.push("<option value='" + val.companyBranch + "'>"
																	+ val.companyBranch
																	+ "</option>");
												}
												sids
														.push("<input type='hidden' value='"+val.supplierId+"' id='supplierId"+val.companyBranch+"' />");
											}
										});
						$cname = $("select[class='branch']");
						$("select[class='branch'] option").remove();
						$(items.join("")).appendTo($cname);
						$("#branchSids").append(sids.join(""));

					}
				});

	}
	function calculateCost() {
		var count = parseInt(document.personForm.purchaseinvoice.value);
		var total = 0.0;
		for (var i = 0; i < count; i++) {
			var qty = "purchaseInvoiceItemBeans" + i + ".quantity";
			var rate = "purchaseInvoiceItemBeans" + i + ".rate";
			var totalCost = "purchaseInvoiceItemBeans" + i + ".totalCost";
			var qtyValue = parseInt(document.getElementById(qty).value);
			var rateValue = parseFloat(document.getElementById(rate).value);
			var totalRate = (qtyValue || 0) * (rateValue || 0);
			document.getElementById(totalCost).value = totalRate.toFixed(2);
			total = parseFloat(total) + parseFloat(totalRate);
		}
		document.getElementById("invoiceAmount").value = parseFloat(total).toFixed(2);
		var discountPercent=parseFloat(document.getElementById("discountPercent").value);
		var discountAmount=(parseFloat(discountPercent/100 || 0)*parseFloat(total)).toFixed(2);
		document.getElementById("discountedAmount").value = parseFloat(discountAmount).toFixed(2);
		document.getElementById("totalCost").value = parseFloat(parseFloat(total)-parseFloat(discountAmount)).toFixed(2);
	}
</script>
</head>
<body onload="loadBranches();calculateCost()">
	<h3>
		<b><c:if test="${!empty operation}">
				<c:out value="${operation}" />
			</c:if> Purchase Invoice</b>
	</h3>
	<br />

	<!-- supplierId purchaseInvoiceItemBeans -->
	<form:form name="personForm" method="POST"
		action="/ERPSoftware/purchaseinvoice/savePurchaseInvoice.html"
		modelAttribute="purchaseInvoiceBean">
		<form:hidden path="supplierBean.supplierId" class="supplierId" />
		<table cellpadding="0" cellspacing="5">
			<tr>
				<td>select Delivery Challan No</td>
				<td><form:input path="deliveryChallanBean.deliveryChallanId"
						readonly="true" href="#" class="show-popup" data-showpopup="2"
						value="${purchaseInvoiceBean.deliveryChallanBean.deliveryChallanId}" /></td>
			</tr>
			<tr>
				<td>Select Supplier Company</td>
				<td><form:input path="supplierBean.companyName" id="cityx" />
				</td>
			</tr>
			<tr>
				<td>Company Branch</td>
				<td><form:select path="supplierBean.companyBranch"
						class="branch" onchange="setSupplierId(this.value)"></form:select>
					<div id="branchSids"></div></td>
			</tr>
			
			<tr>
				<td><form:label path="purchaseInvoiceDate">Date</form:label></td>
				<td><fmt:formatDate var="formattedDate" pattern="MM-dd-yyyy"
						value="${purchaseInvoiceBean.purchaseInvoiceDate}" /> <form:input
						path="purchaseInvoiceDate" value="${formattedDate}" /> <script
						type="text/javascript">
							new datepickr('purchaseInvoiceDate', {
								'dateFormat' : 'm-d-y'
							});
						</script> <form:errors path="purchaseInvoiceDate" cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td>L.R No</td>
				<td><form:input path="lrNo" class="branch" value="${purchaseInvoiceBean.lrNo }" />
				</td>
			</tr>
			<tr>
				<td>Select No. of Items</td>
				<td><form:select path="" name="purchaseinvoice"
						onchange="setItemsFields(this.value,this.name)">
						<c:forEach var="i" begin="1" end="25">
							<c:choose>
								<c:when
									test="${fn:length(purchaseInvoiceBean.purchaseInvoiceItemBeans) eq i}">
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
							<td><form:label
									path="purchaseInvoiceItemBeans[0].productId.productName">Product Name</form:label></td>
							<td><form:label
									path="purchaseInvoiceItemBeans[0].productId.productCode">Code</form:label></td>
							<td><form:label path="purchaseInvoiceItemBeans[0].description">Description</form:label></td>
							<td><form:label path="purchaseInvoiceItemBeans[0].quantity">Quantity</form:label></td>
							<td><form:label path="purchaseInvoiceItemBeans[0].quantityType">Qty Type</form:label></td>
							<td><form:label path="purchaseInvoiceItemBeans[0].rate">Unit Rate</form:label></td>
							<td><form:label path="purchaseInvoiceItemBeans[0].totalCost">Total Cost</form:label></td>
						</tr>

						<c:forEach items="${purchaseInvoiceBean.purchaseInvoiceItemBeans}"
							var="i" varStatus="itemsRow">
							<tr>
								<td><form:hidden
										path="purchaseInvoiceItemBeans[${itemsRow.index}].productId.productId"
										value="${i.productId.productId }" /> <form:hidden
										path="purchaseInvoiceItemBeans[${itemsRow.index}].variantId"
										value="${i.variantId}" /> <form:input
										path="purchaseInvoiceItemBeans[${itemsRow.index}].productId.productName"
										value="${i.productId.productName}" readonly="true" /> <form:errors
										path="purchaseInvoiceItemBeans[${itemsRow.index}].productId.productName"
										cssStyle="color: #ff0000;" /></td>
								<td><form:input
										path="purchaseInvoiceItemBeans[${itemsRow.index}].variantCode"
										value="${i.variantCode}" class="product_Code"
										readonly="true" /> <form:errors
										path="purchaseInvoiceItemBeans[${itemsRow.index}].variantCode"
										cssStyle="color: #ff0000;" /></td>
								<td><form:input
										path="purchaseInvoiceItemBeans[${itemsRow.index}].description"
										value="${i.description}" class="product_Description" /> <form:errors
										path="purchaseInvoiceItemBeans[${itemsRow.index}].description"
										cssStyle="color: #ff0000;" /></td>
								<td><form:input
										path="purchaseInvoiceItemBeans[${itemsRow.index}].quantity"
										value="${i.quantity}" class="product_quantity" type="number"
										step="0.01" onkeyup="calculateCost()" readonly="true" 
										onchange="calculateCost()" /> <form:errors
										path="purchaseInvoiceItemBeans[${itemsRow.index}].quantity"
										cssStyle="color: #ff0000;" /></td>
								<td><form:input
										path="purchaseInvoiceItemBeans[${itemsRow.index}].quantityType"
										value="${i.quantityType}" class="product_quantityType" readonly="true"  /> <form:errors
										path="purchaseInvoiceItemBeans[${itemsRow.index}].quantityType"
										cssStyle="color: #ff0000;" />
								<td><form:input
										path="purchaseInvoiceItemBeans[${itemsRow.index}].rate"
										value="${i.rate}" class="product_Unit_Rate" type="number"
										step="0.01" onkeyup="calculateCost()" readonly="true" 
										onchange="calculateCost()" /> <form:errors
										path="purchaseInvoiceItemBeans[${itemsRow.index}].rate"
										cssStyle="color: #ff0000;" />
								<td><form:input
										path="purchaseInvoiceItemBeans[${itemsRow.index}].totalCost"
										value="${i.totalCost}" class="product_Total_Rate"
										type="number" step="0.01" readonly="true" /> <form:errors
										path="purchaseInvoiceItemBeans[${itemsRow.index}].totalCost"
										cssStyle="color: #ff0000;" /></td>
								<form:hidden
									path="purchaseInvoiceItemBeans[${itemsRow.index}].srNo"
									value="${i.srNo}" class="product_Srno" />
									<form:hidden
									path="purchaseInvoiceItemBeans[${itemsRow.index}].purchaseInvoiceId"
									value="${i.purchaseInvoiceId}" class="product_Srno" />
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
			
			<tr>
				<td></td>
				<td align="right"><form:label path="invoiceAmount">Invoice Amount:</form:label><form:input path="invoiceAmount"
						value="${purchaseInvoiceBean.invoiceAmount}" class="totalCost"
						type="number" readonly="true" /></td>
			</tr>
			<tr>
				<td></td>
				<td align="right"><form:label path="discountPercent">Discount %</form:label><form:input path="discountPercent"
						value="${purchaseInvoiceBean.discountPercent}" class="totalCost" onkeyup="calculateCost()" onchange="calculateCost()"
						type="number"  /></td>
			</tr>
			<tr>
				<td></td>
				<td align="right"><form:label path="discountedAmount">Discount Amount</form:label><form:input path="discountedAmount"
						value="${purchaseInvoiceBean.discountedAmount}" class="totalCost"
						type="number" readonly="true" /></td>
			</tr>
			<tr>
				<td></td>
				<td align="right"><form:label path="totalCost">Total Cost:</form:label><form:input path="totalCost"
						value="${purchaseInvoiceBean.totalCost}" class="totalCost"
						type="number" readonly="true" /></td>
			</tr>
		</table>
		<form:hidden path="purchaseInvoiceId"
			value="${purchaseInvoiceBean.purchaseInvoiceId}" />
		<form:hidden path="paidAmount" value="${purchaseInvoiceBean.paidAmount}" />

		<input type="submit" value="Save" name="Save" />
		<input type="submit" value="Save & Payment" name="SaveDC" />&nbsp;<input type="button"
			name="cancel" value="Cancel" onclick="loadIndex('purchaseinvoice')" />
	</form:form>

	<div class="overlay-bg"></div>
	<div class="overlay-content popup1">
		<iframe src="/ERPSoftware/product/ProductSelectionList/purchaseInvoiceItemBeans"
			frameborder="0" scrolling="auto" width="95%" height="350px"></iframe>
	</div>
	<div class="overlay-content popup2">
		<iframe
			src="/ERPSoftware/deliverychallan/DeliveryChallanSelectionList.html"
			frameborder="0" scrolling="auto" width="95%" height="350px"></iframe>
	</div>
</body>
</html>