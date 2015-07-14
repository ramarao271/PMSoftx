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
	document.rawMaterialForm.action="/ERPSoftware/rawMaterial/addImage.html";
	document.rawMaterialForm.submit();	
}
function loadIndex(val)
{
	document.rawMaterialForm.action = "/ERPSoftware/" +val+"/"+val+"/index.jsp";
	document.rawMaterialForm.method="GET";
	document.rawMaterialForm.submit();
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
		RawMaterial Details
	</h3>
	<form:form method="POST"
		action="/ERPSoftware/rawMaterial/saveRawMaterial.html"
		modelAttribute="rawMaterialBean" name="rawMaterialForm"
		id="rawMaterialForm" enctype="multipart/form-data">
		<form:input path="rawMaterialId" cssStyle="visibility:hidden"
			value="${rawMaterialBean.rawMaterialId}" />
		<table cellpadding=0 cellspacing=5>
			<tr>
				<td>RawMaterial Name</td>
				<td><form:input path="rawMaterialName"
						value="${rawMaterialBean.rawMaterialName}" /></td>
				<td><form:errors path="rawMaterialName" /></td>
			</tr>
			<tr>
				<td>RawMaterial Category</td>
				<td><form:select path="categoryBean.categoryId" class="branch">
						<c:forEach items="${categories}" var="category">
							<c:choose>
								<c:when
									test="${rawMaterialBean.categoryBean.categoryId eq category.categoryId}">
									<form:option selected="true" value="${category.categoryId}">${category.categoryName}</form:option>
								</c:when>
								<c:otherwise>
									<form:option value="${category.categoryId}">${category.categoryName}</form:option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select></td>
				<td><a id="myButton1"
					href="/ERPSoftware/category/category.html" target="_blank">New
						Category</a> <form:errors path="categoryBean" /></td>
			</tr>
			<tr>
				<td>RawMaterial Quantity</td>
				<td><form:input path="quantity"
						value="${rawMaterialBean.quantity}" type="number" step="0.01" /></td>
				<td><form:errors path="quantity" /></td>
			</tr>
			<tr>
				<td>Measurement</td>
				<td><form:select path="measurementBean.measurementId"
						class="branch">
						<c:forEach items="${measurements}" var="measurement">
							<c:choose>
								<c:when
									test="${rawMaterialBean.measurementBean.measurementId eq measurement.measurementId}">
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
				<td>RawMaterial Cost</td>
				<td><form:input path="cost" value="${rawMaterialBean.cost}"
						type="number" step="0.01" /></td>
				<td><form:errors path="cost" /></td>
			</tr>
			<tr>
				<td>RawMaterial Price</td>
				<td><form:input path="price" value="${rawMaterialBean.price}"
						type="number" step="0.01" onkeyup="populatePrices(this.value)" /></td>
				<td><form:errors path="price" /></td>
			</tr>
			<%-- <tr>
				<td>RawMaterial Price1</td>
				<td><form:input path="price1" value="${rawMaterialBean.price1}"
						type="number" step="0.01" /></td>
				<td><form:errors path="price1" /></td>
			</tr>
			<tr>
				<td>RawMaterial Price2</td>
				<td><form:input path="price2" value="${rawMaterialBean.price2}"
						type="number" step="0.01" /></td>
				<td><form:errors path="price2" /></td>
			</tr>
			<tr>
				<td>Defective Item Price</td>
				<td><form:input path="price3" value="${rawMaterialBean.price3}"
						type="number" step="0.01" /></td>
				<td><form:errors path="price3" /></td>
			</tr>
			 --%>
			<tr>
				<td>RawMaterialion Type</td>
				<td><form:select path="rawMaterialionType">
						<c:forEach items="${pTypes}" var="vTypes">
							<c:choose>
								<c:when test="${rawMaterialBean.rawMaterialionType eq vTypes}">
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

						<form:checkbox path="stageBeans[${itemsRow.index}].stageName"
							value="${i.stagePropertiesName }" />
						<c:out value="${i.stagePropertiesName }" />
					</c:forEach></td>
			</tr>
			<tr>
				<td>Variant</td>
				<td><form:select path="variantType" class="branch"
						onchange="loadRawMaterialVariant('rawMaterial',this.value)">
						<c:forEach items="${variantTypes}" var="vTypes">
							<c:choose>
								<c:when test="${rawMaterialBean.variantType eq vTypes}">
									<form:option selected="true" value="${vTypes}" />
								</c:when>
								<c:otherwise>
									<form:option value="${vTypes}" />
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select></td>
				<td>
					<%-- <form:errors path="price3" /> --%>
				</td>
			</tr>
		</table>
		<table border=1 cellpadding="0" cellspacing="2">
			<tr>
				<th><a href="#" onclick="addRawMaterialVariant('rawMaterial')"><img
						src="${addImg}" /></a></th>
				<th>Variant Name</th>
				<th>Quantity</th>
				<th>Cost</th>
			</tr>
			<c:forEach items="${rawMaterialBean.variantBeans}" var="i"
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
					<td><form:input path="variantBeans[${itemsRow.index}].cost"
							value="${i.cost}" /></td>
				</tr>
			</c:forEach>
		</table>
		<table cellpadding=0 cellspacing=5>
			<tr>
				<td>Upload Image</td>
				<td><form:input type="file" path="image"
						value="${rawMaterialBean.image}" /> <!-- <input
					type="button" value="Upload" onclick="uploadImage()"> --></td>
				<td><img alt="" src="${rawMaterialBean.imagePath }"
					height="50px" width="50px"> <form:hidden path="imagePath"
						value="${rawMaterialBean.imagePath}" /></td>
			</tr>
		</table>
		<form:hidden path="rawMaterialCode"
			value="${rawMaterialBean.rawMaterialCode}" />
		<c:if test="${rawMaterialBean.rawMaterialId gt 0 }">
			<form:checkbox path="discontinued"></form:checkbox> Discontinued</c:if>
		<input type="submit" value="Submit" onclick="return setVariantTypes()" />&nbsp;<input
			type="button" name="cancel" value="Cancel"
			onclick="loadIndex('rawMaterial')" />

	</form:form>
</body>
</html>