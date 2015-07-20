package org.erp.tarak.productioninvoice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("productionInvoiceService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProductionInvoiceServiceImpl implements ProductionInvoiceService {

	@Autowired
	private ProductionInvoiceDao productionInvoiceDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addProductionInvoice(ProductionInvoice productionInvoice) {
		productionInvoiceDao.addProductionInvoice(productionInvoice);
	}
	
	public List<ProductionInvoice> listProductionInvoices(String finYear) {
		return productionInvoiceDao.listProductionInvoices(finYear);
	}

	public void deleteProductionInvoice(ProductionInvoice productionInvoice) {
		productionInvoiceDao.deleteProductionInvoice(productionInvoice);
	}

	@Override
	public List<ProductionInvoice> listPendingProductionInvoices(String finYear) {
		return productionInvoiceDao.listPendingProductionInvoices(finYear);
		}

	@Override
	public List<ProductionInvoice> listProcessedProductionInvoices(String finYear) {
		return productionInvoiceDao.listProcessedProductionInvoices(finYear);
	}

	@Override
	public List<ProductionInvoice> listProductionInvoicesByWorker(
			long workerId, String finYear) {
		return productionInvoiceDao.listProductionInvoicesByWorker(workerId,finYear);
	}

	@Override
	public ProductionInvoice getProductionInvoice(long productionInvoiceId,
			String finYear) {
		return productionInvoiceDao.getProductionInvoice(productionInvoiceId,finYear);	}

}
