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
	function setProduct(id, name, code, quantity) {
		var productName = "salesReturnItemBeans" + serial
				+ ".productId.productName";
		var productCode = "salesReturnItemBeans" + serial
				+ ".productId.productCode";
		var productId = "salesReturnItemBeans" + serial
				+ ".productId.productId";
		var quantityf = "salesReturnItemBeans" + serial + ".quantityType";
		document.getElementById(productName).value = name;
		document.getElementById(productCode).value = code;
		document.getElementById(productId).value = id;
		document.getElementById(quantityf).value = quantity;
		$('.overlay-bg, .overlay-content').hide();
	}
	function setCustomerId(value) {
		var customerId = "customerId" + value;
		document.getElementById("customerBean.customerId").value = document
				.getElementById(customerId).value;
	}

	$(function() {
		function log(message) {
			$("<div>").text(message).prependTo("#log");
			$("#log").scrollTop(0);
		}

		$("#cityx").autocomplete({
			source : function(request, response) {
				$.ajax({
					url : "/ERPSoftware/salesreturn/listSRCustomers.jsp",
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
								id : item.customerId,
							};
						}));
					}
				});
			},
			minLength : 1,
			select : function(event, ui) {
				loadBranches(ui.item.value);
				$(".customerId").val(ui.item.id);
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
					url : "/ERPSoftware/salesreturn/listSRCustomers.jsp",
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
											var sid = $(".customerId").val();
											if (i == 0) {
												if (sid == undefined) {
													$(".customerId").val(
															val.customerId);
												}
												i++;
											}
											if (val.companyBranch != undefined) {

												if (sid == val.customerId) {

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
														.push("<input type='hidden' value='"+val.customerId+"' id='customerId"+val.companyBranch+"' />");
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
		var count = parseInt(document.personForm.salesreturn.value);
		var total = 0.0;
		for (var i = 0; i < count; i++) {
			var qty = "salesReturnItemBeans" + i + ".quantity";
			var rate = "salesReturnItemBeans" + i + ".rate";
			var totalCost = "salesReturnItemBeans" + i + ".totalCost";
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
<body onload="loadBranches();calculateCost()">
	<h3>
		<b><c:if test="${!empty operation}">
				<c:out value="${operation}" />
			</c:if> Sales Return</b>
	</h3>
	<br />

	<!-- customerId salesReturnItemBeans -->
	<form:form name="personForm" method="POST"
		action="/ERPSoftware/salesreturn/saveSalesReturn.html"
		modelAttribute="salesReturnBean">
		<form:hidden path="customerBean.customerId" class="customerId" />
		<table cellpadding="0" cellspacing="5">
			<tr>
				<td>select Sales Invoice No</td>
				<td><form:input path="salesInvoiceBean.salesInvoiceId"
						readonly="true" href="#" class="show-popup" data-showpopup="2"
						value="${salesReturnBean.salesInvoiceBean.salesInvoiceId}" /></td>
			</tr>
			<tr>
				<td>Select Customer Company</td>
				<td><form:input path="customerBean.companyName" id="cityx" />
				</td>
			</tr>
			<tr>
				<td>Company Branch</td>
				<td><form:select path="customerBean.companyBranch"
						class="branch" onchange="setCustomerId(this.value)"></form:select>
					<div id="branchSids"></div></td>
			</tr>

			<tr>
				<td><form:label path="salesReturnDate">Date</form:label></td>
				<td><fmt:formatDate var="formattedDate" pattern="MM-dd-yyyy"
						value="${salesReturnBean.salesReturnDate}" /> <form:input
						path="salesReturnDate" value="${formattedDate}" /> <script
						type="text/javascript">
							new datepickr('salesReturnDate', {
								'dateFormat' : 'm-d-y'
							});
						</script> <form:errors path="salesReturnDate" cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td>L.R No</td>
				<td><form:input path="lrNo" class="branch"
						value="${salesReturnBean.lrNo }" /></td>
			</tr>
			<tr>
				<td>Select No. of Items</td>
				<td><form:select path="" name="salesreturn"
						onchange="setItemsFields(this.value,this.name)">
						<c:forEach var="i" begin="1" end="25">
							<c:choose>
								<c:when
									test="${fn:length(salesReturnBean.salesReturnItemBeans) eq i}">
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
							<td>Del</td>
							<td><form:label
									path="salesReturnItemBeans[0].productId.productName">Product Name</form:label></td>
							<td><form:label
									path="salesReturnItemBeans[0].productId.productCode">Code</form:label></td>
							<td><form:label path="salesReturnItemBeans[0].description">Description</form:label></td>
							<td><form:label path="salesReturnItemBeans[0].quantity">Quantity</form:label></td>
							<td><form:label path="salesReturnItemBeans[0].quantityType">Qty Type</form:label></td>
							<td><form:label path="salesReturnItemBeans[0].rate">Unit Rate</form:label></td>
							<td><form:label path="salesReturnItemBeans[0].totalCost">Total Cost</form:label></td>
						</tr>

						<c:forEach items="${salesReturnBean.salesReturnItemBeans}" var="i"
							varStatus="itemsRow">
							<tr>
								<td><a href="#"
									onClick="deleteItemsFields(${i.srNo}, 'salesreturn')"> <img
										alt="" src="${deleteImg}">
								</a></td>
								<td><form:hidden
										path="salesReturnItemBeans[${itemsRow.index}].productId.productId"
										value="${i.productId.productId }" /> <form:hidden
										path="salesReturnItemBeans[${itemsRow.index}].variantId"
										value="${i.variantId}" /> <form:input
										path="salesReturnItemBeans[${itemsRow.index}].productId.productName"
										value="${i.productId.productName}" readonly="true" /> <form:errors
										path="salesReturnItemBeans[${itemsRow.index}].productId.productName"
										cssStyle="color: #ff0000;" /></td>
								<td><form:input
										path="salesReturnItemBeans[${itemsRow.index}].variantCode"
										value="${i.variantCode}" class="product_Code" readonly="true" />
									<form:errors
										path="salesReturnItemBeans[${itemsRow.index}].variantCode"
										cssStyle="color: #ff0000;" /></td>
								<td><form:input
										path="salesReturnItemBeans[${itemsRow.index}].description"
										value="${i.description}" class="product_Description" /> <form:errors
										path="salesReturnItemBeans[${itemsRow.index}].description"
										cssStyle="color: #ff0000;" /></td>
								<td><form:input
										path="salesReturnItemBeans[${itemsRow.index}].quantity"
										value="${i.quantity}" class="product_quantity" type="number"
										step="0.01" onkeyup="calculateCost()"
										onchange="calculateCost()" /> <form:errors
										path="salesReturnItemBeans[${itemsRow.index}].quantity"
										cssStyle="color: #ff0000;" /></td>
								<td><form:input
										path="salesReturnItemBeans[${itemsRow.index}].quantityType"
										value="${i.quantityType}" class="product_quantityType" /> <form:errors
										path="salesReturnItemBeans[${itemsRow.index}].quantityType"
										cssStyle="color: #ff0000;" />
								<td><form:input
										path="salesReturnItemBeans[${itemsRow.index}].rate"
										value="${i.rate}" class="product_Unit_Rate" type="number"
										step="0.01" onkeyup="calculateCost()"
										onchange="calculateCost()" /> <form:errors
										path="salesReturnItemBeans[${itemsRow.index}].rate"
										cssStyle="color: #ff0000;" />
								<td><form:input
										path="salesReturnItemBeans[${itemsRow.index}].totalCost"
										value="${i.totalCost}" class="product_Total_Rate"
										type="number" step="0.01" readonly="true" /> <form:errors
										path="salesReturnItemBeans[${itemsRow.index}].totalCost"
										cssStyle="color: #ff0000;" /></td>
								<form:hidden path="salesReturnItemBeans[${itemsRow.index}].srNo"
									value="${i.srNo}" class="product_Srno" />
								<form:hidden
									path="salesReturnItemBeans[${itemsRow.index}].salesReturnId"
									value="${i.salesReturnId}" class="product_Srno" />
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
			<tr>
				<td><form:label path="totalCost">Total Cost:</form:label></td>
				<td align="right"><form:input path="totalCost"
						value="${salesReturnBean.totalCost}" class="totalCost"
						type="number" readonly="true" /></td>
			</tr>
		</table>
		<form:hidden path="salesReturnId"
			value="${salesReturnBean.salesReturnId}" />
		<form:hidden path="paidAmount" value="${salesReturnBean.paidAmount}" />

		<input type="submit" value="Submit" />&nbsp;<input type="button"
			name="cancel" value="Cancel" onclick="loadIndex('salesreturn')" />
	</form:form>

	<div class="overlay-bg"></div>
	<div class="overlay-content popup1">
		<iframe
			src="/ERPSoftware/product/ProductSelectionList/salesReturnItemBeans"
			frameborder="0" scrolling="auto" width="95%" height="350px"></iframe>
	</div>
	<div class="overlay-content popup2">
		<iframe src="/ERPSoftware/salesinvoice/SalesInvoiceSelectionList.html"
			frameborder="0" scrolling="auto" width="95%" height="350px"></iframe>
	</div>
</body>
</html>