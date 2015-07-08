package org.erp.tarak.purchasePayment;

import java.util.Date;
import java.util.List;


public interface PurchasePaymentDao {
	
	public void addPurchasePayment(PurchasePayment purchasePayment);

	public List<PurchasePayment> listPurchasePayments(String finYear);
	
	public PurchasePayment getPurchasePayment(long purchasePaymentId,String finYear);
	
	public void deletePurchasePayment(PurchasePayment purchasePayment);

	public List<PurchasePayment> listPurchasePaymentsByDate(Date balanceSheetDate);
}
