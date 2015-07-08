package org.erp.tarak.salesPayment;

import java.util.Date;
import java.util.List;


public interface SalesPaymentDao {
	
	public void addSalesPayment(SalesPayment salesPayment);

	public List<SalesPayment> listSalesPayments(String finYear);
	
	public SalesPayment getSalesPayment(long salesPaymentId,String finYear);
	
	public void deleteSalesPayment(SalesPayment salesPayment);

	public List<SalesPayment> listSalesInvoicesByDate(Date balanceSheetDate);
}
