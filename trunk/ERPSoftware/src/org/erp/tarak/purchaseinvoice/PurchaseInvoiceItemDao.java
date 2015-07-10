package org.erp.tarak.purchaseinvoice;

import java.util.List;

import org.erp.tarak.category.CategoryReport;
import org.erp.tarak.variant.VariantReport;


public interface PurchaseInvoiceItemDao {
	
	public void addPurchaseInvoiceItem(PurchaseInvoiceItem purchaseInvoiceItem);

	public List<PurchaseInvoiceItem> listPurchaseInvoiceItems(String finYear);
	
	public PurchaseInvoiceItem getPurchaseInvoiceItem(long purchaseInvoiceItemId,String finYear);
	
	public void deletePurchaseInvoiceItem(PurchaseInvoiceItem purchaseInvoiceItem);

	public void deletePurchaseInvoiceItems(List<PurchaseInvoiceItem> pois);

	public List<CategoryReport> getPurchaseReportByCategory(String finYear);

	public List<Object[]> listPurchaseInvoiceItemsByCategory(long id,
			String finYear);

	public List<Object[]> listFrequesntlyProductsBySupplier(long supplierId, String finYear);

	public List<VariantReport> getPurchaseReportByVariant(String finYear);
}
