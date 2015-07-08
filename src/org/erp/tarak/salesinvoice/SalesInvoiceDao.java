package org.erp.tarak.salesinvoice;

import java.util.Date;
import java.util.List;

import org.erp.tarak.customer.CustomerReport;


public interface SalesInvoiceDao {
	
	public void addSalesInvoice(SalesInvoice salesInvoice);

	public List<SalesInvoice> listSalesInvoices(String finYear);
	
	public SalesInvoice getSalesInvoice(long salesInvoiceId,String finYear);
	
	public void deleteSalesInvoice(SalesInvoice salesInvoice);

	public List<SalesInvoice> listSalesInvoicesByCustomer(long customerId,String finYear);

	public void updateSalesInvoiceBalance(SalesInvoice salesInvoice);

	public List<SalesInvoice> listPendingSalesInvoices(String finYear);

	public List<SalesInvoice> listSalesInvoicesByCustomer(String finYear);

	public List<Long> listBilledCustomers(String finYear);

	public List<Object[]> listPendingSalesInvoicesByCustomer(String finYear);

	public List<CustomerReport> getAvgTktPrice();

	public List<CustomerReport> getCustomerFrequency();

	public List<SalesInvoice> listSalesInvoicesByDate(Date balanceSheetDate);
}
