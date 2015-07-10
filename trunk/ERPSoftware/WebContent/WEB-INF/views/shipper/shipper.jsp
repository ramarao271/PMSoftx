<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ERP Software</title>

<c:url value="/resources/autocomplete/demos.css" var="democss" />
<link rel="stylesheet" href="${democss}">
<c:url value="/resources/js/global.js" var="globaljs" />
<script type="text/javascript" src="${globaljs}"></script>
<c:url value="/resources/css/global.css" var="stylecss" />
<link href="${stylecss}" rel="stylesheet" />
</head>
<body>
	<h3>
		<b><c:if test="${!empty operation}">
				<c:out value="${operation}" />
			</c:if> Shipper Details</b>
	</h3>
	<form:form name="personForm" method="POST"
		action="/ERPSoftware/shipper/saveShipper.html"
		modelAttribute="shipperBean">
		<table>
			<tr>
				<td><form:label path="shipperName">Shipper Name</form:label></td>
				<td><form:input path="shipperName"
						value="${shipperBean.shipperName}" /></td>

				<td>*<form:errors path="shipperName" cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyName">Company Name:</form:label></td>
				<td><form:input path="companyName"
						value="${shipperBean.companyName}" /></td>

				<td>*<form:errors path="companyName" cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyBranch">Company Branch:</form:label></td>
				<td><form:input path="companyBranch"
						value="${shipperBean.companyBranch}" /></td>
				<td><form:errors path="companyBranch"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyTelephone1">Company Telephone1:</form:label></td>
				<td><form:input path="companyTelephone1" type="number" /></td>
				<td><form:errors path="companyTelephone1"
						cssStyle="color: #ff0000;" /></td>
			</tr>

			<tr>
				<td><form:label path="companyTelephone2">Company Telephone2:</form:label></td>
				<td><form:input path="companyTelephone2"
						value="${shipperBean.companyTelephone2}" type="number" /></td>
				<td><form:errors path="companyTelephone2"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyMobile1">Company Mobile1 :</form:label></td>
				<td><form:input path="companyMobile1"
						value="${shipperBean.companyMobile1}" type="number" /></td>
				<td>*<form:errors path="companyMobile1"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyMobile2">Company Mobile2:</form:label></td>
				<td><form:input path="companyMobile2"
						value="${shipperBean.companyMobile2}" type="number" /></td>
				<td><form:errors path="companyMobile2"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyEmail">Company Email:</form:label></td>
				<td><form:input path="companyEmail"
						value="${shipperBean.companyEmail}" /></td>
				<td><form:errors path="companyEmail" cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td colspan="3"><b>Address Details</b></td>
			</tr>
			<tr>
				<td><form:label path="companyAddressBean.addressLine1">Adress Line1:</form:label></td>
				<td><form:input path="companyAddressBean.addressLine1"
						value="${shipperBean.companyAddressBean.addressLine1}" /></td>

				<td>*<form:errors path="companyAddressBean.addressLine1"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyAddressBean.addressLine2">Address Line2</form:label></td>
				<td><form:input path="companyAddressBean.addressLine2"
						value="${shipperBean.companyAddressBean.addressLine2}" /></td>

				<td><form:errors path="companyAddressBean.addressLine2"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyAddressBean.pinCode">Pin code</form:label></td>
				<td><form:input path="companyAddressBean.pinCode"
						value="${shipperBean.companyAddressBean.pinCode}" type="number" /></td>

				<td>*<form:errors path="companyAddressBean.pinCode"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyAddressBean.city">City</form:label></td>
				<td><form:input path="companyAddressBean.city"
						value="${shipperBean.companyAddressBean.city}" /></td>

				<td><form:errors path="companyAddressBean.city"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyAddressBean.district">District:</form:label></td>
				<td><form:input path="companyAddressBean.district"
						value="${shipperBean.companyAddressBean.district}" /></td>

				<td>*<form:errors path="companyAddressBean.district"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyAddressBean.state">State:</form:label></td>
				<td><form:input path="companyAddressBean.state"
						value="${shipperBean.companyAddressBean.state}" /></td>

				<td>*<form:errors path="companyAddressBean.state"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyAddressBean.country">Country:</form:label></td>
				<td><form:input path="companyAddressBean.country"
						value="${shipperBean.companyAddressBean.country}" /></td>

				<td>*<form:errors path="companyAddressBean.country"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="openingBalance">Opening Balance: </form:label></td>
				<td><form:input path="openingBalance"
						value="${shipperBean.openingBalance}" /></td>
				<td><form:errors path="openingBalance"
						cssStyle="color: #ff0000;" /></td>
			</tr>


		</table>
		<input type="submit" value="Submit" />&nbsp;<input type="button"
			name="cancel" value="Cancel" onclick="loadIndex('shipper')" />
		<form:input path="shipperId" cssStyle="visibility:hidden"
			value="${shipperBean.shipperId}" />
		<form:input path="companyAddressBean.addressId"
			cssStyle="visibility:hidden"
			value="${shipperBean.companyAddressBean.addressId}" />
	</form:form>
</body>
</html>