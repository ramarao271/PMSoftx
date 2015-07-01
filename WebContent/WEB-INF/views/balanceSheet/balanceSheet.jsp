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
					url : "/ERPSoftware/balancesheet/listSOCustomers.jsp",
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
					url : "/ERPSoftware/balancesheet/listSOCustomers.jsp",
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
											if(val.companyBranch!=undefined)
											{
										
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
	function calculateCost()
	{
		var count=parseInt(document.personForm.balanceSheet.value);
		var total=0.0;
		for(var i=0;i<count;i++)
			{
				var rate="balanceSheetItemBeans"+i+".rate";
				var rateValue=parseFloat(document.getElementById(rate).value);
				var totalRate=(rateValue || 0);
				total=parseFloat(total)+parseFloat(totalRate);
			}
		document.getElementById("totalCost").value=parseFloat(total).toFixed(2);
	}
	function makePermanent(id)
	{
		if(checkUpdate())
			{
			document.personForm.action = "/ERPSoftware/balanceSheet/updateBalanceSheet/"+id;
			document.personForm.method="GET";
			document.personForm.submit();	
			}
		
	}
</script>
</head>
<body onload="loadBranches();calculateCost()">
	<h3>
		<b><c:if test="${!empty operation}">
				<c:out value="${operation}" />
			</c:if> Balance Sheet</b>
	</h3>
	<br />
	<form:form name="personForm" method="POST"
		action="/ERPSoftware/balanceSheet/saveBalanceSheet.html"
		modelAttribute="balanceSheetBean">
		<table cellpadding="0" cellspacing="5">
			<tr>
				<td>Available Cash</td>
				<td><c:out value="${balance}" /></td>
			</tr>
			<tr>
				<td><form:label path="balanceSheetDate">Date:</form:label></td>
				<td><fmt:formatDate var="formattedDate" pattern="MM-dd-yyyy"
						value="${balanceSheetBean.balanceSheetDate}" /> <form:input
						path="balanceSheetDate" value="${formattedDate}" /> <script
						type="text/javascript">
							new datepickr('balanceSheetDate', {
								'dateFormat' : 'm-d-y'
							});
						</script> <form:errors path="balanceSheetDate" cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td>Select No. of Items</td>
				<td><form:select path="" name="balanceSheet"
						onchange="setItemsFields(this.value,this.name)">
						<c:forEach var="i" begin="1" end="25">
							<c:choose>
								<c:when
									test="${fn:length(balanceSheetBean.balanceSheetItemBeans) eq i}">
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
							<c:if test="${!(balanceSheetBean.balanceSheetId eq 0)}">
								<td>Del</td>
							</c:if>
							<td><form:label
									path="balanceSheetItemBeans[0].expenseBeanId.expenseName">Expense</form:label></td>
							<td><form:label path="balanceSheetItemBeans[0].description">Description</form:label></td>
							<td><form:label path="balanceSheetItemBeans[0].rate">Cost</form:label></td>
						</tr>

						<c:forEach items="${balanceSheetBean.balanceSheetItemBeans}"
							var="i" varStatus="itemsRow">

							<tr>
								<c:if test="${!(i.balanceSheetId eq 0)}">
									<td><a href="#"
										onClick="deleteItemsFields(${i.srNo}, 'balancesheet')"> <img
											alt="" src="${deleteImg}">
									</a></td>
								</c:if>
								<td><form:select
										path="balanceSheetItemBeans[${itemsRow.index}].expenseBeanId.expenseId"
										class="branch">
										<c:forEach items="${expenses}" var="expense">
											<c:choose>
												<c:when
													test="${i.expenseBeanId.expenseId eq expense.expenseId}">
													<form:option selected="true" value="${expense.expenseId}">${expense.expenseName}</form:option>
												</c:when>
												<c:otherwise>
													<form:option value="${expense.expenseId}">${expense.expenseName}</form:option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</form:select></td>

								<td><form:input
										path="balanceSheetItemBeans[${itemsRow.index}].description"
										value="${i.description}" class="expense_Description" /> <form:errors
										path="balanceSheetItemBeans[${itemsRow.index}].description"
										cssStyle="color: #ff0000;" /></td>
								<td><form:input
										path="balanceSheetItemBeans[${itemsRow.index}].rate"
										value="${i.rate}" class="expense_Unit_Rate" type="number"
										step="0.01" onkeyup="calculateCost()"
										onchange="calculateCost()" /> <form:errors
										path="balanceSheetItemBeans[${itemsRow.index}].rate"
										cssStyle="color: #ff0000;" /> <form:hidden
										path="balanceSheetItemBeans[${itemsRow.index}].balanceSheetId"
										value="${i.balanceSheetId}" class="expense_Srno" /> <form:hidden
										path="balanceSheetItemBeans[${itemsRow.index}].srNo"
										value="${itemsRow.index}" class="expense_Srno" />
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
			<tr>
				<td><form:label path="totalCost">Total Cost:</form:label></td>
				<td align="right"><form:input path="totalCost"
						value="${balanceSheetBean.totalCost}" class="totalCost"
						type="number" readonly="true" /></td>
			</tr>
		</table>
		<form:hidden path="balanceSheetId"
			value="${balanceSheetBean.balanceSheetId}" />
		<input type="submit" value="Save(Temporary)" />&nbsp;
			&nbsp;<input
			type="button" name="cancel" value="Cancel"
			onclick="loadIndex('balanceSheet')" />
	</form:form>
</body>
</html>