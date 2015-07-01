<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<table border=1 cellpadding=0 cellspacing=0>
						<tr>
							<td><form:label
									path="purchaseOrderItemBeans[0].productId.productName">Product Name</form:label></td>
							<td><form:label
									path="purchaseOrderItemBeans[0].productId.productCode">Code</form:label></td>
							<td><form:label path="purchaseOrderItemBeans[0].description">Description</form:label></td>
							<td><form:label path="purchaseOrderItemBeans[0].quantity">Quantity</form:label></td>
							<td><form:label
									path="purchaseOrderItemBeans[0].quantityType">Qty Type</form:label></td>
							<td><form:label path="purchaseOrderItemBeans[0].rate">Unit Rate</form:label></td>
							<td><form:label path="purchaseOrderItemBeans[0].totalCost">Total Cost</form:label></td>
						</tr>

						<c:forEach items="${purchaseOrderBean.purchaseOrderItemBeans}"
							var="i" varStatus="itemsRow">
							<tr>
								<td><form:hidden
										path="purchaseOrderItemBeans[${itemsRow.index}].productId.productId" />
									<form:input
										path="purchaseOrderItemBeans[${itemsRow.index}].productId.productName"
										value="${i.productId.productName}" class="show-popup" href="#"
										data-showpopup="1" onclick="setSerialNo(${itemsRow.index})"
										readonly="true" /> <form:errors
										path="purchaseOrderItemBeans[${itemsRow.index}].productId.productName"
										cssStyle="color: #ff0000;" /></td>
								<td><form:input
										path="purchaseOrderItemBeans[${itemsRow.index}].productId.productCode"
										value="${i.productId.productCode}" class="product_Code"
										readonly="true" /> <form:errors
										path="purchaseOrderItemBeans[${itemsRow.index}].productId.productCode"
										cssStyle="color: #ff0000;" /></td>
								<td><form:input
										path="purchaseOrderItemBeans[${itemsRow.index}].description"
										value="${i.description}" class="product_Description" /> <form:errors
										path="purchaseOrderItemBeans[${itemsRow.index}].description"
										cssStyle="color: #ff0000;" /></td>
								<td><form:input
										path="purchaseOrderItemBeans[${itemsRow.index}].quantity"
										value="${i.quantity}" class="product_quantity" type="number" step="0.01" 
										onkeyup="calculateCost()" onchange="calculateCost()" /> <form:errors
										path="purchaseOrderItemBeans[${itemsRow.index}].quantity"
										cssStyle="color: #ff0000;" /></td>
								<td><form:input
										path="purchaseOrderItemBeans[${itemsRow.index}].quantityType"
										value="${i.quantityType}" class="product_quantityType" /> <form:errors
										path="purchaseOrderItemBeans[${itemsRow.index}].quantityType"
										cssStyle="color: #ff0000;" />
								<td><form:input
										path="purchaseOrderItemBeans[${itemsRow.index}].rate"
										value="${i.rate}" class="product_Unit_Rate"  type="number" step="0.01" 
										onkeyup="calculateCost()" onchange="calculateCost()" /> <form:errors
										path="purchaseOrderItemBeans[${itemsRow.index}].rate"
										cssStyle="color: #ff0000;" />
								<td><form:input
										path="purchaseOrderItemBeans[${itemsRow.index}].totalCost"
										value="${i.totalCost}" class="product_Total_Rate"
										 type="number" step="0.01"  readonly="true" /> <form:errors
										path="purchaseOrderItemBeans[${itemsRow.index}].totalCost"
										cssStyle="color: #ff0000;" /></td>
								<form:hidden
									path="purchaseOrderItemBeans[${itemsRow.index}].srNo"
									value="${i.srNo}" class="product_Srno" />
							</tr>
						</c:forEach>
					</table>
