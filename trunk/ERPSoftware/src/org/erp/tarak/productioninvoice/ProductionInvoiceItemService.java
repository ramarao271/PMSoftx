package org.erp.tarak.productioninvoice;

import java.util.List;


public interface ProductionInvoiceItemService {
	public void addProductionInvoiceItem(ProductionInvoiceItem productionInvoiceItem);

	public List<ProductionInvoiceItem> listProductionInvoiceItems();

	public ProductionInvoiceItem getProductionInvoiceItem(long productionInvoiceItemId);

	public void deleteProductionInvoiceItem(ProductionInvoiceItem productionInvoiceItem);

	public void deleteProductionInvoiceItems(List<ProductionInvoiceItem> pois);

}
