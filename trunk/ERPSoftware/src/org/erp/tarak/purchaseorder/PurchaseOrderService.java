package org.erp.tarak.purchaseorder;

import java.util.List;


public interface PurchaseOrderService {

	public void addPurchaseOrder(PurchaseOrder purchaseOrder);

	public List<PurchaseOrder> listPurchaseOrders(String finYear);

	public PurchaseOrder getPurchaseOrder(long purchaseOrderId,String finYear);

	public void deletePurchaseOrder(PurchaseOrder purchaseOrder);

	public List<PurchaseOrder> listPendingPurchaseOrders(String finYear);

	public List<PurchaseOrder> listProcessedPurchaseOrders(String finYear);
}
