package org.erp.tarak.purchasePayment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("purchasePaymentItemService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PurchasePaymentItemServiceImpl implements PurchasePaymentItemService {

	@Autowired
	private PurchasePaymentItemDao purchasePaymentItemDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPurchasePaymentItem(PurchasePaymentItem purchasePaymentItem) {
		purchasePaymentItemDao.addPurchasePaymentItem(purchasePaymentItem);
	}
	
	public List<PurchasePaymentItem> listPurchasePaymentItems() {
		return purchasePaymentItemDao.listPurchasePaymentItems();
	}

	public PurchasePaymentItem getPurchasePaymentItem(long purchasePaymentItemId) {
		return purchasePaymentItemDao.getPurchasePaymentItem(purchasePaymentItemId);
	}
	
	public void deletePurchasePaymentItem(PurchasePaymentItem purchasePaymentItem) {
		purchasePaymentItemDao.deletePurchasePaymentItem(purchasePaymentItem);
	}

	@Override
	public void deletePurchasePaymentItems(List<PurchasePaymentItem> pois) {
		// TODO Auto-generated method stub
		purchasePaymentItemDao.deletePurchasePaymentItems(pois);
	}

}
