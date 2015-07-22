<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ERP Software</title>
<c:url value="/resources/img/b_delete.png" var="deleteImg" />
<c:url value="/resources/img/b_edit.png" var="editImg" />
<c:url value="/resources/img/b_add.png" var="addImg" />
<c:url value="/resources/css/global.css" var="stylecss" />
<link href="${stylecss}" rel="stylesheet" />
<c:url value="/resources/js/global.js" var="globaljs" />
<c:url value="/resources/autocomplete/demos.css" var="democss" />
<link rel="stylesheet" href="${democss}">
<script type="text/javascript" src="${globaljs}"></script>
<script type="text/javascript">
function uploadImage()
{
	document.productForm.action="/ERPSoftware/product/addImage.html";
	document.productForm.submit();	
}
function loadIndex(val)
{
	document.productForm.action = "/ERPSoftware/" +val+"/"+val+"/index.jsp";
	document.productForm.method="GET";
	document.productForm.submit();
}

	function load() {
		var msg = '<c:if test="${!empty message}"><c:out value="${message}" /></c:if>';
		if (msg != "")
			alert(msg);
	}
	function populatePrices(value)
	{
		document.getElementById("price1").value=(parseFloat(value) || "0.0");
		document.getElementById("price2").value=(parseFloat(value) || "0.0");
		document.getElementById("price3").value=(parseFloat(value) || "0.0");
	}
	function setVariantTypes()
	{
		if(document.getElementById("variantBeans0.variantType")!=undefined && document.getElementById("variantBeans0.variantType")!=null)
		{
			var val=document.getElementById("variantBeans0.variantType").value;
			for(var i=1;;i++)
			{
				var id="variantBeans"+i+".variantType";
				if(document.getElementById(id)!=undefined && document.getElementById(id)!=null)
				{
					document.getElementById(id).value=val;
				}
				else
				{
					break;	
				}
			}
		}
		return true;
	}	
</script>

</head>
<body onload="load()">
	<h3>
		<c:if test="${!empty operation}">
			<c:out value="${operation}" />
		</c:if>
		Product Details
	</h3>
	<form:form method="POST" action="/ERPSoftware/product/saveProduct.html"
		modelAttribute="productBean" name="productForm" id="productForm"
		enctype="multipart/form-data">
		<form:input path="productId" cssStyle="visibility:hidden"
			value="${productBean.productId}" />
		<table cellpadding=0 cellspacing=10>
			<tr>
				<td>Product Name</td>
				<td><form:input path="productName"
						value="${productBean.productName}" /></td>
				<td><form:errors path="productName" /></td>
			</tr>
			<tr>
				<td>Product Category</td>
				<td><form:select path="categoryBean.categoryId" class="branch">
						<c:forEach items="${categories}" var="category">
							<c:choose>
								<c:when
									test="${productBean.categoryBean.categoryId eq category.categoryId}">
									<form:option selected="true" value="${category.categoryId}">${category.categoryName}</form:option>
								</c:when>
								<c:otherwise>
									<form:option value="${category.categoryId}">${category.categoryName}</form:option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select></td>
				<td><a id="myButton1" href="/ERPSoftware/category/category.html" target="_blank">New
						Category</a> <form:errors path="categoryBean" /></td>
			</tr>
			<tr>
				<td>Product Quantity</td>
				<td><form:input path="quantity" value="${productBean.quantity}"
						type="number" step="0.01" /></td>
				<td><form:errors path="quantity" /></td>
			</tr>
			<tr>
				<td>Measurement</td>
				<td><form:select path="measurementBean.measurementId"
						class="branch">
						<c:forEach items="${measurements}" var="measurement">
							<c:choose>
								<c:when
									test="${productBean.measurementBean.measurementId eq measurement.measurementId}">
									<form:option selected="true"
										value="${measurement.measurementId}">${measurement.measurementName}</form:option>
								</c:when>
								<c:otherwise>
									<form:option value="${measurement.measurementId}">${measurement.measurementName}</form:option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select></td>
				<td><a id="myButton1"
					href="/ERPSoftware/measurement/measurement.html" target="_blank">New
						Measurement</a> <form:errors path="measurementBean" /></td>
			</tr>
			<tr>
				<td>Product Cost</td>
				<td><form:input path="cost" value="${productBean.cost}"
						type="number" step="0.01" /></td>
				<td><form:errors path="cost" /></td>
			</tr>
			<tr>
				<td>Product Price</td>
				<td><form:input path="price" value="${productBean.price}"
						type="number" step="0.01" onkeyup="populatePrices(this.value)" /></td>
				<td><form:errors path="price" /></td>
			</tr>
			<tr>
				<td>Product Price1</td>
				<td><form:input path="price1" value="${productBean.price1}"
						type="number" step="0.01" /></td>
				<td><form:errors path="price1" /></td>
			</tr>
			<tr>
				<td>Product Price2</td>
				<td><form:input path="price2" value="${productBean.price2}"
						type="number" step="0.01" /></td>
				<td><form:errors path="price2" /></td>
			</tr>
			<tr>
				<td>Defective Item Price</td>
				<td><form:input path="price3" value="${productBean.price3}"
						type="number" step="0.01" /></td>
				<td><form:errors path="price3" /></td>
			</tr>
			<tr>
				<td>Production Type</td>
				<td><form:select path="productionType">
						<c:forEach items="${pTypes}" var="vTypes">
							<c:choose>
								<c:when test="${productBean.productionType eq vTypes}">
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
				<td>Stages Supported</td>
				<td><c:forEach items="${stages}" var="i" varStatus="itemsRow">
						<%-- <c:choose>
							<c:when test="${i.stageName eq sProperties.stageName}"> --%>
						<form:checkbox path="stageBeans[${itemsRow.index}].stageName"
							value="${i.stagePropertiesName }" />
						<%-- <form:hidden path="stageBeans[${itemsRow.index}].stageId" value="${i.stagePropertiesId}" /> --%>	
						<c:out value="${i.stagePropertiesName }" />
						<%-- </c:when>
							<c:otherwise>
								<form:checkbox path="stageBeans[${itemsRow.index}].stageName"
									value="${i.stageName }" />
							</c:otherwise>
						</c:choose> --%>
					</c:forEach></td>
			</tr>
			<tr>
				<td>Variant</td>
				<td><form:select path="variantType" class="branch"
						onchange="loadVariant('product',this.value)">
						<c:forEach items="${variantTypes}" var="vTypes">
							<c:choose>
								<c:when test="${productBean.variantType eq vTypes}">
									<form:option selected="true" value="${vTypes}" />
								</c:when>
								<c:otherwise>
									<form:option value="${vTypes}" />
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select></td>
				<td><form:errors path="price3" /></td>
			</tr>
		</table>
		<table border=1 cellpadding="0" cellspacing="0">
			<tr>
				<th><a href="#" onclick="addVariant('product')"><img
						src="${addImg}" /></a></th>
				<th>Variant Name</th>
				<th>Quantity</th>
				<th>Cost</th>
			</tr>
			<c:forEach items="${productBean.variantBeans}" var="i"
				varStatus="itemsRow">
				<tr>
					<td><form:hidden
							path="variantBeans[${itemsRow.index}].variantType"
							value="${i.variantType}" /> <form:hidden
							path="variantBeans[${itemsRow.index}].variantId"
							value="${i.variantId}" /> <form:hidden
							path="variantBeans[${itemsRow.index}].productCode"
							value="${i.productCode}" /> <a href="#"
						onclick="deleteVariant(${i.variantId})"><c:if
								test="${!(i.variantId eq 0)}">
								<img alt="" src="${deleteImg}">
							</c:if></a></td>
					<td><form:select
							path="variantBeans[${itemsRow.index}].variantName" class="branch">
							<c:forEach items="${vProperties}" var="vProperties">
								<c:choose>
									<c:when
										test="${i.variantName eq vProperties.variantPropertiesName}">
										<form:option selected="true"
											value="${vProperties.variantPropertiesName }" />
									</c:when>
									<c:otherwise>
										<form:option value="${vProperties.variantPropertiesName }" />
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select></td>
					<td><form:input
							path="variantBeans[${itemsRow.index}].quantity"
							value="${i.quantity}" /></td>
					<td><form:input
							path="variantBeans[${itemsRow.index}].cost"
							value="${i.cost}" /></td>
				</tr>
			</c:forEach>
		</table>
		<table cellpadding=0 cellspacing=5>
			<tr>
				<td>Upload Image</td>
				<td><form:input type="file" path="image"
						value="${productBean.image}" /> <!-- <input
					type="button" value="Upload" onclick="uploadImage()"> --></td>
				<td><img alt="" src="${productBean.imagePath }" height="50px"
					width="50px"> <form:hidden path="imagePath"
						value="${productBean.imagePath}" /></td>
			</tr>
		</table>
		<form:hidden path="productCode" value="${productBean.productCode}" />
		<c:if test="${productBean.productId gt 0 }">
			<form:checkbox path="discontinued"></form:checkbox> Discontinued</c:if>
		<input type="submit" value="Submit" onclick="return setVariantTypes()" />&nbsp;<input
			type="button" name="cancel" value="Cancel"
			onclick="loadIndex('product')" />

	</form:form>
</body>
</html>