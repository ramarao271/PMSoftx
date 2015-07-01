package org.erp.tarak.purchaseorder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("purchaseOrderItemService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PurchaseOrderItemServiceImpl implements PurchaseOrderItemService {

	@Autowired
	private PurchaseOrderItemDao purchaseOrderItemDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
		purchaseOrderItemDao.addPurchaseOrderItem(purchaseOrderItem);
	}
	
	public List<PurchaseOrderItem> listPurchaseOrderItems() {
		return purchaseOrderItemDao.listPurchaseOrderItems();
	}

	public PurchaseOrderItem getPurchaseOrderItem(long purchaseOrderItemId) {
		return purchaseOrderItemDao.getPurchaseOrderItem(purchaseOrderItemId);
	}
	
	public void deletePurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
		purchaseOrderItemDao.deletePurchaseOrderItem(purchaseOrderItem);
	}

	@Override
	public void deletePurchaseOrderItems(List<PurchaseOrderItem> pois) {
		// TODO Auto-generated method stub
		purchaseOrderItemDao.deletePurchaseOrderItems(pois);
	}

}
