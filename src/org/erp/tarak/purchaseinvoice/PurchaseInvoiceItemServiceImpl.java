package org.erp.tarak.purchaseinvoice;

import java.util.List;

import org.erp.tarak.category.CategoryReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("purchaseInvoiceItemService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PurchaseInvoiceItemServiceImpl implements PurchaseInvoiceItemService {

	@Autowired
	private PurchaseInvoiceItemDao purchaseInvoiceItemDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPurchaseInvoiceItem(PurchaseInvoiceItem purchaseInvoiceItem) {
		purchaseInvoiceItemDao.addPurchaseInvoiceItem(purchaseInvoiceItem);
	}
	
	public List<PurchaseInvoiceItem> listPurchaseInvoiceItems(String finYear) {
		return purchaseInvoiceItemDao.listPurchaseInvoiceItems(finYear);
	}

	public PurchaseInvoiceItem getPurchaseInvoiceItem(long purchaseInvoiceItemId,String finYear) {
		return purchaseInvoiceItemDao.getPurchaseInvoiceItem(purchaseInvoiceItemId,finYear);
	}
	
	public void deletePurchaseInvoiceItem(PurchaseInvoiceItem purchaseInvoiceItem) {
		purchaseInvoiceItemDao.deletePurchaseInvoiceItem(purchaseInvoiceItem);
	}

	@Override
	public void deletePurchaseInvoiceItems(List<PurchaseInvoiceItem> pois) {
		// TODO Auto-generated method stub
		purchaseInvoiceItemDao.deletePurchaseInvoiceItems(pois);
	}

	@Override
	public List<CategoryReport> getPurchaseReportByCategory(String finYear) {
		return purchaseInvoiceItemDao.getPurchaseReportByCategory(finYear);
	}

	@Override
	public List<Object[]> listPurchaseInvoiceItemsByCategory(long id,
			String finYear) {
		return purchaseInvoiceItemDao.listPurchaseInvoiceItemsByCategory(id,finYear);
	}

	@Override
	public List<Object[]> listFrequesntlyProductsBySupplier(long supplierId,
			String finYear) {
		return purchaseInvoiceItemDao.listFrequesntlyProductsBySupplier(supplierId,finYear);
	}


}
