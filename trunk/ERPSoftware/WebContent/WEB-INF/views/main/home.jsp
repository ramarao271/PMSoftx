<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="pl" xml:lang="pl">
<head>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jspt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>ERP Software</title>
<spring:url value="/resources/css/style.css" var="stylecss" />
<link href="${stylecss}" rel="stylesheet" />
<spring:url value="/resources/css/navi.css" var="navicss" />
<link href="${navicss}" rel="stylesheet" />
<spring:url value="/resources/js/global.js" var="globaljs" />
<script type="text/javascript" src="${globaljs}"></script>
<%-- <spring:url value="/resources/overlay/jquery-1.11.2.min.js"
	var="jqueryjs" />
<script type="text/javascript" src="${jqueryjs}"></script>
 --%><spring:url value="/resources/js/jquery-1.9.1.js"
	var="jqueryj1" />
<script type="text/javascript" src="${jqueryj1}"></script>

<script type="text/javascript">
	$(window).load(function() {
		doIframe();
	});
	
	$(function() {
		$(".box .h_title").not(this).next("ul").hide("normal");
		$(".box .h_title").not(this).next("#home").show("normal");
		$(".box").children(".h_title").click(function() {
			$(this).next("ul").slideToggle();
		});
	});
	function load(x) {
		document.getElementById("linksfrm").src = x;
	}
	function setFinYear(val) {
		document.mainformx.action = "/ERPSoftware/main/year/" + val;
		document.mainformx.submit();
	}
	
</script>
<link href="${stylecss}" rel="stylesheet" />
</head>
<body>

	<form name="mainformx">
		<!-- <div class="wrap"> -->
		<div id="header">
			<div id="top">
				<div class="left">
					<p>
						Welcome, <strong><spring:if test="${!empty userBean}">
								<spring:out value="${userBean.username}" />
							</spring:if>:</strong> [ <a href="/ERPSoftware/main/logout">logout</a> ]
					</p>
				</div>
				<div class="right">
					<div class="align-right">
						<p>
							Financial Year: <a href="financial.html" target="mainFrame"><strong><spring:if
										test="${!empty userBean}">
										<spring:out value="${userBean.finYear}" />
									</spring:if></strong></a>
						</p>
					</div>
				</div>
			</div>
			<div id="nav">
				<ul>
				<li class="upp"><a id="myButton" href="/ERPSoftware/main/transactionForm.html">Transaction</a></li>
					<li class="upp"><a href="/ERPSoftware/product/inventory"
						target="mainFrame">Inventory</a></li>
					<li class="upp"><a href="#">StakeHolders</a>
						<ul>
							<li>&#8250; <a href="/ERPSoftware/customer/customer/"
								target="mainFrame">Customers</a></li>
							<li>&#8250; <a href="/ERPSoftware/supplier/supplier/"
								target="mainFrame">Suppliers</a></li>
							<li>&#8250; <a href="/ERPSoftware/shipper/shipper/"
								target="mainFrame">Shippers</a></li>
							<li>&#8250; <a href="/ERPSoftware/worker/worker/"
								target="mainFrame">Workers</a></li>
						</ul></li>
					<li class="upp"><a href="#">Orders</a>
						<ul>
							<li>&#8250; <a href="/ERPSoftware/salesorder/salesorder/"
								target="mainFrame">Sales Orders</a></li>
							<li>&#8250; <a
								href="/ERPSoftware/purchaseorder/purchaseorder/"
								target="mainFrame">Purchase Orders</a></li>
							<li>&#8250; <a
								href="/ERPSoftware/productionorder/productionorder/"
								target="mainFrame">Production Orders</a></li>
						</ul></li>

					<li class="upp"><a
						href="/ERPSoftware/deliverychallan/deliverychallan/"
						target="mainFrame">Delivery Challans</a></li>
					<li class="upp"><a href="#">Invoice</a>
						<ul>
							<li>&#8250; <a
								href="/ERPSoftware/salesinvoice/salesinvoice/"
								target="mainFrame">Sales Invoice</a></li>

							<li>&#8250; <a
								href="/ERPSoftware/purchaseinvoice/purchaseinvoice/"
								target="mainFrame">Purchase Invoice</a></li>
							<li>&#8250; <a
								href="/ERPSoftware/productioninvoice/productioninvoice/"
								target="mainFrame">Production Invoice</a></li>

						</ul></li>
					<li class="upp"><a href="#">Payments</a>
						<ul>
							<li>&#8250; <a
								href="/ERPSoftware/salesPayment/salesPayment/"
								target="mainFrame">Sales Payment</a></li>
							<li>&#8250; <a
								href="/ERPSoftware/purchasePayment/purchasePayment/"
								target="mainFrame">Purchase Payment</a></li>
								<li>&#8250; <a
								href="/ERPSoftware/otherPayment/otherPayment/"
								target="mainFrame">Other Payments</a></li>
						</ul></li>
					<li class="upp"><a href="#">Returns</a>
						<ul>
							<li>&#8250; <a href="/ERPSoftware/salesreturn/salesreturn/"
								target="mainFrame">Sales Return</a></li>
							<li>&#8250; <a
								href="/ERPSoftware/purchasereturn/purchasereturn/"
								target="mainFrame">Purchase Return</a></li>
						</ul></li>

					<li class="upp"><a href="/ERPSoftware/balanceSheet/balanceSheet/"
						target="mainFrame">Balance Sheet</a></li>
						<li class="upp"><a href="/ERPSoftware/tools/tools/"
						target="mainFrame">Tools</a></li>
					
					<li class="upp"><a href="#">Reports</a>
						<ul>
							<li>&#8250; <a href="/ERPSoftware/financialReports/FR.html" target="mainFrame">Finance Reports</a></li>
							<li>&#8250; <a href="" target="mainFrame">Operations Reports</a></li>
							<li>&#8250; <a href="/ERPSoftware/productReports/PR.html" target="mainFrame">Product Reports</a></li>
							<li>&#8250; <a href="/ERPSoftware/marketingReports/MR.html" target="mainFrame">Marketing Reports</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
		<!-- </div> -->
		<table border="0" cellpadding="0" cellspacing="0">
			<tr valign="top">
				<td><iframe src="/ERPSoftware/main/links.jsp" id="linksfrm"
						name="sideFrame" frameborder="0" width="140px" height="600px"
						scrolling="no"></iframe></td>
				<td><iframe src="/ERPSoftware/main/hello.jsp" name="mainFrame"
						frameborder="0" width="800px" height="600px" class="autoHeight"
						onload="this.width=window.innerWidth-160;this.height=window.innerHeight-80;"></iframe></td>
			</tr>
		</table>
	</form>
</body>
</html>
