package org.erp.tarak.productioninvoice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("productionInvoiceItemService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProductionInvoiceItemServiceImpl implements ProductionInvoiceItemService {

	@Autowired
	private ProductionInvoiceItemDao productionInvoiceItemDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addProductionInvoiceItem(ProductionInvoiceItem productionInvoiceItem) {
		productionInvoiceItemDao.addProductionInvoiceItem(productionInvoiceItem);
	}
	
	public List<ProductionInvoiceItem> listProductionInvoiceItems() {
		return productionInvoiceItemDao.listProductionInvoiceItems();
	}

	public ProductionInvoiceItem getProductionInvoiceItem(long productionInvoiceItemId) {
		return productionInvoiceItemDao.getProductionInvoiceItem(productionInvoiceItemId);
	}
	
	public void deleteProductionInvoiceItem(ProductionInvoiceItem productionInvoiceItem) {
		productionInvoiceItemDao.deleteProductionInvoiceItem(productionInvoiceItem);
	}

	@Override
	public void deleteProductionInvoiceItems(List<ProductionInvoiceItem> pois) {
		// TODO Auto-generated method stub
		productionInvoiceItemDao.deleteProductionInvoiceItems(pois);
	}

}
