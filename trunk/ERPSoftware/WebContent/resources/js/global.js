window.onload = function() {
	if (document.getElementById("myButton") != null) {
		document.getElementById("myButton").onclick = function() {
			return !window.open(this.href, "pop", "width=930,height=600");
		}
	}
	if (document.getElementById("myButton1") != null) {
		document.getElementById("myButton1").onclick = function() {
			return !window.open(this.href, "pop", "width=900,height=600");
		}
	}
}
function checkDelete() {
	if (confirm("Are you sure to delete?"))
		return true;
	else
		return false;
}
function checkUpdate() {
	if (confirm("Are you sure to Update?"))
		return true;
	else
		return false;
}
function updateLinks(x) {
	parent.load("/ERPSoftware/main/links/" + x);
}
function resizeIframe(iframe) {
	var addHeight = 20; // or whatever size is being cut off
	// iframe.height = iframe.contentWindow.document.body.scrollHeight +
	// addHeight
	// + "px";
}
function cleanup() {
	var prefix = "Accounts";
	var sub = "submit";

	for (var i = 0; i < 5; i++) {
		var idName = prefix + i;
		document.getElementById(idName).style.visibility = "hidden";
		idName = sub + i;
		document.getElementById(idName).style.visibility = "hidden";
	}

}
function setPersonFields(val, name) {
	document.personForm.action = "/ERPSoftware/" + name + "/contactperson"
			+ name + "/" + val;
	document.personForm.submit();
}
function setBankFields(val, name) {
	document.personForm.action = "/ERPSoftware/" + name + "/bankAccounts"
			+ name + "/" + val;
	document.personForm.submit();
}

function setItemsFields(val, name) {
	document.personForm.action = "/ERPSoftware/" + name + "/" + name
			+ "ItemModules/" + val;
	document.personForm.submit();
}

function setOrderDetails(val, name) {
	document.personForm.action = "/ERPSoftware/" + name + "/" + name
			+ "Details/" + val;
	document.personForm.submit();
}
function addVariant(name) {
	document.productForm.action = "/ERPSoftware/" + name + "/addVariant.html";
	document.productForm.submit();
}
function addRawMaterialVariant(name) {
	document.productForm.action = "/ERPSoftware/" + name + "/addRawMaterialVariant.html";
	document.productForm.submit();
}

function addExpense(name) {
	document.productForm.action = "/ERPSoftware/" + name + "/addExpense.html";
	document.productForm.submit();
}

function addStage(name) {
	document.productForm.action = "/ERPSoftware/" + name + "/addStage.html";
	document.productForm.submit();
}
function deleteVariant(vId) {
	document.productForm.action = "/ERPSoftware/product/deleteVariant/" + vId;
	document.productForm.submit();
}
function deleteRawVariant(vId) {
	document.productForm.action = "/ERPSoftware/rawMaterial/deleteRawVariant/"
			+ vId;
	document.productForm.submit();
}

function loadVariant(name, value) {
	if (value == '') {
		value = document.getElementById("variantBeans0.variantType").value;
	}
	document.productForm.action = "/ERPSoftware/" + name + "/loadVariant/"
			+ value;
	document.productForm.submit();
}
function loadRawMaterialVariant(name, value) {
	if (value == '') {
		value = document.getElementById("variantBeans0.variantType").value;
	}
	document.productForm.action = "/ERPSoftware/" + name + "/loadRawMaterialVariant/"
			+ value;
	document.productForm.submit();
}
function deleteItemsFields(val, name) {
	document.personForm.action = "/ERPSoftware/" + name + "/" + name
			+ "updateItems/" + val;
	document.personForm.submit();
}
function loadIndex(val) {
	document.personForm.action = "/ERPSoftware/" + val + "/" + val
			+ "/index.jsp";
	document.personForm.method = "GET";
	document.personForm.submit();
}
function doIframe() {
	var $iframes = $("iframe.autoHeight");
	$iframes.each(function() {
		var iframe = this;
		$(iframe).load(function() {
			setHeightAndWidth(iframe);
		});
	});
}

function setHeightAndWidth(e) {
	e.height = e.contentWindow.document.body.scrollHeight;
	e.width = e.contentWindow.document.body.scrollWidth;
}
$(document).ready(function() {
	$(".arrowRotate").click(function() {
		if ($(this).attr('src') == $(this).attr('data-src')) {
			var a = $(this).attr('data-swap');
			$(this).attr('src', a);
		} else {
			var b = $(this).attr('data-src');
			$(this).attr('src', b);
		}
	});
	doIframe();
});

function showHideStage() {
	document.getElementById("stag").visibility = "visible";
}
function changeStage(name) {
	document.personForm.action = "/ERPSoftware/" + val + "/change" + val
			+ "stage.jsp";
	document.personForm.method = "GET";
	document.personForm.submit();
}