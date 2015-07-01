package org.erp.tarak.purchaseinvoice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("purchaseInvoiceService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PurchaseInvoiceServiceImpl implements PurchaseInvoiceService {

	@Autowired
	private PurchaseInvoiceDao purchaseInvoiceDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPurchaseInvoice(PurchaseInvoice purchaseInvoice) {
		purchaseInvoiceDao.addPurchaseInvoice(purchaseInvoice);
	}
	
	public List<PurchaseInvoice> listPurchaseInvoices(String finYear) {
		return purchaseInvoiceDao.listPurchaseInvoices(finYear);
	}

	public PurchaseInvoice getPurchaseInvoice(long purchaseInvoiceId,String finYear) {
		return purchaseInvoiceDao.getPurchaseInvoice(purchaseInvoiceId,finYear);
	}
	
	public void deletePurchaseInvoice(PurchaseInvoice purchaseInvoice) {
		purchaseInvoiceDao.deletePurchaseInvoice(purchaseInvoice);
	}

	@Override
	public List<PurchaseInvoice> listPurchaseInvoicesBySupplier(long supplierId,String finYear) {
		return  purchaseInvoiceDao.listPurchaseInvoicesBySupplier(supplierId,finYear);
	}

	@Override
	public void updatePurchaseInvoiceBalance(PurchaseInvoice purchaseInvoice) {
		purchaseInvoiceDao.updatePurchaseInvoiceBalance(purchaseInvoice);
		
	}

	@Override
	public List<PurchaseInvoice> listPendingPurchaseInvoices(String finYear) {
		return purchaseInvoiceDao.listPendingPurchaseInvoices(finYear);
	}

	@Override
	public List<PurchaseInvoice> listPurchaseInvoicesBySupplier(String finYear) {
		return purchaseInvoiceDao.listPurchaseInvoicesBySupplier(finYear);
	}

	@Override
	public List<Long> listBilledSuppliers(String finYear) {
		return purchaseInvoiceDao.listBilledSuppliers(finYear);
	}

	@Override
	public List<Object[]> listPendingPurchaseInvoicesBySupplier(String finYear) {
		return purchaseInvoiceDao.listPendingPurchaseInvoicesBySupplier(finYear);
	}

}
