package org.erp.tarak.productioninvoice;

import java.util.List;


public interface ProductionInvoiceDao {
	
	public void addProductionInvoice(ProductionInvoice productionInvoice);

	public List<ProductionInvoice> listProductionInvoices(String finYear);
	
	public ProductionInvoice getProductionInvoice(long productionInvoiceId, String finYear);
	
	public void deleteProductionInvoice(ProductionInvoice productionInvoice);

	public List<ProductionInvoice> listPendingProductionInvoices(String finYear);

	public List<ProductionInvoice> listProcessedProductionInvoices(String finYear);

	public List<ProductionInvoice> listProductionInvoicesByWorker(
			long workerId, String finYear);
}
