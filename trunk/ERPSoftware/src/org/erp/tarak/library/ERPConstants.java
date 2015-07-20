
package org.erp.tarak.library;

import java.util.LinkedHashMap;
import java.util.Map;

public class ERPConstants {
	public static final String OP_EDIT="edit";
	public static final String OP_DELETE="delete";
	public static final String OP_CREATE="create";
	public static final String CUSTOMER = "Customer";
	public static final String SUPPLIER = "Supplier";
	public static final String SHIPPER = "Shipper";
	public static final String SALES_ORDER="SalesOrder";
	public static final String PURCHASE_ORDER="PurchaseOrder";
	public static final String PRODUCTION_ORDER="ProductionOrder";
	public static final String SALES_INVOICE="SalesInvoice";
	public static final String SALES_RETURN="SalesReturn";
	public static final String PURCHASE_PAYMENT="PurchasePayment";
	public static final String SALES_PAYMENT="SalesPayment";
	public static final String PURCHASE_RETURN="PurchaseReturn";
	public static final String PURCHASE_INVOICE="PurchaseInvoice";
	public static final String DELIVERY_CHALLAN="DeliveryChallan";
	public static final String UNASSIGNED="Un-Assigned";
	public static final String PT_PURCHASED="Purchased";
	public static final String PT_OWN_PRODUCTION="Own Producion";
	public static final String PT_RAW_MATERIAL="Raw Material";
	public static final String PM_CASH="Cash";
	public static final String PM_CHEQUE="Cheque";
	public static final String PM_ONLINE_TRANSFER="Online Transfer";
	public static final String VT_COLOR="Color";
	public static final String VT_SIZE="Size";
	public static final String VT_MATERIAL="Material";
	public static final String PROCESSED="Processed";
	public static final String PENDING="Pending";
	public static final String CATEGORY="Category";
	public static final String VARIANT="Variant";
	
	public static Map<String, Object> linkMap = new LinkedHashMap<String, Object>();
	public static Map<String, String> customerMenuList = new LinkedHashMap<String, String>();
	public static Map<String, String> supplierMenuList = new LinkedHashMap<String, String>();
	public static Map<String, String> shipperMenuList = new LinkedHashMap<String, String>();
	public static Map<String, String> workerMenuList = new LinkedHashMap<String, String>();
	public static Map<String, String> productMenuList = new LinkedHashMap<String, String>();
	public static Map<String, String> purchaseOrderMenuList = new LinkedHashMap<String, String>();
	public static Map<String, String> salesOrderMenuList = new LinkedHashMap<String, String>();
	public static Map<String, String> productionOrderMenuList = new LinkedHashMap<String, String>();
	public static Map<String, String> productionInvoiceMenuList = new LinkedHashMap<String, String>();
	public static Map<String, String> deliveryChallanMenuList = new LinkedHashMap<String, String>();
	public static Map<String, String> purchaseInvoiceMenuList = new LinkedHashMap<String, String>();
	public static Map<String, String> salesInvoiceMenuList = new LinkedHashMap<String, String>();
	public static Map<String, String> salesReturnMenuList = new LinkedHashMap<String, String>();
	public static Map<String, String> purchaseReturnMenuList = new LinkedHashMap<String, String>();
	public static Map<String, String> salesPaymentMenuList = new LinkedHashMap<String, String>();
	public static Map<String, String> purchasePaymentMenuList = new LinkedHashMap<String, String>();
	public static Map<String, String> otherPaymentMenuList = new LinkedHashMap<String, String>();
	public static Map<String, String> balanceSheetMenuList = new LinkedHashMap<String, String>();
	public static Map<String, String> toolsMenuList = new LinkedHashMap<String, String>();
	public static Map<String, String> financeMenuList = new LinkedHashMap<String, String>();
	public static Map<String, String> marketingReportMenuList = new LinkedHashMap<String, String>();
	public static Map<String, String> productReportMenuList = new LinkedHashMap<String, String>();
	static {

		customerMenuList.put("/ERPSoftware/customer/addCustomer", "Add Customer");
		customerMenuList.put("/ERPSoftware/customer/listCustomer", "Customers List");
		
		supplierMenuList.put("/ERPSoftware/supplier/addSupplier", "Add Supplier");
		supplierMenuList.put("/ERPSoftware/supplier/listSupplier", "Suppliers List");
		
		shipperMenuList.put("/ERPSoftware/shipper/addShipper", "Add Shipper");
		shipperMenuList.put("/ERPSoftware/shipper/listShipper", "Shippers List");
		
		workerMenuList.put("/ERPSoftware/worker/addWorker", "Add worker");
		workerMenuList.put("/ERPSoftware/worker/listWorker", "Workers List");
		
		productMenuList.put("/ERPSoftware/product/addProduct.html", "Add Product");
		productMenuList.put("/ERPSoftware/product/products.html", "Products List");
		productMenuList.put("/ERPSoftware/product/productStage.html", "Product Life Cycle");
		productMenuList.put("/ERPSoftware/rawMaterial/addRawMaterial.html", "Raw Material");
		productMenuList.put("/ERPSoftware/rawMaterial/rawMaterials.html", "Raw Materials List");
		productMenuList.put("/ERPSoftware/rawMaterial/rawMaterial.html", "Raw Materials Stock");
		productMenuList.put("/ERPSoftware/measurement/measurement.html", "Measurements");
		productMenuList.put("/ERPSoftware/category/category.html", "Categories");
		productMenuList.put("/ERPSoftware/stageProperties/stageProperties.html", "Stages");
		productMenuList.put("/ERPSoftware/variantProperties/variantProperties.html", "Variants");
		productMenuList.put("/ERPSoftware/subVariantProperties/subVariantProperties.html", "Sub-Variants Categories");
		productMenuList.put("/ERPSoftware/product/productSubVariant.html", "Sub-Variant Creation");
		
		purchaseOrderMenuList.put("/ERPSoftware/purchaseorder/addPurchaseOrder.html", "New Purchase Order");
		purchaseOrderMenuList.put("/ERPSoftware/purchaseorder/pendingPurchaseorders.html", "Pending PO List");
		purchaseOrderMenuList.put("/ERPSoftware/purchaseorder/processedPurchaseorders.html", "Processed PO List");
		
		salesOrderMenuList.put("/ERPSoftware/salesorder/addSalesOrder.html", "New Sales Order");
		salesOrderMenuList.put("/ERPSoftware/salesorder/pendingSalesorders.html", "Pending SO List");
		salesOrderMenuList.put("/ERPSoftware/salesorder/processedSalesorders.html", "Processed SO List");
		
		productionOrderMenuList.put("/ERPSoftware/productionorder/addProductionOrder.html", "New Production Order");
		productionOrderMenuList.put("/ERPSoftware/productionorder/pendingProductionorders.html", "Pending PRO List");
		productionOrderMenuList.put("/ERPSoftware/productionorder/processedProductionorders.html", "Processed PRO List");
		
		productionInvoiceMenuList.put("/ERPSoftware/productioninvoice/addProductionInvoice.html", "New Production Invoice");
		productionInvoiceMenuList.put("/ERPSoftware/productioninvoice/productioninvoices.html", "Production Invoice List");
		
		deliveryChallanMenuList.put("/ERPSoftware/deliverychallan/addDeliveryChallan.html", "New Delivery Challan");
		deliveryChallanMenuList.put("/ERPSoftware/deliverychallan/pendingDeliverychallans.html", "Pending DC List");
		deliveryChallanMenuList.put("/ERPSoftware/deliverychallan/processedDeliverychallans.html", "Processed DC List");
		
		purchaseInvoiceMenuList.put("/ERPSoftware/purchaseinvoice/addPurchaseInvoice.html", "New Purchase Invoice");
		purchaseInvoiceMenuList.put("/ERPSoftware/purchaseinvoice/purchaseinvoices.html", "Purchase Invoice List");
		
		salesInvoiceMenuList.put("/ERPSoftware/salesinvoice/addSalesInvoice.html", "New Sales Invoice");
		salesInvoiceMenuList.put("/ERPSoftware/salesinvoice/salesinvoices.html", "Sales Invoice List");
		
		salesReturnMenuList.put("/ERPSoftware/salesreturn/addSalesReturn.html", "New Sales Return");
		salesReturnMenuList.put("/ERPSoftware/salesreturn/salesreturns.html", "Sales Returns List");
		
		purchaseReturnMenuList.put("/ERPSoftware/purchasereturn/addPurchaseReturn.html", "New Purchase Return");
		purchaseReturnMenuList.put("/ERPSoftware/purchasereturn/purchasereturns.html", "Purchase Returns List");
		
		otherPaymentMenuList.put("/ERPSoftware/otherPayment/addOtherPayment", "New Payment");
		otherPaymentMenuList.put("/ERPSoftware/otherPayment/otherPayments", "Payments List");
		
		salesPaymentMenuList.put("/ERPSoftware/salesPayment/addSalesPayment", "New Sales Payment");
		salesPaymentMenuList.put("/ERPSoftware/salesPayment/salesPayments", "Sales Payments List");
		
		purchasePaymentMenuList.put("/ERPSoftware/purchasePayment/addPurchasePayment", "New Purchase Payment");
		purchasePaymentMenuList.put("/ERPSoftware/purchasePayment/purchasePayments", "Purchase Payments List");
		
		balanceSheetMenuList.put("/ERPSoftware/expense/expense.html", "Expenses");
		balanceSheetMenuList.put("/ERPSoftware/balanceSheet/addBalanceSheet.html", "Balance Sheet");
		balanceSheetMenuList.put("/ERPSoftware/balanceSheet/pendingBalanceSheets.html", "Pending BS List");
		balanceSheetMenuList.put("/ERPSoftware/balanceSheet/processedBalanceSheets.html", "Processed BS List");
		
		toolsMenuList.put("/ERPSoftware/company/addCompany","Companies");

		financeMenuList.put("/ERPSoftware/financialReports/accountsReceivable","Accounts Receivables");
		financeMenuList.put("/ERPSoftware/financialReports/accountsPayable","Accounts Payable");
		financeMenuList.put("/ERPSoftware/financialReports/ordersToShip","Orders to be Shipped");
		financeMenuList.put("/ERPSoftware/financialReports/ordersToInvoice","Orders to be Invoiced");
		financeMenuList.put("/ERPSoftware/financialReports/salesReport","Sales Report");
		financeMenuList.put("/ERPSoftware/financialReports/profitReport","Profit Report");
		financeMenuList.put("/ERPSoftware/financialReports/lostSalesReport","Lost Sales Report");
		
		
		productReportMenuList.put("/ERPSoftware/productReports/categoryWiseReport", "Products by Category(Sales)");
		productReportMenuList.put("/ERPSoftware/productReports/categoryWisePurchaseReport", "Products by Category(Purchase)");
		productReportMenuList.put("/ERPSoftware/productReports/variantWiseReport", "Products by Variant(Sales)");
		productReportMenuList.put("/ERPSoftware/productReports/variantWisePurchaseReport", "Products by Variant(Purchase)");
		productReportMenuList.put("/ERPSoftware/productReports/frequentProduct","Frequently Purchased Product");
		
		marketingReportMenuList.put("/ERPSoftware/marketingReports/customerSalesReport","Customer Sales Report");
		marketingReportMenuList.put("/ERPSoftware/marketingReports/customerProfitReport","Customer Profit Report");
		marketingReportMenuList.put("/ERPSoftware/marketingReports/avgTktPrice","Avg Tkt Price");
		marketingReportMenuList.put("/ERPSoftware/marketingReports/purchaseFreqCustomer","Customer Purchase Frequency");
		marketingReportMenuList.put("/ERPSoftware/marketingReports/frequentProductByCustomer","Products Customer Wise");
		
		linkMap.put("customer", customerMenuList);
		linkMap.put("worker", workerMenuList);
		linkMap.put("supplier", supplierMenuList);
		linkMap.put("shipper", shipperMenuList);
		linkMap.put("product", productMenuList);
		linkMap.put("purchaseorder", purchaseOrderMenuList);
		linkMap.put("salesorder", salesOrderMenuList);
		linkMap.put("productionorder", productionOrderMenuList);
		linkMap.put("productioninvoice", productionInvoiceMenuList);
		linkMap.put("deliverychallan", deliveryChallanMenuList);
		linkMap.put("purchaseinvoice", purchaseInvoiceMenuList);
		linkMap.put("salesinvoice", salesInvoiceMenuList);
		linkMap.put("salesreturn", salesReturnMenuList);
		linkMap.put("purchasereturn", purchaseReturnMenuList);
		linkMap.put("salesPayment", salesPaymentMenuList);
		linkMap.put("purchasePayment", purchasePaymentMenuList);
		linkMap.put("otherPayment", otherPaymentMenuList);
		linkMap.put("balanceSheet",balanceSheetMenuList);
		linkMap.put("tools",toolsMenuList);
		linkMap.put("financialReports",financeMenuList);
		linkMap.put("productReports", productReportMenuList);
		linkMap.put("marketingReports",marketingReportMenuList);
	}
}

