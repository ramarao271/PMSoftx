package org.erp.tarak.salesinvoice;

import java.util.List;

import org.erp.tarak.category.CategoryReport;
import org.erp.tarak.variant.VariantReport;


public interface SalesInvoiceItemDao {
	
	public void addSalesInvoiceItem(SalesInvoiceItem salesInvoiceItem);

	public List<SalesInvoiceItem> listSalesInvoiceItems(String finYear);
	
	public SalesInvoiceItem getSalesInvoiceItem(long salesInvoiceItemId,String finYear);
	
	public void deleteSalesInvoiceItem(SalesInvoiceItem salesInvoiceItem);

	public void deleteSalesInvoiceItems(List<SalesInvoiceItem> pois);

	public List<CategoryReport> getSalesReportByCategory(String finYear);

	public List<Object[]> listSalesInvoiceItemsByCategory(long id,
			String finYear);

	public List<Object[]> listFrequesntlyProductsByCustomer(long customerId, String finYear);

	public List<VariantReport> listFrequesntlyProductsByVariant(String finYear);
}
