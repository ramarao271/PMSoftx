package org.erp.tarak.purchasereturn;

import java.util.List;


public interface PurchaseReturnItemDao {
	
	public void addPurchaseReturnItem(PurchaseReturnItem purchaseReturnItem);

	public List<PurchaseReturnItem> listPurchaseReturnItems();
	
	public PurchaseReturnItem getPurchaseReturnItem(long purchaseReturnItemId);
	
	public void deletePurchaseReturnItem(PurchaseReturnItem purchaseReturnItem);

	public void deletePurchaseReturnItems(List<PurchaseReturnItem> pois);
}
