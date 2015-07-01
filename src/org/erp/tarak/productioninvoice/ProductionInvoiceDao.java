package org.erp.tarak.productioninvoice;

import java.util.List;


public interface ProductionInvoiceDao {
	
	public void addProductionInvoice(ProductionInvoice productionInvoice);

	public List<ProductionInvoice> listProductionInvoices();
	
	public ProductionInvoice getProductionInvoice(long productionInvoiceId);
	
	public void deleteProductionInvoice(ProductionInvoice productionInvoice);

	public List<ProductionInvoice> listPendingProductionInvoices();

	public List<ProductionInvoice> listProcessedProductionInvoices();
}
