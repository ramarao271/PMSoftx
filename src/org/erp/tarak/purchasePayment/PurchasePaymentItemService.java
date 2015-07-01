package org.erp.tarak.purchasePayment;

import java.util.List;


public interface PurchasePaymentItemService {
	public void addPurchasePaymentItem(PurchasePaymentItem purchasePaymentItem);

	public List<PurchasePaymentItem> listPurchasePaymentItems();

	public PurchasePaymentItem getPurchasePaymentItem(long purchasePaymentItemId);

	public void deletePurchasePaymentItem(PurchasePaymentItem purchasePaymentItem);

	public void deletePurchasePaymentItems(List<PurchasePaymentItem> pois);

}
