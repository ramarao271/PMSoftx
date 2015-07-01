package org.erp.tarak.purchasereturn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("purchaseReturnItemService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PurchaseReturnItemServiceImpl implements PurchaseReturnItemService {

	@Autowired
	private PurchaseReturnItemDao purchaseReturnItemDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPurchaseReturnItem(PurchaseReturnItem purchaseReturnItem) {
		purchaseReturnItemDao.addPurchaseReturnItem(purchaseReturnItem);
	}
	
	public List<PurchaseReturnItem> listPurchaseReturnItems() {
		return purchaseReturnItemDao.listPurchaseReturnItems();
	}

	public PurchaseReturnItem getPurchaseReturnItem(long purchaseReturnItemId) {
		return purchaseReturnItemDao.getPurchaseReturnItem(purchaseReturnItemId);
	}
	
	public void deletePurchaseReturnItem(PurchaseReturnItem purchaseReturnItem) {
		purchaseReturnItemDao.deletePurchaseReturnItem(purchaseReturnItem);
	}

	@Override
	public void deletePurchaseReturnItems(List<PurchaseReturnItem> pois) {
		// TODO Auto-generated method stub
		purchaseReturnItemDao.deletePurchaseReturnItems(pois);
	}

}
