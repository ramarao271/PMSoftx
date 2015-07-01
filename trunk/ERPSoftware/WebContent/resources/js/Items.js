function setProduct(type, id, name, code, quantity, variantId, priceId) {
	var productName = type + serial + ".productId.productName";
	var productCode = type + serial + ".variantCode";
	var productId = type + serial + ".productId.productId";
	var quantityf = type + serial + ".quantityType";
	var variantIdf = type + serial + ".variantId";
	var priceIdf = type + serial + ".rate"
	document.getElementById(productName).value = name;
	document.getElementById(productCode).value = code;
	document.getElementById(productId).value = id;
	document.getElementById(quantityf).value = quantity;
	document.getElementById(variantIdf).value = variantId;
	document.getElementById(priceIdf).value = priceId;
	$('.overlay-bg, .overlay-content').hide();
	calculateCost();
}
function setRawMaterial(type, id, name, code, quantity, variantId, priceId) {
	var productName = type + serial + ".rawMaterialBeanId.rawMaterialName";
	var productCode = type + serial + ".variantCode";
	var productId = type + serial + ".rawMaterialBeanId.rawMaterialId";
	var quantityf = type + serial + ".quantityType";
	var variantIdf = type + serial + ".variantId";
	var priceIdf = type + serial + ".rate"
	document.getElementById(productName).value = name;
	document.getElementById(productCode).value = code;
	document.getElementById(productId).value = id;
	document.getElementById(quantityf).value = quantity;
	document.getElementById(variantIdf).value = variantId;
	document.getElementById(priceIdf).value = priceId;
	$('.overlay-bg, .overlay-content').hide();
	calculateCost();
}
function listInvoices(val, name) {
	document.paymentForm.action = "/ERPSoftware/" + name + "Payment/list"
			+ name + "Invoices/" + val;
	document.paymentForm.submit();

}