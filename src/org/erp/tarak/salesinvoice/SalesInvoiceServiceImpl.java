package org.erp.tarak.salesinvoice;

import java.util.Date;
import java.util.List;

import org.erp.tarak.customer.Customer;
import org.erp.tarak.customer.CustomerReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("salesInvoiceService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SalesInvoiceServiceImpl implements SalesInvoiceService {

	@Autowired
	private SalesInvoiceDao salesInvoiceDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addSalesInvoice(SalesInvoice salesInvoice) {
		salesInvoiceDao.addSalesInvoice(salesInvoice);
	}
	
	public List<SalesInvoice> listSalesInvoices(String finYear) {
		return salesInvoiceDao.listSalesInvoices(finYear);
	}

	public SalesInvoice getSalesInvoice(long salesInvoiceId,String finYear) {
		return salesInvoiceDao.getSalesInvoice(salesInvoiceId,finYear);
	}
	
	public void deleteSalesInvoice(SalesInvoice salesInvoice) {
		salesInvoiceDao.deleteSalesInvoice(salesInvoice);
	}

	@Override
	public List<SalesInvoice> listSalesInvoicesByCustomer(long customerId,String finYear) {
		return  salesInvoiceDao.listSalesInvoicesByCustomer(customerId,finYear);
	}

	@Override
	public void updateSalesInvoiceBalance(SalesInvoice salesInvoice) {
		salesInvoiceDao.updateSalesInvoiceBalance(salesInvoice);
		
	}

	@Override
	public List<SalesInvoice> listPendingSalesInvoices(String finYear) {
		return salesInvoiceDao.listPendingSalesInvoices(finYear);
	}

	@Override
	public List<SalesInvoice> listSalesInvoicesByCustomer(String finYear) {
		return salesInvoiceDao.listSalesInvoicesByCustomer(finYear);
	}

	@Override
	public List<Long> listBilledCustomers(String finYear) {
		return salesInvoiceDao.listBilledCustomers(finYear);
	}

	@Override
	public List<Object[]> listPendingSalesInvoicesByCustomer(String finYear) {
		return salesInvoiceDao.listPendingSalesInvoicesByCustomer(finYear);
	}

	@Override
	public List<CustomerReport> getAvgTktPrice() {
		return salesInvoiceDao.getAvgTktPrice();
	}

	@Override
	public List<CustomerReport> getCustomerFrequency() {
		return salesInvoiceDao.getCustomerFrequency();
	}

	@Override
	public List<SalesInvoice> listSalesInvoicesByDate(Date balanceSheetDate) {
		return salesInvoiceDao.listSalesInvoicesByDate(balanceSheetDate);
	}

	@Override
	public List<Customer> getBilledCustomers(String finYear) {
		return salesInvoiceDao.getBilledCustomers(finYear);
	}

}
