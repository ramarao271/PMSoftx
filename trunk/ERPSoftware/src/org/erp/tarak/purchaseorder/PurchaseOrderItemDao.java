package org.erp.tarak.purchaseorder;

import java.util.List;


public interface PurchaseOrderItemDao {
	
	public void addPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem);

	public List<PurchaseOrderItem> listPurchaseOrderItems();
	
	public PurchaseOrderItem getPurchaseOrderItem(long purchaseOrderItemId);
	
	public void deletePurchaseOrderItem(PurchaseOrderItem purchaseOrderItem);

	public void deletePurchaseOrderItems(List<PurchaseOrderItem> pois);
}
