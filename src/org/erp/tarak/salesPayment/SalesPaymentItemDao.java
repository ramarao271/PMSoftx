package org.erp.tarak.salesPayment;

import java.util.List;


public interface SalesPaymentItemDao {
	
	public void addSalesPaymentItem(SalesPaymentItem salesPaymentItem);

	public List<SalesPaymentItem> listSalesPaymentItems();
	
	public SalesPaymentItem getSalesPaymentItem(long salesPaymentItemId);
	
	public void deleteSalesPaymentItem(SalesPaymentItem salesPaymentItem);

	public void deleteSalesPaymentItems(List<SalesPaymentItem> pois);
}
