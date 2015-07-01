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
<c:url value="/resources/js/global.js" var="globaljs" />
<c:url value="/resources/autocomplete/demos.css" var="democss" />
<link rel="stylesheet" href="${democss}">
<script type="text/javascript" src="${globaljs}"></script>
<c:url value="/resources/css/global.css" var="stylecss" />
<link href="${stylecss}" rel="stylesheet" />
</head>
<body>
	<h3>
		<b><c:if test="${!empty operation}">
				<c:out value="${operation}" />
			</c:if> Customer Details</b>
	</h3>
	<form:form name="personForm" method="POST"
		action="/ERPSoftware/customer/saveCustomer.html" modelAttribute="customerBean">
		<table>
			<tr>
				<td><form:label path="customerName">Customer Name</form:label></td>
				<td><form:input path="customerName"
						value="${customerBean.customerName}" /></td>

				<td>*<form:errors path="customerName"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyName">Company Name:</form:label></td>
				<td><form:input path="companyName"
						value="${customerBean.companyName}" /></td>

				<td>*<form:errors path="companyName" cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyBranch">Company Branch:</form:label></td>
				<td><form:input path="companyBranch"
						value="${customerBean.companyBranch}" /></td>
				<td><form:errors path="companyBranch"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyTelephone1">Company Telephone1:</form:label></td>
				<td><form:input path="companyTelephone1"  type="number"  /></td>
				<td><form:errors path="companyTelephone1"
						cssStyle="color: #ff0000;" /></td>
			</tr>

			<tr>
				<td><form:label path="companyTelephone2">Company Telephone2:</form:label></td>
				<td><form:input path="companyTelephone2"
						value="${customerBean.companyTelephone2}"  type="number" /></td>
				<td><form:errors path="companyTelephone2"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyMobile1">Company Mobile1 :</form:label></td>
				<td><form:input path="companyMobile1"
						value="${customerBean.companyMobile1}"  type="number" /></td>
				<td>*<form:errors path="companyMobile1"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyMobile2">Company Mobile2:</form:label></td>
				<td><form:input path="companyMobile2"
						value="${customerBean.companyMobile2}"  type="number"  /></td>
				<td><form:errors path="companyMobile2"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyEmail">Company Email:</form:label></td>
				<td><form:input path="companyEmail"
						value="${customerBean.companyEmail}" /></td>
				<td><form:errors path="companyEmail"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyTinNo">Company TIN No:</form:label></td>
				<td><form:input path="companyTinNo"
						value="${customerBean.companyTinNo}" /></td>
				<td><form:errors path="companyTinNo"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyCstNo">Company CST No:</form:label></td>
				<td><form:input path="companyCstNo"
						value="${customerBean.companyCstNo}" /></td>
				<td><form:errors path="companyCstNo"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="openingBalance">Opening Balance: </form:label></td>
				<td><form:input path="openingBalance"
						value="${customerBean.openingBalance}" /></td>
				<td><form:errors path="openingBalance"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td colspan="3"><b>Address Details</b></td>
			</tr>
			<tr>
				<td><form:label path="companyAddressBean.addressLine1">Adress Line1:</form:label></td>
				<td><form:input path="companyAddressBean.addressLine1"
						value="${customerBean.companyAddressBean.addressLine1}" /></td>

				<td>*<form:errors path="companyAddressBean.addressLine1"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyAddressBean.addressLine2">Address Line2</form:label></td>
				<td><form:input path="companyAddressBean.addressLine2"
						value="${customerBean.companyAddressBean.addressLine2}" /></td>

				<td><form:errors path="companyAddressBean.addressLine2"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyAddressBean.pinCode">Pin code</form:label></td>
				<td><form:input path="companyAddressBean.pinCode"
						value="${customerBean.companyAddressBean.pinCode}"  type="number"  /></td>

				<td>*<form:errors path="companyAddressBean.pinCode"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyAddressBean.city">City</form:label></td>
				<td><form:input path="companyAddressBean.city"
						value="${customerBean.companyAddressBean.city}" /></td>

				<td><form:errors path="companyAddressBean.city"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyAddressBean.district">District:</form:label></td>
				<td><form:input path="companyAddressBean.district"
						value="${customerBean.companyAddressBean.district}" /></td>

				<td>*<form:errors path="companyAddressBean.district"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyAddressBean.state">State:</form:label></td>
				<td><form:input path="companyAddressBean.state"
						value="${customerBean.companyAddressBean.state}" /></td>

				<td>*<form:errors path="companyAddressBean.state"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			<tr>
				<td><form:label path="companyAddressBean.country">Country:</form:label></td>
				<td><form:input path="companyAddressBean.country"
						value="${customerBean.companyAddressBean.country}" /></td>

				<td>*<form:errors path="companyAddressBean.country"
						cssStyle="color: #ff0000;" /></td>
			</tr>
			
			<tr>
				<td><b>select No of Contact Persons</b></td>
				<td><form:select path="" name="customer" onchange="setPersonFields(this.value,this.name)">
						<c:forEach items="0,1,2,3,4,5" var="i">
							<c:choose>
								<c:when
									test="${fn:length(customerBean.contactPersonsBeans) eq i}">
									<form:option selected="true" value="${i}"></form:option>
								</c:when>
								<c:otherwise>
									<form:option value="${i}"></form:option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select></td>
				<td></td>
			</tr>
			<c:forEach items="${customerBean.contactPersonsBeans}" var="i"
				varStatus="itemsRow">
				<tr>
					<td colspan="3"><form:input
							path="contactPersonsBeans[${itemsRow.index}].contactPersonId"
							cssStyle="visibility:hidden" value="${i.contactPersonId}" /></td>
				</tr>
				<tr>
					<td><form:label
							path="contactPersonsBeans[${itemsRow.index}].contactPersonName">Name</form:label></td>
					<td><form:input
							path="contactPersonsBeans[${itemsRow.index}].contactPersonName"
							value="${i.contactPersonName}" /></td>
					<td>*<form:errors
							path="contactPersonsBeans[${itemsRow.index}].contactPersonName"
							cssStyle="color: #ff0000;" /></td>
				</tr>
				<tr>
					<td><form:label
							path="contactPersonsBeans[${itemsRow.index}].mobileNumber">Mobile Number</form:label></td>
					<td><form:input
							path="contactPersonsBeans[${itemsRow.index}].mobileNumber"
							value="${i.mobileNumber}"  type="number"  /></td>
					<td>*<form:errors
							path="contactPersonsBeans[${itemsRow.index}].mobileNumber"
							cssStyle="color: #ff0000;" /></td>
				</tr>
				<tr>
					<td><form:label
							path="contactPersonsBeans[${itemsRow.index}].emailId">Email Id</form:label></td>
					<td><form:input
							path="contactPersonsBeans[${itemsRow.index}].emailId"
							value="${i.emailId}" /></td>
					<td>*<form:errors
							path="contactPersonsBeans[${itemsRow.index}].emailId"
							cssStyle="color: #ff0000;" /></td>
				</tr>
				
				<tr>
					<td><form:label
							path="contactPersonsBeans[${itemsRow.index}].description">Description</form:label></td>
					<td><form:textarea
							path="contactPersonsBeans[${itemsRow.index}].description"
							value="${i.description}" /></td>
					<td><form:errors
							path="contactPersonsBeans[${itemsRow.index}].description"
							cssStyle="color: #ff0000;" /></td>
				</tr>
			</c:forEach>
			
			<tr>
				<td><b>select No of Bank Accounts</b></td>
				<td><form:select path="" name="customer" onchange="setBankFields(this.value,this.name)">
						<c:forEach items="0,1,2,3,4,5" var="i">
							<c:choose>
								<c:when
									test="${fn:length(customerBean.customerAccountsBeans) eq i}">
									<form:option selected="true" value="${i}"></form:option>
								</c:when>
								<c:otherwise>
									<form:option value="${i}"></form:option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select></td>
				<td></td>
			</tr>
			<c:forEach items="${customerBean.customerAccountsBeans}" var="i"
				varStatus="itemsRow">
				<tr>
					<td colspan="3"><form:input
							path="customerAccountsBeans[${itemsRow.index}].accountId"
							cssStyle="visibility:hidden" value="${i.accountId}" /></td>
				</tr>
				<tr>
					<td><form:label
							path="customerAccountsBeans[${itemsRow.index}].accountNumber">Account Number</form:label></td>
					<td><form:input
							path="customerAccountsBeans[${itemsRow.index}].accountNumber"
							value="${i.accountNumber}" type="number"  /></td>
					<td>*<form:errors
							path="customerAccountsBeans[${itemsRow.index}].accountNumber"
							cssStyle="color: #ff0000;" /></td>
				</tr>
				<tr>
					<td><form:label
							path="customerAccountsBeans[${itemsRow.index}].accountHolderName">Account Holder name</form:label></td>
					<td><form:input
							path="customerAccountsBeans[${itemsRow.index}].accountHolderName"
							value="${i.accountHolderName}" /></td>
					<td>*<form:errors
							path="customerAccountsBeans[${itemsRow.index}].accountHolderName"
							cssStyle="color: #ff0000;" /></td>
				</tr>
				<tr>
					<td><form:label
							path="customerAccountsBeans[${itemsRow.index}].bankIfscCode">Bank IFSC Code</form:label></td>
					<td><form:input
							path="customerAccountsBeans[${itemsRow.index}].bankIfscCode"
							value="${i.bankIfscCode}" /></td>
					<td>*<form:errors
							path="customerAccountsBeans[${itemsRow.index}].bankIfscCode"
							cssStyle="color: #ff0000;" /></td>
				</tr>
				<tr>
					<td><form:label
							path="customerAccountsBeans[${itemsRow.index}].bankName">Bank Name</form:label></td>
					<td><form:input
							path="customerAccountsBeans[${itemsRow.index}].bankName"
							value="${i.bankName}" /></td>
					<td>*<form:errors
							path="customerAccountsBeans[${itemsRow.index}].bankName"
							cssStyle="color: #ff0000;" /></td>
				</tr>
				<tr>
					<td><form:label
							path="customerAccountsBeans[${itemsRow.index}].bankBranch">Bank Branch</form:label></td>
					<td><form:input
							path="customerAccountsBeans[${itemsRow.index}].bankBranch"
							value="${i.bankBranch}" /></td>
					<td>*<form:errors
							path="customerAccountsBeans[${itemsRow.index}].bankBranch"
							cssStyle="color: #ff0000;" /></td>
				</tr>
				<tr>
					<td><form:label
							path="customerAccountsBeans[${itemsRow.index}].bankAddress">Bank Address</form:label></td>
					<td><form:textarea
							path="customerAccountsBeans[${itemsRow.index}].bankAddress"
							value="${i.bankAddress}" /></td>
					<td><form:errors
							path="customerAccountsBeans[${itemsRow.index}].bankAddress"
							cssStyle="color: #ff0000;" /></td>
				</tr>
			</c:forEach>
		</table>
		<input type="submit" value="Submit" />&nbsp;<input type="button"
			name="cancel" value="Cancel" onclick="loadIndex('customer')" />
		<form:input path="customerId" cssStyle="visibility:hidden"
			value="${customerBean.customerId}" />
		<form:input path="companyAddressBean.addressId"
			cssStyle="visibility:hidden"
			value="${customerBean.companyAddressBean.addressId}" />
	</form:form>
</body>
</html>