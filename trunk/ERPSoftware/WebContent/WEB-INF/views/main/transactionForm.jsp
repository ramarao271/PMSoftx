<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Transaction</title>
<style type="text/css">
a {
	font-size: 12px;
	text-decoration: none;
	color: white
}

table {
	background-color: black;
}
</style>
</head>
<body>
	<table cellspacing="0" >
		<tr>
			<td><a href="/ERPSoftware/salesorder/addSalesOrder.html"
				target="transactionFrame">Sales Orders</a></td>
			<td><a
				href="/ERPSoftware/deliverychallan/addDeliveryChallan.html"
				target="transactionFrame">Delivery Challans</a></td>
			<td><a href="/ERPSoftware/salesinvoice/addSalesInvoice.html"
				target="transactionFrame">Sales Invoice</a></td>
			<td><a href="/ERPSoftware/salesPayment/addSalesPayment.html"
				target="transactionFrame">Sales Payment</a></td>
		</tr>
		<tr>
			<td colspan="4"><iframe name="transactionFrame"
					src="transaction.jsp" frameborder="0" width="910" height="560"></iframe></td>
		</tr>
	</table>
</body>
</html>